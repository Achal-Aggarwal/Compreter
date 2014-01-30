package compreter;

import java.util.regex.Pattern;

public interface Patterns {
	String patternStart = "[\\s]*";
	String patternEnd = "[\\s]+.*";
	public static final Pattern 
	KEYWORD=Pattern.compile(patternStart + "(var|function|if|else|while|break|continue|return|NaN|null)"+ patternEnd, Pattern.DOTALL)
	
	,PUNCTUATORS =Pattern.compile(patternStart + "(\\{|\\}|\\(|\\)|[|]" +
			"|;|,|\\?|:" +
			"|<=?|>=?|={1,3}|!={0,2}" +
			"|\\+\\+?|--?|\\*|/|%" +
			"|&&|\\|{2}).*",Pattern.DOTALL)
	
	 
	,COMMENT =Pattern.compile(patternStart + "(\\/\\*[\\w\\'\\s\\r\\n\\*]*\\*\\/).*|" //multiline
			+ patternStart + "(//[^\\n]*).*",Pattern.DOTALL)
	
	,IDENTIFIER_NAME = Pattern.compile(patternStart + "([A-Za-z]+[A-Za-z0-9]*)"+ "(" + patternEnd + "|" + PUNCTUATORS.pattern() + ")", Pattern.DOTALL)
	
	,NUMERIC_LITERAL= Pattern.compile(patternStart + "([0-9]+)" + "(" + patternEnd + "|" + PUNCTUATORS.pattern() + ")", Pattern.DOTALL)
	
	,FLOATINGPOINT_LITERAL= Pattern.compile(patternStart + "([+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)" + "(" + patternEnd + "|" + PUNCTUATORS.pattern() + ")", Pattern.DOTALL)
	
	,BOOLEAN_LITERAL= Pattern.compile(patternStart + "(true|false)" + "(" + patternEnd + "|" + PUNCTUATORS.pattern() + ")", Pattern.DOTALL)
	
	,STRING_LITERAL= Pattern.compile(patternStart + "\"([^\\\n\"]*)\"" + "(" + patternEnd + "|" + PUNCTUATORS.pattern() + ")", Pattern.DOTALL);
	
}
