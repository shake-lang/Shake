package com.shakelang.shake.parser.node

import com.shakelang.shake.lexer.token.ShakeToken
import com.shakelang.util.shason.JSON

@Suppress("unused", "MemberVisibilityCanBePrivate")
class ShakeVariableType(
    val nameToken: ShakeToken,
    val parent: ShakeVariableType?,
    val dotToken: ShakeToken?,
) : ShakeNode {

    val type = when (nameToken.value) {
        "dynamic" -> Type.DYNAMIC
        "byte" -> Type.BYTE
        "short" -> Type.SHORT
        "int" -> Type.INTEGER
        "long" -> Type.LONG
        "float" -> Type.FLOAT
        "double" -> Type.DOUBLE
        "ubyte" -> Type.UNSIGNED_BYTE
        "ushort" -> Type.UNSIGNED_SHORT
        "uint" -> Type.UNSIGNED_INTEGER
        "ulong" -> Type.UNSIGNED_LONG
        "bool" -> Type.BOOLEAN
        "char" -> Type.CHAR
        "object" -> Type.OBJECT
        "void" -> Type.VOID
        else -> Type.OBJECT
    }

    val name get() = nameToken.value

    override fun toString(): String = JSON.stringify(this.json)

    override val json: Map<String, *>
        get() = toJson()

    override fun toJson(): Map<String, *> =
        mapOf(
            "name" to nodeName,
            "type" to type.name.lowercase(),
        )

    override fun equalsIgnorePosition(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShakeVariableType) return false
        if (type != other.type) return false

        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShakeVariableType) return false
        if (type != other.type) return false
        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }

    enum class Type {
        DYNAMIC,
        BYTE,
        SHORT,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        UNSIGNED_BYTE,
        UNSIGNED_SHORT,
        UNSIGNED_INTEGER,
        UNSIGNED_LONG,
        BOOLEAN,
        CHAR,
        OBJECT,
        VOID,
        ;

        override fun toString(): String {
            return name.lowercase()
        }
    }
}
