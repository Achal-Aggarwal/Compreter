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
			
			String temp = "", t="";
			for(int i=1;i<=10;i++){
				temp = o.optimizeAt(threeCode, i);
				System.out.println("\n\nOTIMIZED at level "+ (i) + "\n" + temp);
				if(t.equals(temp)){
					break;
				}
				
				t =temp;
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
