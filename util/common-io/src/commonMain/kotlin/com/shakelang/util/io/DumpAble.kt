package com.shakelang.util.io

import com.shakelang.util.io.streaming.output.bytes.ByteArrayOutputStream
import com.shakelang.util.io.streaming.output.bytes.DataOutputStream
import com.shakelang.util.io.streaming.output.bytes.OutputStream

/**
 * An interface for objects that can be dumped to a OutputStreams
 */
interface DumpAble {

    /**
     * Dump the object to a [DataOutputStream]
     *
     * @param stream The [DataOutputStream] to dump the object to
     */
    fun dump(stream: DataOutputStream)

    /**
     * Dump the object to an [OutputStream]
     * @param stream The [OutputStream] to dump the object to
     */
    fun dump(stream: OutputStream) {
        dump(DataOutputStream(stream))
    }

    /**
     * Dump the object to an [ByteArray]
     *
     * @return The [ByteArray] with the dumped object
     */
    fun dump(): ByteArray {
        val stream = ByteArrayOutputStream()
        dump(stream)
        return stream.toByteArray()
    }
}
