package com.shakelang.util.jvmlib.infos.constants

import com.shakelang.util.io.streaming.input.DataInputStream
import com.shakelang.util.io.streaming.input.InputStream
import com.shakelang.util.io.streaming.input.dataStream
import com.shakelang.util.io.streaming.output.DataOutputStream

class ConstantDoubleInfo(val value: Double) : ConstantInfo() {

    override val tag: Byte get() = ConstantDoubleInfo.TAG
    override val tagName: String get() = NAME
    override fun toJson() = super.toJson().with("value", value)

    override fun dump(out: DataOutputStream) {
        out.writeByte(tag)
        out.writeDouble(value)
    }

    companion object {
        fun contentsFromStream(stream: DataInputStream): ConstantDoubleInfo {
            val value = stream.readDouble()
            return ConstantDoubleInfo(value)
        }

        fun contentsFromStream(stream: InputStream) = contentsFromStream(stream.dataStream)

        fun fromStream(stream: DataInputStream) =
            if (stream.readByte() != TAG) {
                throw IllegalArgumentException("Invalid tag for ConstantDoubleInfo")
            } else {
                contentsFromStream(stream)
            }

        fun fromStream(stream: InputStream) = fromStream(stream.dataStream)

        fun contentsFromBytes(bytes: ByteArray) = contentsFromStream(bytes.dataStream())

        fun fromBytes(bytes: ByteArray) = fromStream(bytes.dataStream())

        const val NAME = "constant_double_info"
        const val TAG = ConstantTags.CONSTANT_DOUBLE
    }
}
