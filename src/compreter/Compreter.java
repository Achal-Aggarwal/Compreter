package compreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Compreter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			Parser parser = new Parser(br);
			parser.parse();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
