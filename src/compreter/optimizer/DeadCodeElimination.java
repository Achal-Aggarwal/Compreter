package compreter.optimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadCodeElimination extends Optimizer {
	static Pattern idnumexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*([^\\w\\d\\s.]+)[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*",Pattern.DOTALL);
	static Pattern idornumexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*",Pattern.DOTALL);
	static Pattern calididexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*\\([\\s]*([^)]*)[\\s]*\\)[\\s]*",Pattern.DOTALL);
	
	public String optimize(String in){
		String out = "";
		
		String lines[] = in.split("\n");
		ArrayList<String> codeOrder = new ArrayList<String>();
		
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
			
			/////////Check this code
			matcher = calididexp.matcher(parts[1]);
			if(matcher.matches()){
				String params[] = matcher.group(2).split(", ");
				
				for(String param:params){
					if(exphm.containsKey(param)){
						counthm.put(param, counthm.get(param)+1);
					} 
				}
			}

			exphm.put(parts[0], line);
			codeOrder.add(parts[0]);
			
			if(parts[0].equals("function") || 
					parts[0].equals("return") ||
					parts[0].equals("goto") ||
					parts[0].equals("label") ||
					parts[0].equals("call"))
				counthm.put(parts[0], 1);
			else
				counthm.put(parts[0], 0);
		}
		
		for (String l : codeOrder) {
			if(counthm.get(l).intValue() != 0){
				out += exphm.get(l) + "\n";
			}
		}
		
		return out;
	}
}
