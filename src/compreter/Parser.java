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
		Tree tree = statementPro();
		System.out.println(tree);
		return 0;
	}
	
	public Symbol accept(Symbol.Id code){
		Symbol token = this.token;
		try {
			token = token == null ? lex.nextToken() : token;
			if(token == null || code != token.getCode()){
				this.token = token;
				
				return null;
			}
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.token = null;
		return token;
	}
	
	public Symbol accept(Symbol.Id code, String lexeme){
		Symbol token = this.token;
		try {
			token = token == null ? lex.nextToken() : token;
			if(token == null || code != token.getCode() || token.getValue().matches(lexeme)==false){
				this.token = token;
				
				return null;
			}
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.token = null;
		return token;
	}
	
	private Tree statementPro(){
		Tree condition = null,
				truePart = null,
				falsePart =  null,
				expression;
		
		if(accept(Symbol.Id.KEYWORD,"if")!=null){
			if((condition = jumpConditionPro()) != null){
				if((truePart = statementPro()) != null){
					if(accept(Symbol.Id.KEYWORD,"else")!=null){
						if((falsePart = statementPro()) != null){
							return new IfStatement(condition, truePart, falsePart);
						}
						
						return null;
					}
					return new IfStatement(condition, truePart);
				}
			}
			
			return null;
		}
		
		if((expression = variablesPro())!=null){
			return expression;
		}
		
		if((expression = expressionPro())!=null){
			return expression;
		}
		
		return null;
	}
	
	private Tree jumpConditionPro(){
		Tree expression = null;
		
		if(accept(Symbol.Id.PUNCTUATORS,"(")!=null){
			if((expression = expressionPro()) != null){
				if(accept(Symbol.Id.PUNCTUATORS,")")!=null){
					return expression;
				}
			}
		}
		
		return null;
	}
	
	private Tree variablesPro(){
		Tree varDeclaration = null;
		
		if(accept(Symbol.Id.KEYWORD,"var")!=null){
			if((varDeclaration = variableListPro()) != null){
				accept(Symbol.Id.PUNCTUATORS,";");
				return new VariableDeclarationStatement(varDeclaration);
			}
		}
		
		return null;
	}
	
	private Tree variableListPro(){
		Tree variable = null;
		VariableList variables = null;
		
		if((variable = variablePro()) != null){
			if(accept(Symbol.Id.PUNCTUATORS,",")!=null){
				if((variables = (VariableList) variableListPro()) != null){
					return new VariableList(variable, variables);
				}
				return null;
			}
			return new VariableList(variable);
			
		}
		
		return null;
	}
	
	private Tree variablePro(){
		Symbol identifier = null;
		Tree expression = null;
		
		if((identifier = accept(Symbol.Id.IDENTIFIER_NAME))!=null){
			if((accept(Symbol.Id.PUNCTUATORS,"="))!=null){
				if((expression = expressionPro())!=null){
					return new Variable(identifier, expression);
				}
				return null;
			}
			
			return new Variable(identifier);
		}
		
		return null;
	}
	
	private Tree expressionOptPro(){
		Tree expression = null;
		if((expression=expressionPro())!=null){
			return expression;
		}
		return null;
	}
	
	private Tree expressionPro(){
		Tree conditionalExpression = null, expression = null;
		
		if((conditionalExpression = conditionalExpressionPro()) != null){
			if((accept(Symbol.Id.PUNCTUATORS,"=")) != null){
				if((expression = expressionPro()) != null){
					return new Expression(conditionalExpression, expression);
				}
				
				return null;
			}
			
			return conditionalExpression;
		}
		
		return null;
	}
	
	private Tree conditionalExpressionPro(){
		Tree conditionalExpression = null, truePart = null, falsePart = null;
		
		if((conditionalExpression = orExpressionPro()) != null){
			if((accept(Symbol.Id.PUNCTUATORS,"\\?")) != null){
				if((truePart = expressionPro()) != null){
					if((accept(Symbol.Id.PUNCTUATORS,":")) != null){
						if((falsePart = expressionPro()) != null){
							return new TernaryExpression(conditionalExpression, truePart, falsePart);
						}
					}
				}
				
				return null;
			}
			
			return conditionalExpression;
		}
		
		return null;
	}
	
	public Tree orExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = andExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"\\|\\|")) != null){
				if((secondConditionalExpression = orExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return firstConditionalExpression;
		}
		
		return null;
	}
	
	public Tree andExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = equalityExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"&&")) != null){
				if((secondConditionalExpression = andExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return firstConditionalExpression;
		}
		
		return null;
	}
	
	public Tree equalityExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = relationalExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"===?")) != null){
				if((secondConditionalExpression = equalityExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return firstConditionalExpression;
		}
		
		return null;
	}
	
	public Tree relationalExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = additiveExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(>=?|<=?)")) != null){
				if((secondConditionalExpression = relationalExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return firstConditionalExpression;
		}
		
		return null;
	}
	
	public Tree additiveExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = multiplicativeExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(\\+|-)")) != null){
				if((secondConditionalExpression = additiveExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return firstConditionalExpression;
		}
		
		return null;
	}
	
	public Tree multiplicativeExpressionPro(){
		Tree firstConditionalExpression = null, secondConditionalExpression = null;
		Symbol operator = null;
		
		if((firstConditionalExpression = unaryExpressionPro()) != null){
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(\\*|/)")) != null){
				if((secondConditionalExpression = multiplicativeExpressionPro()) != null){
					return new BooleanExpression(firstConditionalExpression, 
							operator, secondConditionalExpression);
				}
				
				return null;
			}
			
			return firstConditionalExpression;
		}
		
		return null;
	}
	
	public Tree unaryExpressionPro(){
		Tree operand = null, primaryExpression= null, expression = null;
		Symbol operator = null, identifier = null;
		
		if((operator = accept(Symbol.Id.PUNCTUATORS,"(\\+|-)")) != null){
			if((operand = unaryExpressionPro()) != null){
				return new UnaryExpression(operator, operand);
			}
			
			return null;
		}
		
		if((operator = accept(Symbol.Id.KEYWORD,"new")) != null){
			if((operand = constructorCallPro()) != null){
				return new UnaryExpression(operator, operand);
			}
			
			return null;
		}
		
		if((operator = accept(Symbol.Id.KEYWORD,"delete")) != null){
			if((operand = memberExpressionPro()) != null){
				return new UnaryExpression(operator, operand);
			}
			
			return null;
		}
		
		if((operator = accept(Symbol.Id.PUNCTUATORS,"(\\+\\+|--)")) != null){
			if((operand = memberExpressionPro()) != null){
				return new UnaryExpression(operator, operand);
			}
			
			return null;
		}
		
		if((identifier = accept(Symbol.Id.IDENTIFIER_NAME))!=null){			
			if(accept(Symbol.Id.PUNCTUATORS,"\\(")!=null){
				expression = argumentListPro();
				if(accept(Symbol.Id.PUNCTUATORS,"\\)")!=null){
					return new ConstructorCallExpression(identifier, expression);
				}
				return null;
			}
			
			if(accept(Symbol.Id.PUNCTUATORS,"\\[")!=null){
				if((expression = expressionPro()) != null){
					if(accept(Symbol.Id.PUNCTUATORS,"\\]")!=null){
						if((operator = accept(Symbol.Id.PUNCTUATORS,"(\\+\\+|--)")) != null){
							return new UnaryExpression(identifier,  
										new MemberExpression(new PrimaryExpression(operator), 
										expression), true);
						}
						
						return new MemberExpression(new PrimaryExpression(identifier), 
								expression);
					}
				}
				return null;
			}
			
			return new PrimaryExpression(identifier);
		}
		
		if((primaryExpression = primaryExpressionPro()) != null){
			
			return primaryExpression;
		}
			
		
		return null;
	}
	
	private Tree constructorCallPro(){
		Tree argumentListOpt=null;
		Symbol identifier;

		if((identifier = accept(Symbol.Id.IDENTIFIER_NAME))!=null){
			if(accept(Symbol.Id.PUNCTUATORS,"\\(")!=null){
				argumentListOpt = argumentListPro();
				if(accept(Symbol.Id.PUNCTUATORS,"\\)")!=null){
					return new ConstructorCallExpression(identifier, argumentListOpt);
				}
				return null;
			}
			
			return new PrimaryExpression(identifier);
		}
		
		return null;
	}
	
	private Tree argumentListPro(){
		Tree argumentList = null;
		ArgumentListExpression argumentLists = null;
		
		if((argumentList = expressionPro()) != null){
			if(accept(Symbol.Id.PUNCTUATORS,",")!=null){
				if((argumentLists = (ArgumentListExpression) argumentListPro()) != null){
					return new ArgumentListExpression(argumentList, argumentLists);
				}
				return null;
			}
			return new ArgumentListExpression(argumentList);
			
		}
		
		return null;
	}
	
	private Tree memberExpressionPro(){
		Tree expression=null;
		Symbol identifier = null;
		if((identifier = accept(Symbol.Id.IDENTIFIER_NAME))!=null){
			if(accept(Symbol.Id.PUNCTUATORS,"\\[")!=null){
				if((expression = expressionPro()) != null){
					if(accept(Symbol.Id.PUNCTUATORS,"\\]")!=null){
						return new MemberExpression(new PrimaryExpression(identifier), expression);
					}
				}
				return null;
			}
			
			return new PrimaryExpression(identifier);
		}
		
		return null;
	}
	
	private Tree primaryExpressionPro(){
		Symbol pExpression = null;
		Tree expression = null;
		
		if(accept(Symbol.Id.PUNCTUATORS,"\\(")!=null){
			if ((expression = expressionPro())!=null){
				if(accept(Symbol.Id.PUNCTUATORS,"\\(")!=null){
					return expression;
				}
			}
			return null;
		}
		
		if((pExpression = accept(Symbol.Id.NUMERIC_LITERAL))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.FLOATINGPOINT_LITERAL))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.STRING_LITERAL))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.BOOLEAN_LITERAL))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.BOOLEAN_LITERAL))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.KEYWORD,"null"))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		if((pExpression = accept(Symbol.Id.KEYWORD,"NaN"))!=null){
			return new PrimaryExpression(pExpression);
		}
		
		return null;
	}
}
