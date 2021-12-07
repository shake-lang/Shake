package io.github.shakelang.jvmlib.constants

class JavaClassDoubleConstant(val value: Double) : JavaClassConstant() {

    override val tag: Byte = 5
    override fun toJson() = super.toJson().with("value", value)
    override val name: String get() = "JavaClassDoubleConstant"

}