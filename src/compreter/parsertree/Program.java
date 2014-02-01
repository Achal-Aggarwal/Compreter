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
			str += program.toString();
		
		return str;
	}
}
