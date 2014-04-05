package compreter.parsertree;

public class ArgumentListExpression extends Tree {
	Tree item = null;
	ArgumentListExpression items = null;
	static String argList = "";

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
	
	public String getCode(){
		String str = "";
		String itemCode = item.getCode();
		
		if(itemCode != ""){
			String nextTemp = Tree.getNextTemp();
			str = item.getCode() +
					this.printLineNumber(true) + 
					nextTemp + " := " + item.place + "\n" +
					this.printLineNumber(true) + "push := " + nextTemp + "\n";
		}
		else{
			str = this.printLineNumber(true) + 
				"push := " + item.place + "\n";
		}
		
		ArgumentListExpression itemsList = this.items;
		
		if(itemsList != null)
			str = str + itemsList.getCode();
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = "";
		String itemCode = item.getCode();

		if(itemCode != ""){
			String nextTemp = Tree.getNextTemp();
			str = item.getCode() +
					this.printLineNumber(true) + 
					nextTemp + " := " + item.place + "\n";
			ArgumentListExpression.argList += nextTemp  + ", ";
		}
		else{
			ArgumentListExpression.argList += item.place + ", ";
		}
		
		ArgumentListExpression itemsList = this.items;
		
		if(itemsList != null){
			str = str + itemsList.getSimpleCode();
		}
			
		
		return str;
	}
	
	public String getArgumentList(){ 
		return ArgumentListExpression.argList.substring(0,ArgumentListExpression.argList.length() - 2);
	}
	
	public int tLineCode(){
		int count = item.tLineCount() + 1;
		
		ArgumentListExpression itemsList = this.items;
		
		if(itemsList != null)
			count += itemsList.tLineCode();
		
		return count;
	}
}
