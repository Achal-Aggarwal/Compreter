package compreter.parsertree;

import compreter.Symbol;

public class UnaryExpression extends Tree {
	Symbol operator;
	Tree operand;
	
	public UnaryExpression(Symbol operator, Tree operand){
		this.operator = operator;
		this.operand = operand;
	}
}
