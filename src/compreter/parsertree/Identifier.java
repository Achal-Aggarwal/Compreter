package compreter.parsertree;

public class Identifier {
	public static int nextInt = 0;
	static String nextName;
	static public boolean generateNames = true;
	int presentAt;
	String oldName, newName;
	String block;
	static String firstAvail = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ_";
	static String remainAvail = firstAvail + "0123456789";
	
	public Identifier(String name){
		this.oldName = name;
		this.presentAt = -1;
		this.newName = generateNames ? Identifier.getNextName() : name;
		this.block = "__main__";
	}
	
	public Identifier(String name, int lineNumber){
		this.oldName = name;
		this.presentAt = lineNumber;
		this.newName = generateNames ? Identifier.getNextName() : name;
		this.block = "__main__";
	}
	
	public Identifier(String name, int lineNumber, String block){
		this.oldName = name;
		this.presentAt = lineNumber;
		this.newName = generateNames ? Identifier.getNextName() : name;
		this.block = block;
	}
	
	public static String getNextName(){
		if(nextInt < firstAvail.length())
			return firstAvail.substring(nextInt, ++nextInt);
		return "$";
	}
	
	public String getOldName(){return this.oldName;}
	
	public String getBlock(){return this.block;}
	
	public String getNewName(){return "_"+this.newName+"_";}
	
	public String getNewName(boolean b){
		return this.newName;
	}
}
