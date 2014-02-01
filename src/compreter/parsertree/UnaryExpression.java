package compreter.parsertree;

import compreter.Symbol;

public class UnaryExpression extends Tree {
	Symbol operator;
	Tree operand;
	boolean isPostFix = false;
	
	public UnaryExpression(Symbol operator, Tree operand){
		this.operator = operator;
		this.operand = operand;
	}
	
	public UnaryExpression(Symbol operator, Tree operand, boolean isPostFix) {
		this.operator = operator;
		this.operand = operand;
		this.isPostFix = isPostFix;
	}

	public String toString(){
		if(!isPostFix)return operator.getValue() + operand.toString();
		else return operand.toString() + operator.getValue();
	}
}
