package compreter.parsertree;

import compreter.Symbol;

public class UnaryExpression extends Tree {
	Symbol operator;
	Tree operand;
	boolean isPostFix = false;
	
	public UnaryExpression(Symbol operator, Tree operand){
		this.operator = operator;
		this.operand = operand;
		this.place = Tree.getNextTemp();
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
	
	public String getCode(){
		String str = this.printLineNumber(true) + 
				this.operand.getCode() + this.place + " := ";
		
		if(!this.isPostFix){
			str += this.operator.getValue() + this.place;
		}
		else{
			str += this.place + this.operator.getValue();
		}
		
		return str + "\n";
	}
	
	public int tLineCount(){
		return this.operand.tLineCount() + 1;
	}
}
