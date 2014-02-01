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
	
	public String toString(){
		String str = item.toString();
		ArgumentListExpression itemsList = this.items;
		
		if(itemsList != null)
			str = str + "," + itemsList.toString();
		
		return str;
	}
}
