package compreter;

import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
	Lexer lex;
	Symbol token;
	public Parser(BufferedReader input) throws IOException{
		lex = new Lexer(input);
		token = null;
	}
	public int parse(){
		exps();

		return 0;
	}
	
	public Symbol accept(Symbol.Id code, boolean consume){
		Symbol token = this.token;
		try {
			token = token == null ? lex.nextToken() : token;
			if(token == null || code != token.getCode()){
				this.token = !consume ? token : null;
				
				return null;
			}
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.token = null;
		return token;
	}
	
	public Symbol accept(Symbol.Id code, String lexeme, boolean consume){
		Symbol token = this.token;
		try {
			token = token == null ? lex.nextToken() : token;
			if(token == null || code != token.getCode() || token.getValue().compareTo(lexeme)!=0){
				this.token = !consume ? token : null;
				
				return null;
			}
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.token = null;
		return token;
	}
	
	public boolean exp(){
		Symbol id = null;
		
		//Production rule Expr -> id + id
		if((id = accept(Symbol.Id.IDENTIFIER_NAME, true))!=null){
			if(expDash(id)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean expDash(Symbol idOne){
		Symbol idTwo = null, plusOper = null;
		
		if((plusOper = accept(Symbol.Id.PUNCTUATORS,"+",false))!=null){
			if((idTwo = accept(Symbol.Id.IDENTIFIER_NAME, true))!=null){
				System.out.println("ADD " + idOne.getValue() + ", " + idTwo.getValue());
				return true;
			}
		}
		//Production rule Expr -> id * id
		else if((plusOper = accept(Symbol.Id.PUNCTUATORS,"*",true))!=null){
			if((idTwo = accept(Symbol.Id.IDENTIFIER_NAME, true))!=null){
				System.out.println("MUL " + idOne.getValue() + ", " + idTwo.getValue());
				return true;
			}
		}
		
		return false;
	}
	
	public boolean exps(){
		if(exp()){
			if(exps()){
				return true;
			}
			else{
				return false;
			}
		}
		
		return true;
	}
}
