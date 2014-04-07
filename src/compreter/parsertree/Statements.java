package compreter.parsertree;

import java.util.ArrayList;

public class Statements extends Tree{
	Tree statement = null,
			statements = null;
	
	public Statements(Tree statement, Tree statements){
		this.statement = statement;
		this.statements = statements;
	}
	
	public void setReturnAddresses(String returnAddress){
		if(statement instanceof ReturnStatement)
			((ReturnStatement) statement).setReturnAddress(returnAddress);
		
		if(statements instanceof ReturnStatement)
			((ReturnStatement) statements).setReturnAddress(returnAddress);
		
		if(statements instanceof Statements)
			((Statements) statements).setReturnAddresses(returnAddress);
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
	
	public String getCode(){
		String str = "";
		
		if(statement.toString()!=null)
			str = statement.getCode();
		
		if(statements.toString()!=null){
			str += statements.getCode();
		}
		
		return str;
	}
	
	public String getLabelCode(){
		String str = "";
		
		if(statement.toString()!=null)
			str = statement.getLabelCode();
		
		if(statements.toString()!=null){
			str += statements.getLabelCode();
		}
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = "";
		
		if(statement.toString()!=null)
			str = statement.getSimpleCode();
		
		if(statements.toString()!=null){
			str += statements.getSimpleCode();
		}
		
		return str;
	}
	
	public int tLineCount(){
		int tCount = 0;
		
		if(statement.toString()!=null)
			tCount = statement.tLineCount();
		
		if(statements.toString()!=null){
			tCount += statements.tLineCount();
		}
		
		return tCount;
	}
	
	public ArrayList<Tree> findLoopControlStatements(){
		ArrayList<Tree> loopControlstatements = new ArrayList<Tree>();
		
		if(this.statement instanceof LoopControlStatement)
			loopControlstatements.add(this.statement);
		
		if(this.statements instanceof LoopControlStatement)
			loopControlstatements.add(this.statements);
		
		if(this.statements  instanceof Statements)
			loopControlstatements.addAll(((Statements)this.statements).findLoopControlStatements());

		return loopControlstatements;
	}
}
