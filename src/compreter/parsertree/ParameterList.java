package compreter.parsertree;

public class ParameterList extends Tree {
	Tree identifier = null;
	Tree identifiers = null;
	
	public ParameterList(Tree identifier, Tree identifiers){
		this.identifier = identifier;
		this.identifiers = identifiers;
	}
	
	public String toString(){
		return identifier.toString() + "," + identifiers.toString();
	}
}
