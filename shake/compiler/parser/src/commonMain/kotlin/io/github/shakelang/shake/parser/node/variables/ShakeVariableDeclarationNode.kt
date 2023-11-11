package io.github.shakelang.shake.parser.node.variables

import io.github.shakelang.shake.parser.node.*
import io.github.shakelang.shake.util.parseutils.characters.position.PositionMap
import kotlin.jvm.JvmOverloads

class ShakeVariableDeclarationNode @JvmOverloads constructor(
    map: PositionMap,
    val expandedType: ShakeVariableType?,
    val name: String,
    val type: ShakeVariableType = ShakeVariableType.DYNAMIC,
    val value: ShakeValuedNode? = null,
    val access: ShakeAccessDescriber = ShakeAccessDescriber.PACKAGE,
    val isStatic: Boolean,
    val isFinal: Boolean,
    val isNative: Boolean,
    val isConst: Boolean,
    val isOverride: Boolean,
    val isInline: Boolean
) : ShakeValuedStatementNodeImpl(map), ShakeFileChildNode {

    override fun toJson(): Map<String, *> =
        mapOf(
            "name" to "VariableDivAssignmentNode",
            "variable_name" to name,
            "type" to type.toString(),
            "access" to access.toString(),
            "assignment" to this.value?.toJson(),
            "is_static" to isStatic,
            "is_final" to isFinal
        )
}
