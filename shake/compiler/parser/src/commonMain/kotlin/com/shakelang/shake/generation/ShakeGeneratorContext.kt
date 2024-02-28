package com.shakelang.shake.generation

import com.shakelang.shake.parser.node.*
import com.shakelang.shake.parser.node.expression.*
import com.shakelang.shake.parser.node.factor.ShakeDoubleLiteralNode
import com.shakelang.shake.parser.node.factor.ShakeIntegerLiteralNode
import com.shakelang.shake.parser.node.factor.ShakeLogicalFalseLiteralNode
import com.shakelang.shake.parser.node.factor.ShakeLogicalTrueLiteralNode
import com.shakelang.shake.parser.node.functions.ShakeFunctionDeclarationNode
import com.shakelang.shake.parser.node.functions.ShakeInvocationNode
import com.shakelang.shake.parser.node.loops.ShakeDoWhileNode
import com.shakelang.shake.parser.node.loops.ShakeForNode
import com.shakelang.shake.parser.node.loops.ShakeWhileNode
import com.shakelang.shake.parser.node.objects.ShakeClassDeclarationNode
import com.shakelang.shake.parser.node.variables.*

@Suppress("unused")
abstract class ShakeGeneratorContext<T, C> : com.shakelang.shake.generation.ShakeGeneratorBase {
    fun visit(n: ShakeNodeImpl, context: C): T {
        if (n is ShakeBlockNode) return visitTree(n, context)
        if (n is ShakeDoubleLiteralNode) return visitDoubleNode(n, context)
        if (n is ShakeIntegerLiteralNode) return visitIntegerNode(n, context)
        if (n is ShakeAddNode) return visitAddNode(n, context)
        if (n is ShakeSubNode) return visitSubNode(n, context)
        if (n is ShakeMulNode) return visitMulNode(n, context)
        if (n is ShakeDivNode) return visitDivNode(n, context)
        if (n is ShakeModNode) return visitModNode(n, context)
        if (n is ShakePowNode) return visitPowNode(n, context)
        if (n is ShakeLocalDeclarationNode) return visitVariableDeclarationNode(n, context)
        if (n is ShakeVariableAddAssignmentNode) return visitVariableAddAssignmentNode(n, context)
        if (n is ShakeVariableSubAssignmentNode) return visitVariableSubAssignmentNode(n, context)
        if (n is ShakeVariableMulAssignmentNode) return visitVariableMulAssignmentNode(n, context)
        if (n is ShakeVariableDivAssignmentNode) return visitVariableDivAssignmentNode(n, context)
        if (n is ShakeVariableModAssignmentNode) return visitVariableModAssignmentNode(n, context)
        if (n is ShakeVariablePowAssignmentNode) return visitVariablePowAssignmentNode(n, context)
        if (n is ShakeValuedNode) return visitVariableAssignmentNode(n, context)
        if (n is ShakeVariableUsageNode) return visitVariableUsageNode(n, context)
        if (n is ShakeEqualNode) return visitEqEqualsNode(n, context)
        if (n is ShakeGreaterThanOrEqualNode) return visitBiggerEqualsNode(n, context)
        if (n is ShakeLessThanOrEqualNode) return visitSmallerEqualsNode(n, context)
        if (n is ShakeGreaterThanNode) return visitBiggerNode(n, context)
        if (n is ShakeLessThanNode) return visitSmallerNode(n, context)
        if (n is ShakeLogicalAndNode) return visitLogicalAndNode(n, context)
        if (n is ShakeLogicalOrNode) return visitLogicalOrNode(n, context)
        if (n is ShakeLogicalXOrNode) return visitLogicalXOrNode(n, context)
        if (n is ShakeWhileNode) return visitWhileNode(n, context)
        if (n is ShakeDoWhileNode) return visitDoWhileNode(n, context)
        if (n is ShakeForNode) return visitForNode(n, context)
        if (n is ShakeIfNode) return visitIfNode(n, context)
        if (n is ShakeFunctionDeclarationNode) return visitFunctionDeclarationNode(n, context)
        if (n is ShakeInvocationNode) return visitFunctionCallNode(n, context)
        if (n is ShakeIdentifierNode) return visitIdentifierNode(n, context)
        if (n is ShakeClassDeclarationNode) return visitClassDeclarationNode(n, context)
        if (n is ShakeLogicalTrueLiteralNode) return visitLogicalTrueNode(n, context)
        if (n is ShakeLogicalFalseLiteralNode) return visitLogicalFalseNode(n, context)
        if (n is ShakeImportNode) return visitImportNode(n, context)
        if (n is ShakeCastNode) return visitCastNode(n, context)
        throw Error("It looks like that node is not implemented in the generator: $n")
    }

