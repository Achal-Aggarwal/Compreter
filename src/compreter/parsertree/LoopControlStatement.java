package compreter.parsertree;

public class LoopControlStatement extends Tree {
	
	String type = null;
	
	public LoopControlStatement(String dir){
		this.type = dir;
	}
	
	public String toString(){
		return type + ";";
	}

}
