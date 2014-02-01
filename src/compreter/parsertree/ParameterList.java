package compreter.parsertree;

public class ParameterList extends Tree {
	Tree identifier = null;
	Tree identifiers = null;
	
	public ParameterList(Tree identifier, Tree identifiers){
		this.identifier = identifier;
		this.identifiers = identifiers;
	}
	
	public ParameterList(Tree identifier) {
		this.identifier = identifier;
	}

	public String toString(){
		String str = identifier.toString();
		
		if(identifiers != null)
			str += "," + identifiers.toString();
		
		return str;
	}
	
	public String getCode(){
		String str = this.printLineNumber(true) + identifier.place + " := pull" + "\n";
		
		if(identifiers != null)
			str += identifiers.getCode();
		
		return str;
	}
	
	public int tLineCount(){
		int  count= 1;
		
		if(identifiers != null)
			count += identifiers.tLineCount();
		
		return count;
	}
}
