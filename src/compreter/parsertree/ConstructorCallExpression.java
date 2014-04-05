package compreter.parsertree;

import compreter.Symbol;

public class ConstructorCallExpression extends Tree {
	Tree argumentListOpt = null;
	Identifier identifier = null;
	
	public ConstructorCallExpression(Symbol symbol){
		this.identifier = it.getIdentifier(symbol.getValue());
	}
	
	public ConstructorCallExpression(Symbol symbol, Tree argumentListOpt){
		this.identifier = it.getIdentifier(symbol.getValue());
		this.argumentListOpt = argumentListOpt;
	}
	
	public String toString(){
		String str = identifier.getNewName(false) + "(";
		if(argumentListOpt!=null)
			str += argumentListOpt.toString();
		str += ")";
		
		return str;
	}
	
	public String getCode(){
		String str = "";
		
		if(argumentListOpt!=null){
			str += argumentListOpt.getCode();
		}
		
		str += this.printLineNumber(true) + "push := " + String.valueOf(this.currentLineNumber + 2) + "\n";
		str += this.printLineNumber(true) + "goto := " + identifier.getNewName() + "\n";
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = "";
		
		if(argumentListOpt!=null){
			str += argumentListOpt.getSimpleCode();
		}
		
		str += this.printLineNumber(true) + "call := " + identifier.getNewName() + " ( " + ((ArgumentListExpression) argumentListOpt).getArgumentList() +" )" + "\n";
		this.place = "called " + identifier.getNewName();
		return str;
	}
}
