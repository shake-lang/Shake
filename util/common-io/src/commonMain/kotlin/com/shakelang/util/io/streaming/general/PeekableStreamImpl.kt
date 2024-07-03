package com.shakelang.util.io.streaming.general

/**
 * A stream that can be peeked
 * @param T The type of the elements in the stream
 * @property stream The stream to peek
 * @constructor Create a new peekable stream
 * @since 0.6.0
 */
open class PeekableStreamImpl<T>(
    private val stream: Stream<T>,
) : PeekableStream<T> {

    private val buffer = mutableListOf<T>()

    private fun fillBuffer(amount: Int) {
        for (i in 0 until amount) {
            if (!stream.hasNext()) break
            buffer.add(stream.read())
        }
    }

    /**
     * Peek at the next elements in the stream
     * @param index The index of the element to peek
     * @return The peeked elements
     * @since 0.6.0
     */
    override fun peek(index: Int): T {
        fillBuffer(index)
        if (buffer.size < index) throw IllegalStateException("Stream is empty")
        return buffer[index - 1]
    }

    /**
     * Peek at the next element in the stream
     * @return The peeked element
     * @since 0.6.0
     */
    override fun peek(): T {
        fillBuffer(1)
        if (buffer.isEmpty()) throw IllegalStateException("Stream is empty")
        return buffer[0]
    }

    /**
     * Peek at the next elements in the stream
     * @param index The index of the element to peek
     * @return The peeked elements or null if the stream is empty
     * @since 0.6.0
     */
    override fun peekOrNull(index: Int): T? {
        fillBuffer(index)
        return buffer.getOrNull(index - 1)
    }

    /**
     * Peek at the next element in the stream
     * @return The peeked element or null if the stream is empty
     * @since 0.6.0
     */
    override fun peekOrNull(): T? {
        fillBuffer(1)
        return buffer.getOrNull(0)
    }

    /**
     * Read the next element from the stream
     * @return The next element
     * @since 0.6.0
     */
    override fun read(): T {
        if (buffer.isEmpty()) fillBuffer(1)
        if (buffer.isEmpty()) throw IllegalStateException("Stream is empty")
        return buffer.removeAt(0)
    }

    /**
     * Check if the stream has a next element
     * @return If the stream has a next element
     * @since 0.6.0
     */
    override fun hasNext(): Boolean = buffer.isNotEmpty() || stream.hasNext()

    /**
     * Check if the stream has a specified amount of elements
     * @param amount The amount of elements to check for
     * @return If the stream has the specified amount of elements
     * @since 0.6.0
     */
    override fun has(amount: Int): Boolean {
        fillBuffer(amount)
        return buffer.size >= amount
    }
}

fun <T> Stream<T>.peekable(): PeekableStream<T> = PeekableStreamImpl(this)
