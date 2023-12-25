package com.shakelang.shake.processor.program.creation

import com.shakelang.shake.parser.node.ShakeAccessDescriber
import com.shakelang.shake.parser.node.objects.ShakeConstructorDeclarationNode
import com.shakelang.shake.processor.ShakeASTProcessor
import com.shakelang.shake.processor.ShakeProcessor
import com.shakelang.shake.processor.program.creation.code.CreationShakeCode
import com.shakelang.shake.processor.program.creation.code.statements.CreationShakeVariableDeclaration
import com.shakelang.shake.processor.program.types.ShakeConstructor
import com.shakelang.shake.processor.program.types.ShakeType

open class CreationShakeConstructor(
    override val clazz: CreationShakeClass,
    override val body: CreationShakeCode,
    override val isStrict: Boolean,
    override val isPrivate: Boolean,
    override val isProtected: Boolean,
    override val isPublic: Boolean,
    override val isNative: Boolean,
    override val name: String? = null,
    override var parameters: List<CreationShakeParameter>
) : ShakeConstructor {

    override val scope: CreationShakeScope = ShakeConstructorScope()

    val qualifiedName: String
        get() = "${clazz.qualifierPrefix}+${name ?: "default"}"

    val parameterTypes: List<ShakeType> get() = parameters.map { it.type }
    val signature: String
        get() = "${name ?: "default"}(${parameterTypes.joinToString(",") { it.qualifiedName }})N"
    val qualifiedSignature: String
        get() = "$qualifiedName(${parameterTypes.joinToString(",") { it.qualifiedName }})N"

    override fun phase3() {
        debug("phases", "Phase 4 of constructor $qualifiedSignature")
    }

    override fun phase4() {
        debug("phases", "Phase 4 of constructor $qualifiedSignature")
        if (body is CreationShakeCode.ShakeLateProcessCode) {
            (body as CreationShakeCode.ShakeLateProcessCode).process(
                scope
            )
        }
    }

    inner class ShakeConstructorScope : CreationShakeScope() {

        override val uniqueName: String get() = qualifiedSignature
        override val parent: CreationShakeScope = clazz.instanceScope
        override val project get() = clazz.prj
        val variables = mutableListOf<CreationShakeVariableDeclaration>()

        override fun get(name: String): CreationShakeAssignable? {
            return variables.find { it.name == name } ?: parent.get(name)
        }

        override fun set(value: CreationShakeDeclaration) {
            if (value !is CreationShakeVariableDeclaration) throw IllegalArgumentException("Only variable declarations can be set in a method scope")
            if (variables.any { it.name == value.name }) throw IllegalArgumentException("Variable ${value.name} already exists in this scope")
            variables.add(value)
        }

        override fun getFunctions(name: String): List<CreationShakeMethod> {
            return parent.getFunctions(name)
        }

        override fun setFunctions(function: CreationShakeMethod) {
            throw IllegalStateException("Cannot set function in method scope")
        }

        override fun getClass(name: String): CreationShakeClass? {
            return parent.getClass(name)
        }

        override fun setClass(klass: CreationShakeClass) {
            throw IllegalStateException("Cannot set class in method scope")
        }

        override val processor: ShakeASTProcessor
            get() = parent.processor
    }

    companion object {

        val debug = ShakeProcessor.debug.child("creation", "constructor")

        fun from(
            clazz: CreationShakeClass,
            parentScope: CreationShakeScope,
            node: ShakeConstructorDeclarationNode
        ): CreationShakeConstructor {
            return CreationShakeConstructor(
                clazz,
                CreationShakeCode.fromTree(node.body),
                false,
                node.access == ShakeAccessDescriber.PRIVATE,
                node.access == ShakeAccessDescriber.PROTECTED,
                node.access == ShakeAccessDescriber.PUBLIC,
                node.isNative,
                node.name,
                node.args.map { parentScope.getType(it.type) }.map { CreationShakeParameter(it.name, it) }
            )
        }
    }
}
