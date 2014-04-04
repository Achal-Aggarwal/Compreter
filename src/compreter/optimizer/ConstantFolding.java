package compreter.optimizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantFolding {
	static Pattern numExp = Pattern.compile("[\\s]*([\\d]+)[\\s]*([^\\w\\d\\s]+)[\\s]*([\\d]+)[\\s]*",Pattern.DOTALL);
	public static String optimize(String in){
		String out = "";
		
		String lines[] = in.split("\n");
		
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			
			Matcher matcher = numExp.matcher(parts[1]);
			if(matcher.matches()){
				
				int a = Integer.parseInt(matcher.group(1));
				int b = Integer.parseInt(matcher.group(3));
				Object result = 0;
				
				switch(matcher.group(2)){
					case "+":
						result = a + b;
						break;
					case "-":
						result = a - b;
						break;
					case "*":
						result = a * b;
						break;
					case "/":
						result = a / b;
						break;
					case "&&":
						result = (a != 0 && b != 0) ? 1 : 0;
						break;
					case "||":
						result = (a != 0 || b != 0) ? 1 : 0;
						break;
					case "<=":
						result = (a <= b) ? 1 : 0;
						break;
					case "==":
						result = (a == b) ? 1 : 0;
						break;
					case ">=":
						result = (a >= b) ? 1 : 0;
						break;
					default:
						result = a + " " + matcher.group(2) + " " + b;
				}
				out += parts[0] + " := " + result + "\n";
			} else {
				out += line + "\n";
			}
		}
		
		return out;
	}
}
