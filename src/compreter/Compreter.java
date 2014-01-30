package compreter;

import java.io.IOException;

public class Compreter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lexer l = new Lexer("if(a===2)b=3;\nelse b=4;");
		try {
			Symbol s;
			while(true){
				s = l.nextToken();
				if(s == null)break;
				System.out.println(s.getClas() + "---" + s.value());
			}
			
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
