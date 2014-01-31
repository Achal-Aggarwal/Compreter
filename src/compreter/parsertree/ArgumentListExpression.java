package compreter.parsertree;

public class ArgumentListExpression extends Tree {
	Tree item = null;
	ArgumentListExpression items = null;
	
	public ArgumentListExpression(Tree item){
		this.item = item;
	}
	
	public ArgumentListExpression(Tree item, ArgumentListExpression items){
		this.item = item;
		this.items = items;
	}
}
