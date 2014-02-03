package compreter.parsertree;

public class Program extends Tree{
	Tree element = null,
			program = null;
	
	public Program(Tree element, Tree program){
		this.element = element;
		this.program = program;
	}
	
	public String toString(){
		String str = element.toString();
		
		if(program != null)
			str += ";" + program.toString();
		
		return str;
	}
	
	public String getCode(){
		String str = element.getCode();
		
		if(program != null)
			str += program.getCode();
		
		return str;
	}
	
	public String getLabelCode(){
		String str = element.getLabelCode();
		
		if(program != null)
			str += program.getLabelCode();
		
		return str;
	}
	
	public int tLineCount(){
		int count = element.tLineCount();
		
		if(program != null)
			count += program.tLineCount();
		
		return count;
	}
}
