package compreter.parsertree;

import compreter.Symbol;

public class PrimaryExpression extends Tree {
	Symbol symbol = null;
	Tree expression = null;
	
	public PrimaryExpression(Symbol s){
		this.symbol = s;
		this.place = this.symbol.getValue();
	}
	
	public String toString(){
		if(symbol != null)return symbol.getValue();
		else return expression.toString();
	}
}
