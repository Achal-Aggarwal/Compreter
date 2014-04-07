package compreter.optimizer;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantPropogation extends Optimizer {

	static Pattern exp = Pattern.compile("[\\s]*([\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*",Pattern.DOTALL);
	static Pattern idopidexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*|[\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*([^\\w\\d\\s.]+)[\\s]*([^0-9 ][\\w]*|[\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*",Pattern.DOTALL);
	static Pattern idexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*",Pattern.DOTALL);
	static Pattern calididexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*\\([\\s]*([^)]*)[\\s]*\\)[\\s]*",Pattern.DOTALL);
	
	public String optimize(String in){
		String out = "";
		HashMap<String, String> hm = new HashMap<String, String>();
		String lines[] = in.split("\n");
		
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			String id = parts[0];
			Matcher matcher = exp.matcher(parts[1]);
			if(matcher.matches() && parts[0] != "return" && parts[0] != "call"){
				String constant = matcher.group(1);
				hm.put(id, constant);
				out += line + "\n";
			} else {
				hm.remove(id);
				matcher = idopidexp.matcher(parts[1]);
				if(matcher.matches()){
					Object idOne = matcher.group(1);
					String op = matcher.group(2);
					Object idTwo = matcher.group(3);
					
					if(hm.containsKey(idOne)){
						idOne = hm.get(idOne);
					}
					
					if(hm.containsKey(idTwo)){
						idTwo = hm.get(idTwo);
					}
					
					out += parts[0] + " := " + idOne + " " + op + " " + idTwo + "\n";
				} else {
					matcher = idexp.matcher(parts[1]);
					if(matcher.matches()){
						Object idOne = matcher.group(1);
						
						if(hm.containsKey(idOne)){
							idOne = hm.get(idOne);
						}
						
						out += parts[0] + " := " + idOne + "\n";
					} else {
						matcher = calididexp.matcher(parts[1]);
						if(matcher.matches() && parts[0].equals("call")){
							Object functName = matcher.group(1);
							String params[] = matcher.group(2).split(", ");
							out += parts[0] + " := " + functName + " ( ";
							for(String param:params){
								if(hm.containsKey(param)){
									out += hm.get(param) + ", ";
								} 
								else {
									out += param + ", ";
								}
							}
							
							out = out.substring(0, out.length()-3) + " )\n";
						} else {
							out += line + "\n";
						}
					}
				}

			}
		}
		
		return out;
	}

}
