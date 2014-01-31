package compreter;


import java.io.BufferedReader;
import java.io.IOException;

import compreter.parsertree.*;

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
	
	private Tree expressionPro(){
		Tree conditionalExpression = null, expression = null;
		
		if((conditionalExpression = conditionalExpressionPro()) != null){
			if((accept(Symbol.Id.PUNCTUATORS,"=", true)) != null){
				if((expression = expressionPro()) != null){
					return new Expression(conditionalExpression, expression);
				}
				
				return null;
			}
			
			return new Expression(conditionalExpression);
		}
		
		return null;
	}
	
	private Tree conditionalExpressionPro(){
		Tree conditionalExpression = null, truePart = null, falsePart = null;
		
		if((conditionalExpression = orExpressionPro()) != null){
			if((accept(Symbol.Id.PUNCTUATORS,"?", true)) != null){
				if((truePart = expressionPro()) != null){
					if((accept(Symbol.Id.PUNCTUATORS,":", true)) != null){
						if((falsePart = expressionPro()) != null){
							return new TernaryExpression(conditionalExpression, truePart, falsePart);
						}
					}
				}
				
				return null;
			}
			
			return new Expression(conditionalExpression);
		}
		
		return null;
	}
	
	public Tree orExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = andExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"||", true)) != null){
				if((secondConditionalExpression = orExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return new Expression(firstConditionalExpression);
		}
		
		return null;
	}
	
	public Tree andExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = equalityExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"&&", true)) != null){
				if((secondConditionalExpression = andExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return new Expression(firstConditionalExpression);
		}
		
		return null;
	}
	
	public Tree equalityExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = equalityExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"===?", true)) != null){
				if((secondConditionalExpression = relationalExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return new Expression(firstConditionalExpression);
		}
		
		return null;
	}
	
	public Tree relationalExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = additiveExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(>=?|<=?)", true)) != null){
				if((secondConditionalExpression = relationalExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return new Expression(firstConditionalExpression);
		}
		
		return null;
	}
	
	public Tree additiveExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = multiplicativeExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(+|-)", true)) != null){
				if((secondConditionalExpression = additiveExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return new Expression(firstConditionalExpression);
		}
		
		return null;
	}
	
	public Tree multiplicativeExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = unaryExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(*|/)", true)) != null){
				if((secondConditionalExpression = multiplicativeExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return new Expression(firstConditionalExpression);
		}
		
		return null;
	}
	
	public Tree unaryExpressionPro(){
		Tree opreand = null, memberExpression= null;
		Symbol operator = null;
		
		if((memberExpression = memberExpressionPro(false)) != null){
			return memberExpression;
		}
		
		if((operator = accept(Symbol.Id.PUNCTUATORS,"(+|-)", false)) != null){
			if((opreand = unaryExpressionPro()) != null){
				return new UnaryExpression(operator, opreand);
			}
			
			return null;
		}
		
		if((operator = accept(Symbol.Id.PUNCTUATORS,"(++|--)", false)) != null){
			if((opreand = memberExpressionPro(true)) != null){
				return new UnaryExpression(operator, opreand);
			}
			
			return null;
		}
		
		if((opreand = memberExpressionPro(false)) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(++|--)", true)) != null){
				return new UnaryExpression(operator, opreand);
			}
			
			return null;
		}
		
		if((operator = accept(Symbol.Id.KEYWORD,"new", false)) != null){
			if((opreand = constructorCallPro()) != null){
				return new UnaryExpression(operator, opreand);
			}
			
			return null;
		}
		
		if((operator = accept(Symbol.Id.KEYWORD,"new", true)) != null){
			if((opreand = memberExpressionPro(true)) != null){
				return new UnaryExpression(operator, opreand);
			}
			
			return null;
		}
			
		
		return null;
	}
	
	private Tree constructorCallPro(){
		Tree argumentListOpt=null;
		Symbol identifier;

		if((identifier = accept(Symbol.Id.IDENTIFIER_NAME,true))!=null){
			if(accept(Symbol.Id.PUNCTUATORS,"(",true)!=null){
				argumentListOpt = argumentListOptPro();
				if(accept(Symbol.Id.PUNCTUATORS,")",true)!=null){
					return new ConstructorCallExpression(identifier, argumentListOpt);
				}
				return null;
			}
			
			return new PrimaryExpression(identifier);
		}
		
		return null;
	}
	
	private Tree argumentListOptPro(){
		Tree argumentListOpt=null;
		Symbol identifier = null;

		if((argumentListOpt = argumentListPro())!=null){
				return new ConstructorCallExpression(identifier, argumentListOpt);
		}
			
		return null;
	}
	
	private Tree argumentListPro(){
		Tree argumentList = null;
		ArgumentListExpression argumentLists = null;
		
		if((argumentList = expressionPro()) != null){
			if(accept(Symbol.Id.PUNCTUATORS,",",true)!=null){
				if((argumentLists = (ArgumentListExpression) argumentListPro()) != null){
					return new ArgumentListExpression(argumentList, argumentLists);
				}
				return null;
			}
			return new ArgumentListExpression(argumentList);
			
		}
		
		return null;
	}
	
	private Tree memberExpressionPro(boolean consume){
		Tree pExpression = null, expression=null;
		
		if((pExpression = primaryExpressionPro(consume)) != null){
			if(accept(Symbol.Id.PUNCTUATORS,"[",true)!=null){
				if((expression = expressionPro()) != null){
					if(accept(Symbol.Id.PUNCTUATORS,"]",true)!=null){
						return new MemberExpression(pExpression, expression);
					}
				}
				return null;
			}
			else{
				return new MemberExpression(pExpression);
			}
		}
		
		return null;
	}
	
	private Tree primaryExpressionPro(boolean consume){
		Symbol pExpression = null;
		Tree expression = null;
		
		if(accept(Symbol.Id.PUNCTUATORS,"(",false)!=null){
			if ((expression = expressionPro())!=null){
				if(accept(Symbol.Id.PUNCTUATORS,"(",false)!=null){
					return new PrimaryExpression(expression);
				}
			}
			return null;
		}
		
		if((pExpression = accept(Symbol.Id.IDENTIFIER_NAME,false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.NUMERIC_LITERAL,false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.FLOATINGPOINT_LITERAL,false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.STRING_LITERAL,false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.BOOLEAN_LITERAL,false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.BOOLEAN_LITERAL,false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.KEYWORD,"null",false))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.KEYWORD,"NaN",consume))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		return null;
	}
}
