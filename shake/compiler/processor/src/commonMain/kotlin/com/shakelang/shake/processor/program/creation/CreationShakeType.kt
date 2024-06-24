package com.shakelang.shake.processor.program.creation

import com.shakelang.shake.conventions.descriptor.TypeDescriptor
import com.shakelang.shake.parser.node.misc.ShakeVariableType
import com.shakelang.shake.processor.program.types.GenericMap
import com.shakelang.shake.processor.program.types.ShakeType
import com.shakelang.shake.processor.program.types.code.ShakeScope

abstract class CreationShakeType(
    override val name: String,
) : ShakeType {

    abstract override val kind: ShakeType.Kind

    abstract override fun castableTo(other: ShakeType): Boolean
    override fun compatibleTo(other: ShakeType): Boolean = compatibilityDistance(other) >= 0
    abstract override fun compatibilityDistance(other: ShakeType): Int

    abstract override fun toJson(): Map<String, Any?>

    override fun equals(other: Any?): Boolean {
        if (other !is CreationShakeType) return false
        return qualifiedName == other.qualifiedName
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + kind.hashCode() + qualifiedName.hashCode()
        return result
    }

    override fun toString(): String = name

    abstract class Primitive(
        name: String,
        override val type: ShakeType.PrimitiveType,
    ) : CreationShakeType(name),
        ShakeType.Primitive {
        override val kind: ShakeType.Kind
            get() = ShakeType.Kind.PRIMITIVE

        override fun castableTo(other: ShakeType): Boolean =
            other is Primitive &&
                (
                    other.type == ShakeType.PrimitiveType.BYTE ||
                        other.type == ShakeType.PrimitiveType.SHORT ||
                        other.type == ShakeType.PrimitiveType.INT ||
                        other.type == ShakeType.PrimitiveType.LONG ||
                        other.type == ShakeType.PrimitiveType.FLOAT ||
                        other.type == ShakeType.PrimitiveType.DOUBLE ||
                        other.type == ShakeType.PrimitiveType.UNSIGNED_BYTE ||
                        other.type == ShakeType.PrimitiveType.UNSIGNED_SHORT ||
                        other.type == ShakeType.PrimitiveType.UNSIGNED_INT ||
                        other.type == ShakeType.PrimitiveType.UNSIGNED_LONG ||
                        other.type == ShakeType.PrimitiveType.CHAR
                    )

        companion object {

            private fun bool(): Primitive = BOOLEAN

            private fun byte(): Primitive = BYTE

            private fun short(): Primitive = SHORT

            private fun int(): Primitive = INT

            private fun long(): Primitive = LONG

            private fun float(): Primitive = FLOAT

            private fun double(): Primitive = DOUBLE

            private fun unsignedByte(): Primitive = UNSIGNED_BYTE

            private fun unsignedShort(): Primitive = UNSIGNED_SHORT

            private fun unsignedInt(): Primitive = UNSIGNED_INT

            private fun unsignedLong(): Primitive = UNSIGNED_LONG

            val BOOLEAN: Primitive = object : Primitive("boolean", ShakeType.PrimitiveType.BOOLEAN) {
                override fun additionType(other: ShakeType, scope: ShakeScope): CreationShakeType? = null
                override fun subtractionType(other: ShakeType, scope: ShakeScope): CreationShakeType? = null
                override fun multiplicationType(other: ShakeType, scope: ShakeScope): CreationShakeType? = null
                override fun divisionType(other: ShakeType, scope: ShakeScope): CreationShakeType? = null
                override fun modulusType(other: ShakeType, scope: ShakeScope): CreationShakeType? = null
                override fun powerType(other: ShakeType, scope: ShakeScope): CreationShakeType? = null

                override fun greaterThanType(other: ShakeType, scope: ShakeScope): CreationShakeType = bool()
                override fun greaterThanOrEqualType(other: ShakeType, scope: ShakeScope): CreationShakeType = bool()
                override fun lessThanType(other: ShakeType, scope: ShakeScope): CreationShakeType = bool()
                override fun lessThanOrEqualType(other: ShakeType, scope: ShakeScope): CreationShakeType = bool()
                override fun logicalAndType(other: ShakeType, scope: ShakeScope): CreationShakeType = bool()
                override fun logicalOrType(other: ShakeType, scope: ShakeScope): CreationShakeType = bool()
                override fun logicalNotType(scope: ShakeScope): CreationShakeType = bool()

                override fun castableTo(other: ShakeType): Boolean =
                    other is Primitive && other.type == ShakeType.PrimitiveType.BOOLEAN

                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other is Primitive && other.type == ShakeType.PrimitiveType.BOOLEAN) 0 else -1

                override fun toJson(): Map<String, Any?> = mapOf("type" to "boolean")

                override val qualifiedName: String get() = "Z"
            }

            val NULL: Primitive = object : Primitive("null", ShakeType.PrimitiveType.NULL) {
                override fun compatibilityDistance(other: ShakeType): Int {
                    TODO("Not yet implemented")
                }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "null")

                override val qualifiedName: String get() = "N"
            }

            val BYTE: Primitive = object : Primitive("byte", ShakeType.PrimitiveType.BYTE) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.BYTE -> 0
                            ShakeType.PrimitiveType.SHORT -> 1
                            ShakeType.PrimitiveType.INT -> 2
                            ShakeType.PrimitiveType.LONG -> 3
                            ShakeType.PrimitiveType.FLOAT -> 4
                            ShakeType.PrimitiveType.DOUBLE -> 4
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "byte")

                override val qualifiedName: String get() = "B"
            }

            val SHORT: Primitive = object : Primitive("shorts", ShakeType.PrimitiveType.SHORT) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.SHORT -> 0
                            ShakeType.PrimitiveType.INT -> 1
                            ShakeType.PrimitiveType.LONG -> 2
                            ShakeType.PrimitiveType.FLOAT -> 3
                            ShakeType.PrimitiveType.DOUBLE -> 4
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "shorts")

                override val qualifiedName: String get() = "S"
            }

            val INT: Primitive = object : Primitive("int", ShakeType.PrimitiveType.INT) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.INT -> 0
                            ShakeType.PrimitiveType.LONG -> 1
                            ShakeType.PrimitiveType.FLOAT -> 2
                            ShakeType.PrimitiveType.DOUBLE -> 3
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "int")

                override val qualifiedName: String get() = "I"
            }

            val LONG: Primitive = object : Primitive("long", ShakeType.PrimitiveType.LONG) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.LONG -> 0
                            ShakeType.PrimitiveType.FLOAT -> 1
                            ShakeType.PrimitiveType.DOUBLE -> 2
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "long")

                override val qualifiedName: String get() = "J"
            }

            val FLOAT: Primitive = object : Primitive("float", ShakeType.PrimitiveType.FLOAT) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.FLOAT -> 0
                            ShakeType.PrimitiveType.DOUBLE -> 1
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "float")

                override val qualifiedName: String get() = "F"
            }

            val DOUBLE: Primitive = object : Primitive("double", ShakeType.PrimitiveType.DOUBLE) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.DOUBLE -> 0
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "double")

                override val qualifiedName: String get() = "D"
            }

            val UNSIGNED_BYTE: Primitive = object : Primitive("unsigned_byte", ShakeType.PrimitiveType.UNSIGNED_BYTE) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.UNSIGNED_BYTE -> 0
                            ShakeType.PrimitiveType.UNSIGNED_SHORT -> 1
                            ShakeType.PrimitiveType.UNSIGNED_INT -> 2
                            ShakeType.PrimitiveType.UNSIGNED_LONG -> 3
                            ShakeType.PrimitiveType.SHORT -> 4
                            ShakeType.PrimitiveType.INT -> 5
                            ShakeType.PrimitiveType.LONG -> 6
                            ShakeType.PrimitiveType.FLOAT -> 7
                            ShakeType.PrimitiveType.DOUBLE -> 8
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "unsigned_byte")

                override val qualifiedName: String get() = "b"
            }

            val UNSIGNED_SHORT: Primitive =
                object : Primitive("unsigned_short", ShakeType.PrimitiveType.UNSIGNED_SHORT) {
                    override fun compatibilityDistance(other: ShakeType): Int =
                        if (other !is Primitive) {
                            -1
                        } else {
                            when (other.type) {
                                ShakeType.PrimitiveType.UNSIGNED_SHORT -> 0
                                ShakeType.PrimitiveType.UNSIGNED_INT -> 1
                                ShakeType.PrimitiveType.UNSIGNED_LONG -> 2
                                ShakeType.PrimitiveType.BYTE -> 3
                                ShakeType.PrimitiveType.SHORT -> 4
                                ShakeType.PrimitiveType.INT -> 5
                                ShakeType.PrimitiveType.LONG -> 6
                                ShakeType.PrimitiveType.FLOAT -> 7
                                ShakeType.PrimitiveType.DOUBLE -> 8
                                else -> -1
                            }
                        }

                    override fun toJson(): Map<String, Any?> = mapOf("type" to "unsigned_short")

                    override val qualifiedName: String get() = "s"
                }

            val UNSIGNED_INT: Primitive = object : Primitive("unsigned_int", ShakeType.PrimitiveType.UNSIGNED_INT) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.UNSIGNED_INT -> 0
                            ShakeType.PrimitiveType.UNSIGNED_LONG -> 1
                            ShakeType.PrimitiveType.BYTE -> 2
                            ShakeType.PrimitiveType.SHORT -> 3
                            ShakeType.PrimitiveType.INT -> 4
                            ShakeType.PrimitiveType.LONG -> 5
                            ShakeType.PrimitiveType.FLOAT -> 6
                            ShakeType.PrimitiveType.DOUBLE -> 7
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "unsigned_int")

                override val qualifiedName: String get() = "i"
            }

            val UNSIGNED_LONG: Primitive = object : Primitive("unsigned_long", ShakeType.PrimitiveType.UNSIGNED_LONG) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.UNSIGNED_LONG -> 0
                            ShakeType.PrimitiveType.BYTE -> 1
                            ShakeType.PrimitiveType.SHORT -> 2
                            ShakeType.PrimitiveType.INT -> 3
                            ShakeType.PrimitiveType.LONG -> 4
                            ShakeType.PrimitiveType.FLOAT -> 5
                            ShakeType.PrimitiveType.DOUBLE -> 6
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "unsigned_long")

                override val qualifiedName: String get() = "j"
            }

            val CHAR = object : Primitive("char", ShakeType.PrimitiveType.CHAR) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.CHAR -> 0
                            ShakeType.PrimitiveType.SHORT -> 1
                            ShakeType.PrimitiveType.UNSIGNED_SHORT -> 1
                            ShakeType.PrimitiveType.INT -> 2
                            ShakeType.PrimitiveType.UNSIGNED_INT -> 2
                            ShakeType.PrimitiveType.LONG -> 3
                            ShakeType.PrimitiveType.UNSIGNED_LONG -> 3
                            ShakeType.PrimitiveType.FLOAT -> 5
                            ShakeType.PrimitiveType.DOUBLE -> 6
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "char")

                override val qualifiedName: String get() = "C"
            }

            val VOID = object : Primitive("void", ShakeType.PrimitiveType.VOID) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.VOID -> 0
                            else -> -1
                        }
                    }

                override fun toJson(): Map<String, Any?> = mapOf("type" to "void")

                override val qualifiedName: String get() = "V"
            }

            val DYNAMIC = object : Primitive("dynamic", ShakeType.PrimitiveType.DYNAMIC) {
                override fun compatibilityDistance(other: ShakeType): Int =
                    if (other !is Primitive) {
                        -1
                    } else {
                        when (other.type) {
                            ShakeType.PrimitiveType.DYNAMIC -> 0
                            else -> -1
                        }
                    }

                override fun compatibleTo(other: ShakeType): Boolean = other == this

                override fun castableTo(other: ShakeType): Boolean = true

                override fun toJson(): Map<String, Any?> = mapOf("type" to "dynamic")

                override val qualifiedName: String get() = "?"
            }
        }
    }

    class Object(
        override val clazz: CreationShakeClass,
        override val genericArguments: List<CreationShakeType>? = null,
    ) : CreationShakeType(clazz.qualifiedName),
        ShakeType.Object {

        override val genericMap: CreationGenericMap
            get() = CreationGenericMap(
                clazz.generics.zip(genericArguments ?: emptyList()).toMap(),
            )

        override val kind: ShakeType.Kind
            get() = ShakeType.Kind.OBJECT

        override fun castableTo(other: ShakeType): Boolean = other is Object && other.clazz.compatibleTo(clazz)

        override fun compatibleTo(other: ShakeType): Boolean = other is Object && clazz.compatibleTo(other.clazz)

        override fun compatibilityDistance(other: ShakeType): Int = if (other is Object) clazz.compatibilityDistance(other.clazz) else -1

        override fun toJson(): Map<String, Any?> = mapOf("type" to "object", "class" to clazz.qualifiedName)
    }

    class Generic(
        override val name: String,
        override val base: ShakeType?,
        override val owner: String,
    ) : CreationShakeType(name),
        ShakeType.Generic {
        override val qualifiedName: String
            get() = "G$owner#$name;"

        override val kind: ShakeType.Kind
            get() = ShakeType.Kind.GENERIC

        override fun castableTo(other: ShakeType): Boolean = super.castableTo(other)

        override fun compatibleTo(other: ShakeType): Boolean = super<ShakeType.Generic>.compatibleTo(other)

        override fun compatibilityDistance(other: ShakeType): Int = super.compatibilityDistance(other)

        override fun toJson(): Map<String, Any?> = mapOf("type" to "generic", "name" to name)
    }

    class Lambda(
        name: String,
        val parameters: List<CreationShakeParameter>,
        val returnType: CreationShakeType,
    ) : CreationShakeType(name) {

        override val kind: ShakeType.Kind
            get() = ShakeType.Kind.LAMBDA

        override fun castableTo(other: ShakeType): Boolean {
            // TODO: Check parameters
            return other is Lambda && other.parameters.size == parameters.size && other.returnType.castableTo(returnType)
        }

        override fun compatibleTo(other: ShakeType): Boolean {
            // TODO: Check parameters
            return other is Lambda && returnType.compatibleTo(other.returnType)
        }

        override fun compatibilityDistance(other: ShakeType): Int {
            // TODO: Check parameters
            return if (other is Lambda) returnType.compatibilityDistance(other.returnType) else -1
        }

        override fun toJson(): Map<String, Any?> = mapOf(
            "type" to "lambda",
            "parameters" to parameters.map { it.toJson() },
            "returnType" to returnType.toJson(),
        )

        override val qualifiedName: String
            get() = "(${parameters.joinToString(", ") { it.type.qualifiedName }} -> ${returnType.qualifiedName})"
    }

    object Primitives {
        val BOOLEAN = Primitive.BOOLEAN
        val NULL = Primitive.NULL
        val BYTE = Primitive.BYTE
        val SHORT = Primitive.SHORT
        val INT = Primitive.INT
        val LONG = Primitive.LONG
        val FLOAT = Primitive.FLOAT
        val DOUBLE = Primitive.DOUBLE
        val UNSIGNED_BYTE = Primitive.UNSIGNED_BYTE
        val UNSIGNED_SHORT = Primitive.UNSIGNED_SHORT
        val UNSIGNED_INT = Primitive.UNSIGNED_INT
        val UNSIGNED_LONG = Primitive.UNSIGNED_LONG
        val UBYTE = UNSIGNED_BYTE
        val USHORT = UNSIGNED_SHORT
        val UINT = UNSIGNED_INT
        val ULONG = UNSIGNED_LONG
        val CHAR = Primitive.CHAR
        val VOID = Primitive.VOID
        val DYNAMIC = Primitive.DYNAMIC
        val UNKNOWN = Primitive.DYNAMIC // TODO: Change this
    }

    companion object {

        fun objectType(clazz: CreationShakeClass, genericArguments: List<CreationShakeType>? = null): CreationShakeType = Object(clazz, genericArguments)
    }
}

