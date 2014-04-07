package compreter.optimizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantFolding extends Optimizer {
	static Pattern numExp = Pattern.compile("[\\s]*([\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*([^\\w\\d\\s.]+)[\\s]*([\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*",Pattern.DOTALL);
	public String optimize(String in){
		String out = "";
		
		String lines[] = in.split("\n");
		
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			
			Matcher matcher = numExp.matcher(parts[1]);
			if(matcher.matches()){
				
				float aT = Float.parseFloat(matcher.group(1));
				float bT = Float.parseFloat(matcher.group(3));
				Integer ai = null, bi = null;
				Float af = null, bf = null;
				
				if(aT == Math.round(aT)){
					ai = (int) aT; 
				} else {
					af = (float) aT;
				}
				
				if(bT == Math.round(bT)){
					bi = (int) bT; 
				} else {
					bf = (float) bT;
				}

				Object result = 0;
				
				switch(matcher.group(2)){
					case "+":
						result = ai != null ? (bi != null ? ai + bi : ai + bf) : (bi != null ? af + bi : af + bf);
						break;
					case "-":
						result = ai != null ? (bi != null ? ai - bi : ai - bf) : (bi != null ? af - bi : af - bf);
						break;
					case "*":
						result = ai != null ? (bi != null ? ai * bi : ai * bf) : (bi != null ? af * bi : af * bf);
						break;
					case "/":
						result = ai != null ? (bi != null ? ai * 1.0 / bi : ai * 1.0 / bf) : (bi != null ? af / bi : af / bf);
						break;
					case "&&":
						result = ((ai != null && ai!= 0) || (af != null && af!= 0)) && ((bi != null && bi!= 0) || (bf != null && bf!= 0)) ? 1 : 0;
						break;
					case "||":
						result = ((ai != null && ai!= 0) || (af != null && af!= 0)) || ((bi != null && bi!= 0) || (bf != null && bf!= 0)) ? 1 : 0;
						break;
					case "<=":
						result = ai != null ? (bi != null ? ai <= bi : ai <= bf) : (bi != null ? af <= bi : af <= bf);
						break;
					case "==":
						result = ai != null ? (bi != null ? ai == bi : 0) : (bi != null ? 0 : af == bf);
						break;
					case ">=":
						result = ai != null ? (bi != null ? ai >= bi : ai >= bf) : (bi != null ? af >= bi : af >= bf);
						break;
					default:
						result = aT  + " " + matcher.group(2) + " " + bT;
				}
				out += parts[0] + " := " + result + "\n";
			} else {
				out += line + "\n";
			}
		}
		
		return out;
	}
}
