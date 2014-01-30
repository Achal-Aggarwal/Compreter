package compreter;

import java.util.regex.Pattern;

public interface Patterns {
	String patternStart = "[\\s]*";
	String patternEnd = "[\\s]+.*";
	public static final Pattern 
	KEYWORD=Pattern.compile(patternStart + "(var|function|if|else|while|break|continue|return)"+ patternEnd);
	public static final Pattern 
	OPERATOR =Pattern.compile(patternStart + "(\\+|-|\\*|/|<={0,1}|>={0,1}|={1,3}|\\^|,|;).*");
	public static final Pattern 
		IDENTIFIER_NAME = Pattern.compile(patternStart + "([A-Za-z]+[A-Za-z0-9]*)"+ "(" + patternEnd + "|" + OPERATOR.pattern() + ")");
	public static final Pattern 
		NUMERIC_LITERAL= Pattern.compile(patternStart + "([0-9]+)" + patternEnd);
	public static final Pattern 
		STRING_LITERAL= Pattern.compile(patternStart + "\"([^\\\n\"]*)\"" + "(" + patternEnd + "|" + OPERATOR.pattern() + ")");
	
}
