package compreter.parsertree;

public class VariableDeclarationStatement extends Tree {
	Tree varList = null;
	
	public VariableDeclarationStatement(Tree varlist){
		this.varList = varlist;
	}
	
	public String toString(){
		return "var " + varList.toString() + ";";
	}
}
