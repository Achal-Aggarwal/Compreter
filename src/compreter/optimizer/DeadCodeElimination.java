package compreter.optimizer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadCodeElimination extends Optimizer {
	static Pattern idnumexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*([^\\w\\d\\s]+)[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*",Pattern.DOTALL);
	static Pattern idornumexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*",Pattern.DOTALL);
	static Pattern calididexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*\\([\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*,[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*\\)[\\s]*",Pattern.DOTALL);
	
	public String optimize(String in){
		String out = "";
		
		String lines[] = in.split("\n");
		HashMap<String, String> exphm = new HashMap<String, String>();
		HashMap<String, Integer> counthm = new HashMap<String, Integer>();
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			String op1 = null, op2, op;
			Matcher matcher = idnumexp.matcher(parts[1]);
			if(matcher.matches()){
				op1 = matcher.group(1);
				op2 = matcher.group(3);
				op = matcher.group(2);
				
				if(exphm.containsKey(op1)){
					counthm.put(op1, counthm.get(op1)+1);
				} 
				
				if(exphm.containsKey(op2)){
					counthm.put(op2, counthm.get(op2)+1);
				}
			}
			
			matcher = idornumexp.matcher(parts[1]);
			if(matcher.matches()){
				op1 = matcher.group(1);
				
				if(exphm.containsKey(op1)){
					counthm.put(op1, counthm.get(op1)+1);
				} 
			}
			
			matcher = calididexp.matcher(parts[1]);
			if(matcher.matches()){
				op1 = matcher.group(2);
				op2 = matcher.group(3);

				if(exphm.containsKey(op1)){
					counthm.put(op1, counthm.get(op1)+1);
				} 
				
				if(exphm.containsKey(op2)){
					counthm.put(op2, counthm.get(op2)+1);
				} 
			}

			exphm.put(parts[0], line);
			
			if(parts[0].equals("function") || 
					parts[0].equals("return") ||
					parts[0].equals("goto") ||
					parts[0].equals("label") ||
					parts[0].equals("call"))
				counthm.put(parts[0], 1);
			else
				counthm.put(parts[0], 0);
		}
		
		for (Map.Entry entry : exphm.entrySet()) {
			if(counthm.get(entry.getKey()).intValue() != 0){
				out = entry.getValue() + "\n" + out;
			}
		}
		
		return out;
	}
}
