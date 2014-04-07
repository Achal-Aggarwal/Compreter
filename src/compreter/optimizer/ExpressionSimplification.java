package compreter.optimizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionSimplification extends Optimizer{
	static Pattern idnumexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*([^\\w\\d\\s.]+)[\\s]*([\\d]+|true|false)[\\s]*",Pattern.DOTALL);
	static Pattern numidexp = Pattern.compile("[\\s]*([\\d]+|true|false)[\\s]*([^\\w\\d\\s.]+)[\\s]*([^0-9 ][\\w]*)[\\s]*",Pattern.DOTALL);
	public String optimize(String in){
		String out = "";
		
		String lines[] = in.split("\n");
		
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			
			Matcher matcher = idnumexp.matcher(parts[1]);
			String id = null;
			boolean flag = false;
			String op = null;
			int b = 0;
			if(matcher.matches()){
				id = matcher.group(1);
				b = (int) (matcher.group(3).equals("true") ? 1 : (matcher.group(3).equals("false") ? 0 :Float.parseFloat(matcher.group(3))));
				op = matcher.group(2);
				flag = true;
			}
			
			matcher = numidexp.matcher(parts[1]);
			if(matcher.matches()){
				id = matcher.group(3);
				b = (int) (matcher.group(1).equals("true") ? 1 : (matcher.group(1).equals("false") ? 0 :Float.parseFloat(matcher.group(1))));
				op = matcher.group(2);
			}
			
			if(id != null){
				Object result = 0;
				
				
				if((op.equals("*") && b == 1) || 
						(op.equals("+") && b == 0) || 
						(op.equals("-") && b == 0 && flag)||
						(op.equals("&&") && b == 1) ||
						(op.equals("||") && b == 0))
					result = id;
				
				else if((op.equals("/") && b == 0) || (op.equals("&&") && b == 0) || (op.equals("*") && b == 0))
					result = 0;
				
				else if((op.equals("||") && b == 1))
					result = 1;
				else{
					if (flag){
						result = id + " " + op + " " + b;
					} else{
						result = b + " " + op + " " + id;
					}
				}
					

				out += parts[0] + " := " + result + "\n";
			} else {
				out += line + "\n";
			}
		}
		
		return out;
	}
}
