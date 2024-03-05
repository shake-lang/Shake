package com.shakelang.util.jvmlib.infos.fields

import com.shakelang.util.io.streaming.input.bytes.DataInputStream
import com.shakelang.util.io.streaming.input.bytes.InputStream
import com.shakelang.util.io.streaming.input.bytes.dataStream
import com.shakelang.util.io.streaming.output.bytes.ByteArrayOutputStream
import com.shakelang.util.io.streaming.output.bytes.DataOutputStream
import com.shakelang.util.io.streaming.output.bytes.OutputStream
import com.shakelang.util.jvmlib.infos.attributes.AttributeMap
import com.shakelang.util.jvmlib.infos.constants.ConstantInfo
import com.shakelang.util.jvmlib.infos.constants.ConstantPool
import com.shakelang.util.jvmlib.infos.constants.ConstantUser
import com.shakelang.util.jvmlib.infos.constants.ConstantUtf8Info
import com.shakelang.util.shason.json

class FieldInfo(
    val accessFlags: UShort,
    val name: ConstantUtf8Info,
    val descriptor: ConstantUtf8Info,
    val attributes: AttributeMap,
) : ConstantUser {

    val isPublic: Boolean
        get() = accessFlags.toInt() and 0x0001 != 0

    val isPrivate: Boolean
        get() = accessFlags.toInt() and 0x0002 != 0

    val isProtected: Boolean
        get() = accessFlags.toInt() and 0x0004 != 0

    val isStatic: Boolean
        get() = accessFlags.toInt() and 0x0008 != 0

    val isFinal: Boolean
        get() = accessFlags.toInt() and 0x0010 != 0

    val isVolatile: Boolean
        get() = accessFlags.toInt() and 0x0040 != 0

    val isTransient: Boolean
        get() = accessFlags.toInt() and 0x0080 != 0

    val isSynthetic: Boolean
        get() = accessFlags.toInt() and 0x1000 != 0

    val isEnum: Boolean
        get() = accessFlags.toInt() and 0x4000 != 0

    val nameIndex get() = name.index
    val descriptorIndex get() = descriptor.index

    override val uses: Array<ConstantInfo> get() = arrayOf(name, descriptor, *attributes.uses)
    override val users: Array<ConstantUser> get() = arrayOf(this, *attributes.users)

    private lateinit var clazz: com.shakelang.util.jvmlib.infos.ClassInfo
    val classInfo: com.shakelang.util.jvmlib.infos.ClassInfo get() = clazz

    override fun toString() = json.stringify(toJson())

    fun toJson() = mapOf(
        "access_flags" to accessFlags,
        "name" to name.toJson(),
        "name_index" to nameIndex,
        "descriptor" to descriptor.toJson(),
        "descriptor_index" to descriptorIndex,
        "attributes" to attributes.toJson(),
    )

    fun init(clazz: com.shakelang.util.jvmlib.infos.ClassInfo) {
        this.clazz = clazz
        this.attributes.init(clazz)
    }

    fun dump(out: DataOutputStream) {
        out.writeUnsignedShort(accessFlags)
        out.writeUnsignedShort(nameIndex)
        out.writeUnsignedShort(descriptorIndex)
        attributes.dump(out)
    }

    fun dump(out: OutputStream) = dump(DataOutputStream(out))
    fun toBytes(): ByteArray {
        val out = ByteArrayOutputStream()
        dump(out)
        return out.toByteArray()
    }

    companion object {
        fun fromStream(pool: ConstantPool, stream: DataInputStream): FieldInfo {
            val accessFlags = stream.readUnsignedShort()
            val nameIndex = stream.readUnsignedShort()
            val name = pool.getUtf8(nameIndex)
            val descriptorIndex = stream.readUnsignedShort()
            val descriptor = pool.getUtf8(descriptorIndex)
            val attributes = AttributeMap.fromStream(pool, stream)
            return FieldInfo(accessFlags, name, descriptor, attributes)
        }

        fun fromStream(pool: ConstantPool, stream: InputStream): FieldInfo {
            return fromStream(pool, stream.dataStream)
        }

        fun fromBytes(pool: ConstantPool, array: ByteArray): FieldInfo {
            return fromStream(pool, array.dataStream())
        }
    }
}
