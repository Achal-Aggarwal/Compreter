package compreter.parsertree;

import compreter.Symbol;

public class MemberExpression extends Tree {
	Tree primaryExpression = null;
	Tree expression = null;
	
	public MemberExpression(Tree pe){
		this.primaryExpression = pe;
	}
	
	public MemberExpression(Tree pe, Tree e){
		this.primaryExpression = pe;
		this.expression = e;
	}
}
