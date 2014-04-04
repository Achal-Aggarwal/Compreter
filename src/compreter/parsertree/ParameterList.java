package compreter.parsertree;

import compreter.Symbol;

public class ParameterList extends Tree {
	Identifier identifier = null;
	Tree identifiers = null;
	
	public ParameterList(Symbol identifier, Tree identifiers){
		this.identifier = new Identifier(identifier.getValue());
		it.addIdentifier(this.identifier);
		this.identifiers = identifiers;
	}
	
	public ParameterList(Symbol identifier) {
		this.identifier = new Identifier(identifier.getValue());
		it.addIdentifier(this.identifier);
	}

	public String toString(){
		String str = "";
		
		if(identifiers != null)
			str += identifiers.toString() + ",";
		
		str += identifier.getNewName(false);
		
		return str;
	}
	
	public String getCode(){
		String str = "";
		
		if(identifiers != null)
			str += identifiers.getCode();
		
		str += this.printLineNumber(true) + identifier.getNewName() + " := pop" + "\n";
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = "";
		
		if(identifiers != null)
			str += identifiers.getSimpleCode() + ", ";
		
		str += identifier.getNewName();
		
		return str;
	}
	
	public int tLineCount(){
		int  count= 1;
		
		if(identifiers != null)
			count += identifiers.tLineCount();
		
		return count;
	}
}
