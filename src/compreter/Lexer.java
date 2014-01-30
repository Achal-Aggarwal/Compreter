package compreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;

public class Lexer implements Patterns {
	BufferedReader input;
	String buffer;
	static int offset;
	static final int bufferSize = 200;
	public Lexer(BufferedReader input) throws IOException{
		this.input = input;
		this.buffer = this.input.readLine();
	}
	
	private void fillBuffer() throws IOException{
		buffer = buffer.substring(offset);
		offset = 0;
		while(buffer.length() < bufferSize){
			String nextLine = input.readLine();
			if(nextLine != null){
				buffer = buffer.concat("\n").concat(nextLine);
			}
			else{
				break;
			}
		}
	}
	
	private boolean isEOF(){
		return buffer == null | buffer.length() == 0;
	}
	public Symbol nextToken() throws IOException, CloneNotSupportedException{
		
		Matcher matcher = null;
		if(this.isEOF()){
			return null;
		}
		
		this.fillBuffer();
		
		matcher = COMMENT.matcher(this.buffer);
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
		
		matcher = PUNCTUATORS.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.PUNCTUATORS, matcher.group(1));
		}
		
		matcher = KEYWORD.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.KEYWORD, matcher.group(1));
		}
		
		matcher = NUMERIC_LITERAL.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.NUMERIC_LITERAL, matcher.group(1));
		}
		
		matcher = FLOATINGPOINT_LITERAL.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.FLOATINGPOINT_LITERAL, matcher.group(1));
		}
		
		matcher = BOOLEAN_LITERAL.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.BOOLEAN_LITERAL, matcher.group(1));
		}
		
		matcher = STRING_LITERAL.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1) + 1;//plus one is to skip " (inverted commas)
			return new Symbol(Symbol.Id.STRING_LITERAL, matcher.group(1));
		}
		
		matcher = IDENTIFIER_NAME.matcher(this.buffer);
		if(matcher.matches()){
			offset += matcher.end(1);
			return new Symbol(Symbol.Id.IDENTIFIER_NAME, matcher.group(1));
		}
		
		return null;
	} 
}
