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
		BufferedReader br = null;
		Lexer l = new Lexer();
		try {
			Symbol s;
			String sCurrentLine = null;
			br = new BufferedReader(new FileReader(args[0]));
			while ((sCurrentLine = br.readLine()) != null) {
				l.parserLine(sCurrentLine);
				while(true){
					s = l.nextToken();
					if(s == null)break;
					System.out.println(s.getClas() + "---" + s.value());
				}
			}
			
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
