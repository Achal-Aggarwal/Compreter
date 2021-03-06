package compreter.parsertree;

public abstract class Tree {
	static int lineNumber = 1;
	
	static int backPatchLineNumber = -1;
	static String current_block = "__main__";
	static int lastTemp = 0;
	
	static int lastLabel = 0;
	
	public static boolean printLineNumber = true;

	String place = null;
	
	static IdentifierTable it;
	
	int currentLineNumber = -1;
	
	public static void initializeIdentifierTable(){
		Identifier.nextInt = 0;
		it = new IdentifierTable();
	}
	
	public String getCode(){return "";}
	public String getLabelCode(){return this.getCode();}
	public abstract String getSimpleCode();
	
	public static String getNextTemp(){
		return "Temp"+String.valueOf(lastTemp++);
	}
	
	public static String getNextLabel(){
		return "Label"+String.valueOf(lastLabel++);
	}
	
	public String printLineNumber(boolean printAlso){
		currentLineNumber  = lineNumber++;
		return printLineNumber && printAlso  ? String.valueOf(currentLineNumber) + ". " : "";
	}
	
	public int tLineCount(){return 0;}
	
	public static void generateNewNames(boolean b){
		Identifier.generateNames = b;
	}
}
