package compreter;

import compreter.optimizer.ConstantFolding;
import compreter.optimizer.ConstantPropogation;
import compreter.optimizer.CopyPropogation;
import compreter.optimizer.DeadCodeElimination;
import compreter.optimizer.ExpressionSimplification;
import compreter.optimizer.Optimizer;

public class Optimize {
	String buffer[] = null;
	int nextLine = 0;
	Optimizer optimizers[]= {
			new ConstantFolding()
			 ,new ExpressionSimplification()
			,new ConstantPropogation()
			, new CopyPropogation()
			, new DeadCodeElimination()
	};

	private String getNextCodeBlock(){
		if(nextLine >= buffer.length)
			return null;
		
		String s = buffer[nextLine++] + "\n";
		String parts[];
		while(nextLine < buffer.length){
			parts = buffer[nextLine].split("[\\s]*:=[\\s]*");
			if(parts[0].equals("function") || 
					parts[0].equals("label"))
				break;
			
			if(parts[0].equals("return") || 
					parts[0].equals("goto") ||
					parts[0].equals("call")){
				s += buffer[nextLine++] + "\n";
				break;
			}
			s += buffer[nextLine++] + "\n";	
		}
		
		return s;
	}
	
	private String optimize(String in)
	{
		this.buffer = in.split("\n");
		this.nextLine = 0;
		String threeCode, output = "";
		
		while((threeCode = this.getNextCodeBlock()) != null){
			for(Optimizer optimizer : optimizers){
				threeCode = optimizer.optimize(threeCode);
			}
			output += threeCode;
		}
		
		return output;
	}
	
	public String optimizeAt(String in, int level){
		 
		for(int i=0;i<level;i++)
			in = this.optimize(in);
		
		return in;
	}
}
