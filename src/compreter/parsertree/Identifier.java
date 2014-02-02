package compreter.parsertree;

public class Identifier {
	static int nextInt = 0;
	static String nextName;
	int presentAt;
	String oldName, newName;
	static String firstAvail = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ_";
	static String remainAvail = firstAvail + "0123456789";
	
	public Identifier(String name){
		this.oldName = name;
		this.presentAt = -1;
		this.newName = Identifier.getNextName();
	}
	
	public Identifier(String name, int lineNumber){
		this.oldName = name;
		this.presentAt = lineNumber;
		this.newName = Identifier.getNextName();
	}
	
	public static String getNextName(){
		if(nextInt < firstAvail.length())
			return firstAvail.substring(nextInt, ++nextInt);
		return "$";
	}
	
	public String getOldName(){return this.oldName;}
	
	public String getNewName(){return "_"+this.newName+"_";}
	
	public String getNewName(boolean b){
		return this.newName;
	}
}
