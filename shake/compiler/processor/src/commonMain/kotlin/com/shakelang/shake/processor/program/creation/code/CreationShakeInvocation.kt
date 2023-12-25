package com.shakelang.shake.processor.program.creation.code

import com.shakelang.shake.processor.program.creation.CreationShakeMethod
import com.shakelang.shake.processor.program.creation.code.statements.CreationShakeStatement
import com.shakelang.shake.processor.program.creation.code.values.CreationShakeValue
import com.shakelang.shake.processor.program.types.ShakeProject
import com.shakelang.shake.processor.program.types.ShakeType
import com.shakelang.shake.processor.program.types.code.ShakeInvocation
import com.shakelang.shake.processor.program.types.code.ShakeInvokable

class CreationShakeInvocation
private constructor(
    override val project: ShakeProject,
    override val callable: ShakeInvokable,
    override val arguments: List<CreationShakeValue>,
    override val parent: CreationShakeValue? = null
) : CreationShakeValue, CreationShakeStatement, ShakeInvocation {

    override val type: ShakeType
        get() = callable.returnType

    override val name get() = if (callable is CreationShakeMethod) callable.name else "anonymous"
    override val isAnonymous get() = callable !is CreationShakeMethod

    override fun toJson(): Map<String, Any?> {
        return mapOf(
            "type" to "invocation",
            "callable" to callable.qualifiedName,
            "arguments" to arguments.map { it.toJson() },
            "parent" to parent?.toJson()
        )
    }

    companion object {
        fun create(
            project: ShakeProject,
            callable: ShakeInvokable,
            arguments: List<CreationShakeValue>,
            parent: CreationShakeValue? = null
        ): CreationShakeInvocation {
            // If the callable is an extension function, the parent will be the first argument
            if (callable is CreationShakeMethod && callable.expanding != null && parent != null) {
                return CreationShakeInvocation(project, callable, listOf(parent) + arguments, null)
            }
            return CreationShakeInvocation(project, callable, arguments, parent)
        }
    }
}
