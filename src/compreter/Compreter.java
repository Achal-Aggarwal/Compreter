package compreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

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
			
			String ostr = null;
			System.out.println("Original\n-----------------------");
			while((ostr = br.readLine()) != null){
				System.out.println(ostr);
			}
			System.out.println("/*-|-|-*/\n");
			
			Parser parser = new Parser(new BufferedReader(new FileReader(args[0])));
			String threeCode = (String) parser.parse(Parser.CodeType.SIMPLE_CODE, false, false);
			
			System.out.println("UNOPTIMIZED 3 ADDRESS CODE\n-----------------------\n" 
					+ threeCode + "\n/*-|-|-*/");
			
			System.out.println("\n\nONLY SPACE OPTIMIZED -- curent scenario\n-----------------------\n"
					+ (String) (new Parser(new BufferedReader(new FileReader(args[0])))).parse(Parser.CodeType.JS_MINIFIED, false, false) + "\n/*-|-|-*/");
			
			System.out.println("\n\nONLY SPACE OPTIMIZED\n-----------------------\n" + 
					(String) (new Parser(new BufferedReader(new FileReader(args[0])))).parse(Parser.CodeType.JS_MINIFIED, false, true) + "\n/*-|-|-*/");
			
			Optimize o = new Optimize();
			
			String temp = "", t="";
			for(int i=1;i<=10;i++){
				temp = o.optimizeAt(threeCode, i);
				System.out.println("\n\nOTIMIZED at level "+ (i) + "\n-----------------------\n" 
						+ temp + "\n/*-|-|-*/");
				if(t.equals(temp)){
					break;
				}
				
				t =temp;
			}
			Converter c = new Converter();
			String js = c.convertToJS(temp);
			System.out.println("\n\nOptimized JS \n-----------------------\n"
					+ js + "\n/*-|-|-*/");
			
			Parser miniparser = new Parser(new BufferedReader(new StringReader(js)));
			String minifiedJS = (String) miniparser.parse(Parser.CodeType.JS_MINIFIED, false, true);
			
			System.out.println("\n\nMinifiedJs \n-----------------------\n"
			+ minifiedJS + "\n/*-|-|-*/");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
