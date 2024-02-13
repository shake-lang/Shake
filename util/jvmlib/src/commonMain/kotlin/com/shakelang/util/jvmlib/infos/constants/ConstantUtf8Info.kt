package com.shakelang.util.jvmlib.infos.constants

import com.shakelang.util.io.streaming.input.DataInputStream
import com.shakelang.util.io.streaming.input.InputStream
import com.shakelang.util.io.streaming.input.dataStream
import com.shakelang.util.io.streaming.output.DataOutputStream

class ConstantUtf8Info(val value: String) : ConstantInfo() {

    override val tag: Byte get() = TAG
    override val tagName: String get() = NAME

    override fun toJson() = super.toJson().with("value", value)

    override fun dump(out: DataOutputStream) {
        out.writeByte(tag)
        out.writeStringUTF8(value)
    }

    companion object {
        fun contentsFromStream(stream: DataInputStream): ConstantUtf8Info {
            val length = stream.readUnsignedShort()
            val bytes = stream.readNBytes(length.toInt())
            val value = bytes.joinToString("") { it.toInt().toChar().toString() }
            return ConstantUtf8Info(value)
        }

        fun contentsFromStream(stream: InputStream) = contentsFromStream(stream.dataStream)

        fun fromStream(stream: DataInputStream) =
            if (stream.readByte() != TAG) {
                throw IllegalArgumentException("Invalid tag for ConstantUtf8Info")
            } else {
                contentsFromStream(stream)
            }

        fun fromStream(stream: InputStream) = fromStream(stream.dataStream)

        fun contentsFromBytes(bytes: ByteArray) = contentsFromStream(bytes.dataStream())

        fun fromBytes(bytes: ByteArray) = fromStream(bytes.dataStream())

        const val NAME = "constant_utf8_info"
        const val TAG = ConstantTags.CONSTANT_UTF8
    }
}
