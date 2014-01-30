package compreter;

import java.io.IOException;
import java.util.regex.Matcher;

public class Lexer implements Patterns {
	String source;
	static int offset = 0;
	static final int bufferSize = 50;
	public Lexer(String source){
		this.source = source;
	}
	private String fillBuffer(){
		String buffer = null;
		if(source.length() > offset + bufferSize){
			buffer = source.substring(offset, offset + bufferSize);
		}
		else{
			buffer = source.substring(offset);
		}
		return buffer;
	}
	private boolean isEOF(){
		return offset == source.length();
	}
	public Symbol nextToken() throws IOException, CloneNotSupportedException{
		
		Matcher matcher = null;
		if(this.isEOF())return null;
		
		String buffer = this.fillBuffer();
		
		matcher = COMMENT.matcher(buffer);
		if(matcher.matches()){
			if(matcher.group(1) != null){ // For multiline
				offset += matcher.end(1);
				return new Symbol(Symbol.Id.COMMENT, matcher.group(1));
			}
			else{ // For single line
				offset += matcher.end(2);
				return new Symbol(Symbol.Id.COMMENT, matcher.group(2));
			}
		}
		
		matcher = OPERATOR.matcher(buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.OPERATOR, matcher.group(1));
		}
		
		matcher = KEYWORD.matcher(buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.KEYWORD, matcher.group(1));
		}
		
		matcher = NUMERIC_LITERAL.matcher(buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.NUMERIC_LITERAL, matcher.group(1));
		}
		
		matcher = IDENTIFIER_NAME.matcher(buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.IDENTIFIER_NAME, matcher.group(1));
		}
		
		matcher = STRING_LITERAL.matcher(buffer);
		if(matcher.matches()){
			offset += matcher.end(1) + 1;//plus one is to skip " (inverted commas)
			return new Symbol(Symbol.Id.STRING_LITERAL, matcher.group(1));
		}
		
		return null;
	} 
}
