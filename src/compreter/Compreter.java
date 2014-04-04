package compreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import compreter.optimizer.ConstantFolding;
import compreter.optimizer.ExpressionSimplification;
import compreter.optimizer.Optimizer;

public class Compreter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			Parser parser = new Parser(br);
			String threeCode = (String) parser.parse();
			
			Optimizer optimizers[] = {new ConstantFolding(), new ExpressionSimplification()};
			
			
			for(Optimizer optimizer : optimizers){
				threeCode = optimizer.optimize(threeCode);
			}
			
			System.out.println(threeCode);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
