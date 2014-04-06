package compreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
	static Pattern calididexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*)[\\s]*\\([\\s]*([^)]*)[\\s]*\\)[\\s]*",Pattern.DOTALL);
	static Pattern idnumexp = Pattern.compile("[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*([^\\w\\d\\s.]+)[\\s]*([^0-9 ][\\w]*|[\\d]+)[\\s]*",Pattern.DOTALL);
	
	public String convertToJS(String in){
		String out = "";
		String lines[] = in.split("\n");
		Matcher matcher = null;
		int indentLevel = 0;
		for(String line : lines){
			String parts[] = line.split("[\\s]*:=[\\s]*");
			
			if(parts[0].equals("function") || parts[0].equals("call")){
				matcher = calididexp.matcher(parts[1]);
				if(matcher.matches()){
					String functName = matcher.group(1);
					String params[] = matcher.group(2).split("[\\s]*,[\\s]*");
					String par = "";
					for(String param:params){
						par += param.substring(1,param.length()-1) + ", ";
					}
					
					par = par.substring(0,par.length()-2);
					
					if(parts[0].equals("function")){
						out += "function " + functName.substring(1,functName.length()-1) + " ( ";
						out += par + " ){\n";
						indentLevel += 1;
					} else{
						out += nString("\t", indentLevel) + functName.substring(1,functName.length()-1) + "( ";
						out += par + " );\n";
					}
					
				}
			}
			
			else if(!(parts[0].equals("label") || 
					parts[0].equals("goto") || 
					parts[0].equals("call"))){
			
				matcher = idnumexp.matcher(parts[1]);
				String op = null;
				String op1 = null;
				String op2 = null;
				if(matcher.matches()){
					op1 = matcher.group(1);
					op2 = matcher.group(3);
					op = matcher.group(2);
					
					if(op1.contains("_")){
						op1 = op1.substring(1,op1.length()-1);
					}
					
					if(op2.contains("_")){
						op2 = op2.substring(1,op2.length()-1);
					}
					
					if(parts[0].equals("return")){
						out += nString("\t", indentLevel) +  "return " + op1 + " " +  op + " " + op2 + ";\n"; 
					} else {
						String result = parts[0];
						if(parts[0].contains("_")){
							result = parts[0].substring(1,parts[0].length()-1);
						}
						out += nString("\t", indentLevel) +  result + " = " + op1 + " " +  op + " " + op2 + ";\n"; 
					}
					
				} else{
					if(parts[1].contains("function")){
						indentLevel -= 1;
						out += "}\n";
					}
				}
			}
		}
		
		return out;
	}
	
	private String nString(String s,int i){
	   String result="";
	   for(;i>0;i--){
	       result += s;   //for appending strings
	   }
	   return result;
	}
}
