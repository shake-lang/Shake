package com.shakelang.shake.bytecode.interpreter.heap

import com.shakelang.util.primitives.bytes.toBytes

class Malloc(
    val globalMemory: GlobalMemory,
) {
    var freeStartPointer: Long = -1
    var freeTailPointer: Long = -1
    var usedStartPointer: Long = -1
    var usedTailPointer: Long = -1

    // Let's define some stuff
    // All our malloc objects have the following header:
    // struct malloc_header {
    //     uint64_t size;
    //     struct malloc_header* next;
    // };
    // As we are in a 64bit environment, our next pointer is 8 bytes long,
    // so overall our header is 16 bytes long
    val headerSize = 16

    // We are filling memory from the bottom to the top, so we need to know
    // what we used already. We prefer to recycle memory over increasing the
    // memory size, so we need to know what we used already
    var size: Long = 0
        private set

    fun readHeader(pointer: Long): MallocHeader {
        if (!globalMemory.contains(pointer) || !globalMemory.contains(pointer + 15)) {
            throw OutOfRangeException("Cannot read header at $pointer")
        }

        val sizeInfo = globalMemory.getLong(pointer)
        val next = globalMemory.getLong(pointer + 8)

        val size = sizeInfo and 0x0000FFFFFFFFFFFF // 48 bits
        val additionalInfo = ((sizeInfo and 0xFFFF000000000000uL.toLong()) shr 48).toInt() // 16 bits

        return MallocHeader(
            size,
            next,
            additionalInfo,
        )
    }

    fun writeHeader(pointer: Long, header: MallocHeader) {
        if (!globalMemory.contains(pointer) || !globalMemory.contains(pointer + 15)) {
            throw OutOfRangeException("Cannot read header at $pointer")
        }

        val sizeInfo = header.size or (header.additionalInfo.toLong() shl 48)
        val next = header.next

        globalMemory.setBytes(pointer, sizeInfo.toBytes())
        globalMemory.setBytes(pointer + 8, next.toBytes())
    }

    private fun searchForFreeSpace(size: Long): FreeHeaderSearchResult? {
        var pointer = freeStartPointer
        var resultPreviousPointer = -1L
        var resultPointer = -1L
        var resultHeader = MallocHeader(0, 0)
        var previousPointer = -1L
        while (pointer != -1L) {
            val header = readHeader(pointer)
            if (header.size >= size) {
                // Free chunk is big enough.

                if (header.size == size) {
                    // This chunk is exactly the size we need.
                    // No need to search further.
                    return FreeHeaderSearchResult(pointer, previousPointer, header)
                }

                if (header.size < resultHeader.size || resultPointer == -1L) {
                    // This chunk is smaller than the previous best fit.
                    // But it is still big enough.
                    resultPointer = pointer
                    resultPreviousPointer = previousPointer
                    resultHeader = header

                    println("Found a chunk that is smaller than the previous best fit for $size: ${header.size}")
                }
            }

            previousPointer = pointer
            pointer = header.next
        }

        if (resultPointer == -1L) {
            // No free chunk was found.
            return null
        }
        println("Selected chunk with size ${resultHeader.size} for $size")

        return FreeHeaderSearchResult(resultPointer, resultPreviousPointer, resultHeader)
    }

    private fun appendToUsed(pointer: Long, size: Long = -1L) {
        val realSize = if (size == -1L) readHeader(pointer).size else size
        writeHeader(pointer, MallocHeader(realSize, -1))

        if (usedTailPointer == -1L) {
            usedTailPointer = pointer
            usedStartPointer = pointer
            return
        }

        val header = readHeader(usedTailPointer)
        writeHeader(usedTailPointer, MallocHeader(header.size, pointer))
        usedTailPointer = pointer
    }

    private fun appendToFree(pointer: Long, size: Long = -1L) {
        val realSize = if (size == -1L) readHeader(pointer).size else size
        writeHeader(pointer, MallocHeader(realSize, -1))

        if (freeTailPointer == -1L) {
            freeTailPointer = pointer
            freeStartPointer = pointer
            return
        }

        val header = readHeader(freeTailPointer)
        writeHeader(freeTailPointer, MallocHeader(header.size, pointer))
        freeTailPointer = pointer
    }

    private fun grow(size: Long): Long {
        this.size += size
        val overflow = this.size - this.globalMemory.size
        if (overflow > 0) globalMemory.grow(size.divCeil(globalMemory.innerSize).toInt())
        return this.size
    }

    fun malloc(size: Long): Long {
        // We need to allocate a new chunk of memory.
        // We need to find a free chunk that is big enough.
        val result = searchForFreeSpace(size)
        result?.let {
            println("Found a chunk with size ${it.header.size} for $size at ${it.pointer}")
        }

        if (result == null) {
            // No free chunk was found.
            // We need to allocate a new chunk.
            val newPointer = this.size + GlobalMemory.POINTER_BASE
            grow(size + headerSize)
            this.writeHeader(newPointer, MallocHeader(size, -1))

            // Append the chunk to the end of used chunks.
            appendToUsed(newPointer)

            return newPointer + headerSize
        }

        // We found a free chunk.
        // TODO: Split the chunk if it is too big.

        // Update the previous chunk to point to the next chunk.
        if (result.previousPointer != -1L) {
            val beforeHeader = readHeader(result.previousPointer)
            writeHeader(result.previousPointer, MallocHeader(beforeHeader.size, result.header.next))
        }

        // Update the header of the chunk to be allocated.
        writeHeader(result.pointer, MallocHeader(size, -1))

        // Append the chunk to the end of used chunks.
        appendToUsed(result.pointer)

        // If this is the first chunk, update the start pointer.
        if (result.pointer == freeStartPointer) {
            freeStartPointer = result.header.next
        }

        // Return the pointer to the chunk.
        return result.pointer + headerSize
    }

    private fun findBefore(pointer: Long): Long {
        var currentPointer = freeStartPointer
        while (currentPointer != -1L) {
            val header = readHeader(currentPointer)
            if (header.next == pointer) {
                return currentPointer
            }
            currentPointer = header.next
        }
        return -1L
    }

    fun free(pointer: Long) {
        val headerPointer = pointer - headerSize
        val header = readHeader(headerPointer)

        // Remove the chunk from the used chunks list.
        val beforePointer = findBefore(headerPointer)
        if (beforePointer != -1L) {
            val beforeHeader = readHeader(beforePointer)
            writeHeader(beforePointer, MallocHeader(beforeHeader.size, header.next))
        }
        if (headerPointer == usedTailPointer) {
            usedTailPointer = beforePointer
        }
        if (headerPointer == usedStartPointer) {
            usedStartPointer = header.next
        }

        appendToFree(headerPointer, header.size)
    }
}

