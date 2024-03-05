package com.shakelang.util.jvmlib.infos.attributes

import com.shakelang.util.io.streaming.input.bytes.DataInputStream
import com.shakelang.util.io.streaming.output.bytes.DataOutputStream
import com.shakelang.util.jvmlib.infos.constants.ConstantInfo
import com.shakelang.util.jvmlib.infos.constants.ConstantPool
import com.shakelang.util.jvmlib.infos.constants.ConstantUser
import com.shakelang.util.primitives.bytes.toBytes

open class AttributeMap(open val map: Map<String, AttributeInfo>) : Map<String, AttributeInfo>, ConstantUser {

    override val uses: Array<ConstantInfo> get() = map.values.map { it.uses.toList() }.flatten().toTypedArray()
    override val users: Array<ConstantUser> get() = map.values.toTypedArray()

    private lateinit var clazz: com.shakelang.util.jvmlib.infos.ClassInfo

    override fun get(key: String): AttributeInfo? = map[key]
    override val entries: Set<Map.Entry<String, AttributeInfo>>
        get() = map.entries

    override val keys: Set<String>
        get() = map.keys

    override val size: Int
        get() = map.size

    override val values: Collection<AttributeInfo>
        get() = map.values

    override fun containsKey(key: String): Boolean = map.containsKey(key)
    override fun containsValue(value: AttributeInfo): Boolean = map.containsValue(value)
    override fun isEmpty(): Boolean = map.isEmpty()

    fun toBytes(): ByteArray {
        val childBytes = mutableListOf<Byte>()
        childBytes.addAll(this.size.toUShort().toBytes().toList())
        for (i in map.values.indices) {
            val attribute = map.values.elementAt(i)
            childBytes.addAll(attribute.toBytes().toList())
        }
        return childBytes.toByteArray()
    }

    fun toJson() = map.map {
        it.value.toJson()
    }

    fun init(clazz: com.shakelang.util.jvmlib.infos.ClassInfo) {
        this.clazz = clazz
        for (i in map.values.indices) {
            val attribute = map.values.elementAt(i)
            attribute.init(clazz)
        }
    }

    fun dump(out: DataOutputStream) = out.writeByteArray(this.toBytes())

    companion object {

        fun fromStream(pool: ConstantPool, stream: DataInputStream): AttributeMap {
            val size = stream.readUnsignedShort()
            val map = mutableMapOf<String, AttributeInfo>()
            for (i in 0 until size.toInt()) {
                val attribute = AttributeInfo.fromStream(pool, stream)
                map[attribute.name.value] = attribute
            }
            return AttributeMap(map)
        }
    }
}

class MutableAttributeMap(map: MutableMap<String, AttributeInfo>) :
    AttributeMap(map),
    MutableMap<String, AttributeInfo> {

    override val map: MutableMap<String, AttributeInfo>
        get() = super.map as MutableMap<String, AttributeInfo>

    override val entries: MutableSet<MutableMap.MutableEntry<String, AttributeInfo>>
        get() = map.entries

    override val keys: MutableSet<String>
        get() = map.keys

    override val values: MutableCollection<AttributeInfo>
        get() = map.values

    override fun clear() = map.clear()

    override fun put(key: String, value: AttributeInfo): AttributeInfo? = map.put(key, value)

    override fun putAll(from: Map<out String, AttributeInfo>) = map.putAll(from)

    override fun remove(key: String): AttributeInfo? = map.remove(key)
}
