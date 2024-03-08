package com.shakelang.shake.bytecode.interpreter.format.pool

import com.shakelang.util.io.streaming.input.bytes.dataStream
import com.shakelang.util.primitives.bytes.toBytes
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ConstantPoolEntryTests : FreeSpec(
    {

        "utf8 constant to bytes" {
            val constant = ConstantPoolEntry.Utf8Constant("test")
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(1, 0, 4, *"test".toBytes())
        }

        "utf8 constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.Utf8Constant.fromStreamIgnoreIdentifier(byteArrayOf(0, 4, *"test".toBytes()).dataStream())
            constant.value shouldBe "test"
        }

        "utf8 constant from bytes" {
            val constant = ConstantPoolEntry.Utf8Constant.fromStream(byteArrayOf(1, 0, 4, *"test".toBytes()).dataStream())
            constant.value shouldBe "test"
        }

        "utf8 constant equals" {
            val constant1 = ConstantPoolEntry.Utf8Constant("test")
            val constant2 = ConstantPoolEntry.Utf8Constant("test")
            val constant3 = ConstantPoolEntry.Utf8Constant("test2")
            val constant4 = ConstantPoolEntry.Utf8Constant("test")
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "utf8 constant hashcode" {
            val constant1 = ConstantPoolEntry.Utf8Constant("test")
            val constant2 = ConstantPoolEntry.Utf8Constant("test")
            val constant3 = ConstantPoolEntry.Utf8Constant("test2")
            val constant4 = ConstantPoolEntry.Utf8Constant("test")
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "byte constant to bytes" {
            val constant = ConstantPoolEntry.ByteConstant(1)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(2, 1)
        }

        "byte constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.ByteConstant.fromStreamIgnoreIdentifier(byteArrayOf(1).dataStream())
            constant.value shouldBe 1
        }

        "byte constant from bytes" {
            val constant = ConstantPoolEntry.ByteConstant.fromStream(byteArrayOf(2, 1).dataStream())
            constant.value shouldBe 1
        }

        "byte constant equals" {
            val constant1 = ConstantPoolEntry.ByteConstant(1)
            val constant2 = ConstantPoolEntry.ByteConstant(1)
            val constant3 = ConstantPoolEntry.ByteConstant(2)
            val constant4 = ConstantPoolEntry.ByteConstant(1)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "byte constant hashcode" {
            val constant1 = ConstantPoolEntry.ByteConstant(1)
            val constant2 = ConstantPoolEntry.ByteConstant(1)
            val constant3 = ConstantPoolEntry.ByteConstant(2)
            val constant4 = ConstantPoolEntry.ByteConstant(1)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "shorts constant to bytes" {
            val constant = ConstantPoolEntry.ShortConstant(1)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(3, 0, 1)
        }

        "shorts constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.ShortConstant.fromStreamIgnoreIdentifier(byteArrayOf(0, 1).dataStream())
            constant.value shouldBe 1
        }

        "shorts constant from bytes" {
            val constant = ConstantPoolEntry.ShortConstant.fromStream(byteArrayOf(3, 0, 1).dataStream())
            constant.value shouldBe 1
        }

        "shorts constant equals" {
            val constant1 = ConstantPoolEntry.ShortConstant(1)
            val constant2 = ConstantPoolEntry.ShortConstant(1)
            val constant3 = ConstantPoolEntry.ShortConstant(2)
            val constant4 = ConstantPoolEntry.ShortConstant(1)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "shorts constant hashcode" {
            val constant1 = ConstantPoolEntry.ShortConstant(1)
            val constant2 = ConstantPoolEntry.ShortConstant(1)
            val constant3 = ConstantPoolEntry.ShortConstant(2)
            val constant4 = ConstantPoolEntry.ShortConstant(1)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "int constant to bytes" {
            val constant = ConstantPoolEntry.IntConstant(1)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(4, 0, 0, 0, 1)
        }

        "int constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.IntConstant.fromStreamIgnoreIdentifier(byteArrayOf(0, 0, 0, 1).dataStream())
            constant.value shouldBe 1
        }

        "int constant from bytes" {
            val constant = ConstantPoolEntry.IntConstant.fromStream(byteArrayOf(4, 0, 0, 0, 1).dataStream())
            constant.value shouldBe 1
        }

        "int constant equals" {
            val constant1 = ConstantPoolEntry.IntConstant(1)
            val constant2 = ConstantPoolEntry.IntConstant(1)
            val constant3 = ConstantPoolEntry.IntConstant(2)
            val constant4 = ConstantPoolEntry.IntConstant(1)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "int constant hashcode" {
            val constant1 = ConstantPoolEntry.IntConstant(1)
            val constant2 = ConstantPoolEntry.IntConstant(1)
            val constant3 = ConstantPoolEntry.IntConstant(2)
            val constant4 = ConstantPoolEntry.IntConstant(1)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "long constant to bytes" {
            val constant = ConstantPoolEntry.LongConstant(1)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(5, 0, 0, 0, 0, 0, 0, 0, 1)
        }

        "long constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.LongConstant.fromStreamIgnoreIdentifier(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 1).dataStream())
            constant.value shouldBe 1
        }

        "long constant from bytes" {
            val constant = ConstantPoolEntry.LongConstant.fromStream(byteArrayOf(5, 0, 0, 0, 0, 0, 0, 0, 1).dataStream())
            constant.value shouldBe 1
        }

        "long constant equals" {
            val constant1 = ConstantPoolEntry.LongConstant(1)
            val constant2 = ConstantPoolEntry.LongConstant(1)
            val constant3 = ConstantPoolEntry.LongConstant(2)
            val constant4 = ConstantPoolEntry.LongConstant(1)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "long constant hashcode" {
            val constant1 = ConstantPoolEntry.LongConstant(1)
            val constant2 = ConstantPoolEntry.LongConstant(1)
            val constant3 = ConstantPoolEntry.LongConstant(2)
            val constant4 = ConstantPoolEntry.LongConstant(1)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "float constant to bytes" {
            val constant = ConstantPoolEntry.FloatConstant(1f)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(6, *(1f.toBits().toBytes()))
        }

        "float constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.FloatConstant.fromStreamIgnoreIdentifier(byteArrayOf(*(1f.toBytes())).dataStream())
            constant.value shouldBe 1f
        }

        "float constant from bytes" {
            val constant = ConstantPoolEntry.FloatConstant.fromStream(byteArrayOf(6, *(1f.toBytes())).dataStream())
            constant.value shouldBe 1f
        }

        "float constant equals" {
            val constant1 = ConstantPoolEntry.FloatConstant(1f)
            val constant2 = ConstantPoolEntry.FloatConstant(1f)
            val constant3 = ConstantPoolEntry.FloatConstant(2f)
            val constant4 = ConstantPoolEntry.FloatConstant(1f)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "float constant hashcode" {
            val constant1 = ConstantPoolEntry.FloatConstant(1f)
            val constant2 = ConstantPoolEntry.FloatConstant(1f)
            val constant3 = ConstantPoolEntry.FloatConstant(2f)
            val constant4 = ConstantPoolEntry.FloatConstant(1f)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "doubles constant to bytes" {
            val constant = ConstantPoolEntry.DoubleConstant(1.0)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(7, *(1.0.toBits().toBytes()))
        }

        "doubles constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.DoubleConstant.fromStreamIgnoreIdentifier(byteArrayOf(*(1.0.toBytes())).dataStream())
            constant.value shouldBe 1.0
        }

        "doubles constant from bytes" {
            val constant = ConstantPoolEntry.DoubleConstant.fromStream(byteArrayOf(7, *(1.0.toBytes())).dataStream())
            constant.value shouldBe 1.0
        }

        "doubles constant equals" {
            val constant1 = ConstantPoolEntry.DoubleConstant(1.0)
            val constant2 = ConstantPoolEntry.DoubleConstant(1.0)
            val constant3 = ConstantPoolEntry.DoubleConstant(2.0)
            val constant4 = ConstantPoolEntry.DoubleConstant(1.0)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "doubles constant hashcode" {
            val constant1 = ConstantPoolEntry.DoubleConstant(1.0)
            val constant2 = ConstantPoolEntry.DoubleConstant(1.0)
            val constant3 = ConstantPoolEntry.DoubleConstant(2.0)
            val constant4 = ConstantPoolEntry.DoubleConstant(1.0)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "class constant to bytes" {
            val constant = ConstantPoolEntry.ClassConstant(1)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(8, 0, 0, 0, 1)
        }

        "class constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.ClassConstant.fromStreamIgnoreIdentifier(byteArrayOf(0, 0, 0, 1).dataStream())
            constant.identifier shouldBe 1
        }

        "class constant from bytes" {
            val constant = ConstantPoolEntry.ClassConstant.fromStream(byteArrayOf(8, 0, 0, 0, 1).dataStream())
            constant.identifier shouldBe 1
        }

        "class constant equals" {
            val constant1 = ConstantPoolEntry.ClassConstant(1)
            val constant2 = ConstantPoolEntry.ClassConstant(1)
            val constant3 = ConstantPoolEntry.ClassConstant(2)
            val constant4 = ConstantPoolEntry.ClassConstant(1)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "class constant hashcode" {
            val constant1 = ConstantPoolEntry.ClassConstant(1)
            val constant2 = ConstantPoolEntry.ClassConstant(1)
            val constant3 = ConstantPoolEntry.ClassConstant(2)
            val constant4 = ConstantPoolEntry.ClassConstant(1)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }

        "string constant to bytes" {
            val constant = ConstantPoolEntry.StringConstant(1)
            val bytes = constant.dump()
            bytes shouldBe byteArrayOf(9, 0, 0, 0, 1)
        }

        "string constant from bytes ignore identifier" {
            val constant = ConstantPoolEntry.StringConstant.fromStreamIgnoreIdentifier(byteArrayOf(0, 0, 0, 1).dataStream())
            constant.identifier shouldBe 1
        }

        "string constant from bytes" {
            val constant = ConstantPoolEntry.StringConstant.fromStream(byteArrayOf(9, 0, 0, 0, 1).dataStream())
            constant.identifier shouldBe 1
        }

        "string constant equals" {
            val constant1 = ConstantPoolEntry.StringConstant(1)
            val constant2 = ConstantPoolEntry.StringConstant(1)
            val constant3 = ConstantPoolEntry.StringConstant(2)
            val constant4 = ConstantPoolEntry.StringConstant(1)
            constant1 shouldBe constant2
            constant1 shouldNotBe constant3
            constant1 shouldBe constant4
        }

        "string constant hashcode" {
            val constant1 = ConstantPoolEntry.StringConstant(1)
            val constant2 = ConstantPoolEntry.StringConstant(1)
            val constant3 = ConstantPoolEntry.StringConstant(2)
            val constant4 = ConstantPoolEntry.StringConstant(1)
            constant1.hashCode() shouldBe constant2.hashCode()
            constant1.hashCode() shouldNotBe constant3.hashCode()
            constant1.hashCode() shouldBe constant4.hashCode()
        }
    },
)
