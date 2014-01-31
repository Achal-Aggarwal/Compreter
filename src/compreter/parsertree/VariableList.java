package compreter.parsertree;

public class VariableList extends Tree {
	Tree item = null;
	VariableList items = null;
	
	public VariableList(Tree item){
		this.item = item;
	}
	
	public VariableList(Tree item, VariableList items){
		this.item = item;
		this.items = items;
	}
	
	public String toString(){
		String str = item.toString();
		VariableList itemsList = this.items;
		
		if(itemsList != null)
			str = str + ", " + itemsList.toString();
		
		return str;
	}
}
