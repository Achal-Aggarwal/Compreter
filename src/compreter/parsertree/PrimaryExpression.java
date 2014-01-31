package compreter.parsertree;

import compreter.Symbol;

public class PrimaryExpression extends Tree {
	Symbol symbol = null;
	Tree expression = null;
	
	public PrimaryExpression(Symbol s){
		this.symbol = s;
	}
	
	public PrimaryExpression(Tree e){
		this.expression = e;
	}
	
}
