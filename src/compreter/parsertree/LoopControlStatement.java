package compreter.parsertree;

public class LoopControlStatement extends Tree {
	
	String type = null;
	WhileStatement loop = null;
	
	public LoopControlStatement(String dir){
		this.type = dir;
	}
	
	public String toString(){
		return type;
	}
	
	public void setLoop(Tree loop){
		this.loop = (WhileStatement) loop;
	}
	
	public String getCode(){
		if(type.compareTo("break")==0){
			return this.printLineNumber(true) +"goto := " + loop.labelLast + "\n";
		}
		else{
			return this.printLineNumber(true) +"goto := " + loop.labelFirst + "\n";
		}
	}
	
	public int tLineCount(){
		return 1;
	}
}
