package compreter.parsertree;

public class Statements extends Tree{
	Tree statement = null,
			statements = null;
	
	public Statements(Tree statement, Tree statements){
		this.statement = statement;
		this.statements = statements;
	}
	
	public String toString(){
		String str = null;
		
		if(statement.toString()!=null)
			str = statement.toString();
		
		if(statements.toString()!=null){
			if(statement.toString()!=null)
				str += ";";
			str += statements.toString();
		}
		
		return str;
	}
}
