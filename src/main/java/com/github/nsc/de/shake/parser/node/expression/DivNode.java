package com.github.nsc.de.shake.parser.node.expression;

import com.github.nsc.de.shake.parser.node.ValuedNode;
import com.github.nsc.de.shake.util.characterinput.position.PositionMap;

public class DivNode extends ExpressionNode {
    public DivNode(PositionMap map, ValuedNode left, ValuedNode right, int operatorPosition) {
        super(map, left, right, operatorPosition);
    }

    @Override
    public char getOperator() {
        return '/';
    }
}
