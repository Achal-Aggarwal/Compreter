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
			str = str + "," + itemsList.toString();
		
		return str;
	}
	
	public String getCode(){
		String str = item.getCode();
		
		if(this.items != null)
			str += this.items.getCode();
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = item.getSimpleCode();
		
		if(this.items != null)
			str += this.items.getSimpleCode();
		
		return str;
	}
	
	public int tLineCount(){
		int count = item.tLineCount();
		
		if(this.items != null)
			count += this.items.tLineCount();
		
		return count;
	}
}