    abstract fun visitTree(t: ShakeBlockNode?, context: C): T
    abstract fun visitDoubleNode(n: ShakeDoubleLiteralNode?, context: C): T
    abstract fun visitIntegerNode(n: ShakeIntegerLiteralNode?, context: C): T
    abstract fun visitAddNode(n: ShakeAddNode?, context: C): T
    abstract fun visitSubNode(n: ShakeSubNode?, context: C): T
    abstract fun visitMulNode(n: ShakeMulNode?, context: C): T
    abstract fun visitDivNode(n: ShakeDivNode?, context: C): T
    abstract fun visitModNode(n: ShakeModNode?, context: C): T
    abstract fun visitPowNode(n: ShakePowNode?, context: C): T
    abstract fun visitVariableDeclarationNode(n: ShakeLocalDeclarationNode?, context: C): T
    abstract fun visitVariableAssignmentNode(n: ShakeValuedNode?, context: C): T
    abstract fun visitVariableAddAssignmentNode(n: ShakeVariableAddAssignmentNode?, context: C): T
    abstract fun visitVariableSubAssignmentNode(n: ShakeVariableSubAssignmentNode?, context: C): T
    abstract fun visitVariableMulAssignmentNode(n: ShakeVariableMulAssignmentNode?, context: C): T
    abstract fun visitVariableDivAssignmentNode(n: ShakeVariableDivAssignmentNode?, context: C): T
    abstract fun visitVariableModAssignmentNode(n: ShakeVariableModAssignmentNode?, context: C): T
    abstract fun visitVariablePowAssignmentNode(n: ShakeVariablePowAssignmentNode?, context: C): T
    abstract fun visitVariableUsageNode(n: ShakeVariableUsageNode?, context: C): T
    abstract fun visitEqEqualsNode(n: ShakeEqualNode?, context: C): T
    abstract fun visitBiggerEqualsNode(n: ShakeGreaterThanOrEqualNode?, context: C): T
    abstract fun visitSmallerEqualsNode(n: ShakeLessThanOrEqualNode?, context: C): T
    abstract fun visitBiggerNode(n: ShakeGreaterThanNode?, context: C): T
    abstract fun visitSmallerNode(n: ShakeLessThanNode?, context: C): T
    abstract fun visitLogicalAndNode(n: ShakeLogicalAndNode?, context: C): T
    abstract fun visitLogicalOrNode(n: ShakeLogicalOrNode?, context: C): T
    abstract fun visitLogicalXOrNode(n: ShakeLogicalXOrNode?, context: C): T
    abstract fun visitWhileNode(n: ShakeWhileNode?, context: C): T
    abstract fun visitDoWhileNode(n: ShakeDoWhileNode?, context: C): T
    abstract fun visitForNode(n: ShakeForNode?, context: C): T
    abstract fun visitIfNode(n: ShakeIfNode?, context: C): T
    abstract fun visitFunctionDeclarationNode(n: ShakeFunctionDeclarationNode?, context: C): T
    abstract fun visitClassDeclarationNode(n: ShakeClassDeclarationNode?, context: C): T
    abstract fun visitFunctionCallNode(n: ShakeInvocationNode?, context: C): T
    abstract fun visitIdentifierNode(n: ShakeIdentifierNode?, context: C): T
    abstract fun visitLogicalTrueNode(n: ShakeLogicalTrueLiteralNode?, context: C): T
    abstract fun visitLogicalFalseNode(n: ShakeLogicalFalseLiteralNode?, context: C): T
    abstract fun visitImportNode(n: ShakeImportNode?, context: C): T
    abstract fun visitCastNode(n: ShakeCastNode?, context: C): T
}