private fun Long.divCeil(other: Int): Long {
    return (this + other - 1) / other
}

data class MallocHeader(
    var size: Long,
    var next: Long,

    // The additional info is stored in the last 16 bits of the first 8 header bytes.
    // The second of these two bytes is used to store the number of times this
    // chunk survived a garbage collection.
    // The first byte stores a list of boolean values.
    // 0x01: Is marked (as used)
    // 0x02: Is pinned (cannot be collected)
    // 0x04: Is scanned (has been scanned by the garbage collector in the current cycle)
    // 0x08: Reserved for future use
    // 0x10: Reserved for future use
    // 0x20: Reserved for future use
    // 0x40: Reserved for future use
    // 0x80: Reserved for future use

    var additionalInfo: Int = 0,
) {
    val nextIndex: Long get() = GlobalMemory.pointerToIndex(next)
    var isMarked: Boolean
        get() = additionalInfo and 0x1 == 0x1
        set(value) = if (value) mark() else unmark()

    var isPinned: Boolean
        get() = additionalInfo and 0x2 == 0x2
        set(value) {
            additionalInfo = additionalInfo and 0xfd or (if (value) 0x2 else 0x0)
        }

    var isScanned: Boolean
        get() = additionalInfo and 0x4 == 0x4
        set(value) {
            additionalInfo = additionalInfo and 0xfb or (if (value) 0x4 else 0x0)
        }

    var survived: Short
        get() = (additionalInfo and 0xf).toShort()
        set(value) {
            additionalInfo = additionalInfo and 0xf0 or (value.toInt() and 0xf)
        }

    fun check(): Boolean {
        return GlobalMemory.isPointer(next)
    }

    fun mark() {
        additionalInfo = additionalInfo or 0x10
    }

    fun unmark() {
        additionalInfo = additionalInfo and 0x10.inv()
    }
}

private data class FreeHeaderSearchResult(
    val pointer: Long,
    val previousPointer: Long,
    val header: MallocHeader,
)
