package compreter.parsertree;

import compreter.Symbol;

public class PrimaryExpression extends Tree {
	Identifier identifer = null;
	Tree expression = null;
	Symbol literal = null;
	
	public PrimaryExpression(Symbol s){
		if(Symbol.Id.IDENTIFIER_NAME == s.getCode()){
			if(it.isExist(s.getValue())){
				this.identifer = it.getIdentifier(s.getValue());
			} else {
				this.identifer = new Identifier(s.getValue(),-1,current_block);
				it.addIdentifier(this.identifer);
			}
			this.place = this.identifer.getNewName();
		}
		else{
			this.literal = s;
			this.place = s.getValue();
		}
	}
	
	public String getSimpleCode(){
		return this.getCode();
	}
	
	public String toString(){
		if(identifer != null)return this.identifer.getNewName(false);
		else if(literal != null)return this.literal.getValue();
		else return expression.toString();
	}
}
