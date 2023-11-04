package io.github.shakelang.shake.parser.node.expression

import io.github.shakelang.shake.parser.node.ShakeValuedNode
import io.github.shakelang.shake.util.parseutils.characters.position.PositionMap

class ShakePowNode(map: PositionMap, left: ShakeValuedNode, right: ShakeValuedNode, operatorPosition: Int) :
    ShakeExpressionNode(map, left, right, operatorPosition) {
    override val operator: Char
        get() = '^'

    override fun toJson(): Map<String, *> = mapOf("name" to "PowNode", "left" to left, "right" to right)
}