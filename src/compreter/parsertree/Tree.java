package compreter.parsertree;

public abstract class Tree {
	static int lineNumber = 1;
	
	static int backPatchLineNumber = -1;
	
	static int lastTemp = 0;
	
	String place = null;
	
	static IdentifierTable it = new IdentifierTable();
	
	int currentLineNumber = -1;
	
	public String getCode(){return "";}
	
	public static String getNextTemp(){
		return "Temp"+String.valueOf(lastTemp++);
	}
	
	public String printLineNumber(boolean printAlso){
		currentLineNumber  = lineNumber++;
		return printAlso ? String.valueOf(currentLineNumber) + ". " : "";
	}
	
	public int tLineCount(){return 0;}
}
