package compreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import compreter.optimizer.ConstantFolding;
import compreter.optimizer.ConstantPropogation;
import compreter.optimizer.CopyPropogation;
import compreter.optimizer.DeadCodeElimination;
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
			
			System.out.println("ORIGINAL\n" + threeCode);
			
			Optimize o = new Optimize();
			
			System.out.println("\n\nOTIMIZED\n" + o.optimizeAt(threeCode, 2));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