fun ShakeType?.isCreation() = this is CreationShakeType
fun ShakeType?.asCreation() = this as CreationShakeType

internal class TypeStorage(
    val type: String,
    val generics: List<TypeStorage>? = null,
) {

    fun resolve(scope: CreationShakeScope): CreationShakeType {
        val generics = generics?.map { it.resolve(scope) }
        return scope.getType(type, generics)
    }

    fun resolve(prj: CreationShakeProject): CreationShakeType = when (
        type
    ) {
        "byte" -> CreationShakeType.Primitives.BYTE
        "short" -> CreationShakeType.Primitives.SHORT
        "int" -> CreationShakeType.Primitives.INT
        "long" -> CreationShakeType.Primitives.LONG
        "float" -> CreationShakeType.Primitives.FLOAT
        "double" -> CreationShakeType.Primitives.DOUBLE
        "ubyte" -> CreationShakeType.Primitives.UBYTE
        "ushort" -> CreationShakeType.Primitives.USHORT
        "uint" -> CreationShakeType.Primitives.UINT
        "ulong" -> CreationShakeType.Primitives.ULONG
        "boolean" -> CreationShakeType.Primitives.BOOLEAN
        "char" -> CreationShakeType.Primitives.CHAR
        "dynamic" -> CreationShakeType.Primitives.DYNAMIC
        "void" -> CreationShakeType.Primitives.VOID
        else -> {
            val generics = generics?.map { it.resolve(prj) }
            val clazz = prj.getClass(type) ?: throw IllegalArgumentException("Class $type not found")
            CreationShakeType.objectType(clazz, generics)
        }
    }

    companion object {

        private val Byte = TypeStorage("byte")
        private val Short = TypeStorage("short")
        private val Int = TypeStorage("int")
        private val Long = TypeStorage("long")
        private val UByte = TypeStorage("ubyte")
        private val UShort = TypeStorage("ushort")
        private val UInt = TypeStorage("uint")
        private val ULong = TypeStorage("ulong")
        private val Float = TypeStorage("float")
        private val Double = TypeStorage("double")
        private val Boolean = TypeStorage("boolean")
        private val Char = TypeStorage("char")
        private val Dynamic = TypeStorage("dynamic")
        private val Void = TypeStorage("void")

        fun from(type: ShakeVariableType): TypeStorage = TypeStorage(
            type.namespace.toArray().joinToString("."),
            type.genericTypes?.map { from(it) },
        )

        fun from(type: TypeDescriptor): TypeStorage = when (type) {
            is TypeDescriptor.ByteType -> Byte
            is TypeDescriptor.ShortType -> Short
            is TypeDescriptor.IntType -> Int
            is TypeDescriptor.LongType -> Long
            is TypeDescriptor.UnsignedByteType -> UByte
            is TypeDescriptor.UnsignedShortType -> UShort
            is TypeDescriptor.UnsignedIntType -> UInt
            is TypeDescriptor.UnsignedLongType -> ULong
            is TypeDescriptor.FloatType -> Float
            is TypeDescriptor.DoubleType -> Double
            is TypeDescriptor.BooleanType -> Boolean
            is TypeDescriptor.CharType -> Char
            is TypeDescriptor.DynamicType -> Dynamic
            is TypeDescriptor.VoidType -> Void
            is TypeDescriptor.ArrayType -> TypeStorage(
                "shake/lang/Array",
                listOf(from(type.type)),
            )
            is TypeDescriptor.ObjectType -> {
                val genericTypes = type.genericTypes.map { from(it) }
                TypeStorage(
                    type.className,
                    genericTypes.ifEmpty { null },
                )
            }
            else -> throw IllegalArgumentException("Type $type not supported")
        }

        fun from(type: String): TypeStorage = from(TypeDescriptor.parse(type))
    }
}

class CreationGenericMap(
    map: Map<CreationShakeType.Generic, CreationShakeType>,
) : GenericMap<CreationShakeType.Generic, CreationShakeType>(map),
    Map<CreationShakeType.Generic, CreationShakeType>
