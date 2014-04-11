package compreter.optimizer;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyPropogation extends Optimizer {
	static Pattern idexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*",Pattern.DOTALL);
	public String optimize(String in){
		String out = "";
		HashMap<String, String> hm = new HashMap<String, String>();
		String lines[] = in.split("\n");
		
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			String id = parts[0];
			Matcher matcher = idexp.matcher(parts[1]);
			if(matcher.matches() && 
					!(parts[0].equals("function") ||
						parts[0].equals("label") ||
						parts[0].equals("goto") ||
						parts[0].equals("call"))){
				if(hm.containsKey(matcher.group(1))){
					out += parts[0] + " := " + hm.get(matcher.group(1)) + "\n";
					hm.put(parts[0], hm.get(matcher.group(1)));
				} else{
					out += line + "\n";
					hm.put(parts[0], parts[1]);
				}
			} else {
				out += line + "\n";
				hm.put(parts[0], parts[1]);
			}
			
			
		}
		
		return out;
	}
}
