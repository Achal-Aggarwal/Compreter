package compreter;


import java.io.BufferedReader;
import java.io.IOException;

import compreter.parsertree.*;

public class Parser {
	Lexer lex;
	Symbol token;
	LoopControlStatement loopControl = null;
	boolean foundLoopControl = false;
	public Parser(BufferedReader input) throws IOException{
		lex = new Lexer(input);
		token = null;
	}
	public int parse(){
		Tree tree = programPro();
		//System.out.println(tree);
		System.out.println(tree.getCode());
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
	
	private Tree programPro(){
		Tree element = null;
		Tree program = null;
		
		if((element = elementPro())!=null){
			if((program = programPro())!=null){
				return new Program(element, program);
			}
			
			return element;
		}

		return null;
	}
	
	private Tree elementPro(){
		Symbol identifier = null;
		Tree parameterList = null,
				compoundStatement = null;
		if(accept(Symbol.Id.KEYWORD, "function")!=null){
			if((identifier = accept(Symbol.Id.IDENTIFIER_NAME))!=null){
				if(accept(Symbol.Id.PUNCTUATORS, "\\(")!=null){
					parameterList = parameterListPro();
					
					if(accept(Symbol.Id.PUNCTUATORS, "\\)")!=null){
						if((compoundStatement = compoundStatementPro())!=null){
							return new FunctionStatement(identifier, parameterList, compoundStatement);
						}
					}
					
				}
				
			}
		}
		
		if((compoundStatement = statementsPro())!= null){
			return compoundStatement;
		}
		
		return null;
	}
	
	private Tree parameterListPro(){
		Symbol identifier = null;
		Tree identifiers = null;
		
		if((identifier = accept(Symbol.Id.IDENTIFIER_NAME))!=null){
			if(accept(Symbol.Id.PUNCTUATORS,",")!=null){
				if((identifiers = parameterListPro())!=null){
					return new ParameterList(new PrimaryExpression(identifier), identifiers);
				}
				
				return null;
			}
			
			return new ParameterList(new PrimaryExpression(identifier));
		}
		
		return null;
	}
	
	private Tree compoundStatementPro(){
		Tree statements = null;
		
		if(accept(Symbol.Id.PUNCTUATORS,"\\{")!=null){
			statements = statementsPro();
			if(accept(Symbol.Id.PUNCTUATORS,"\\}")!=null){
				return statements;
			}
		}
		
		return null;
	}
	
	private Tree statementsPro(){
		Tree statement = null,
				statements = null;
		
		if((statement = statementPro())!= null){
				if((statements = statementsPro())!=null){
					return new Statements(statement, statements);
				}

				return statement;
		}
		
		return null;
	}
	
	private Tree statementPro(){
		Tree condition = null,
				truePart = null,
				falsePart =  null,
				expression = null,
				compoundStatement = null;
		
		accept(Symbol.Id.PUNCTUATORS,";");
		
		if(accept(Symbol.Id.KEYWORD,"if")!=null){
			if((condition = jumpConditionPro()) != null){
				if((truePart = statementPro()) != null){
					accept(Symbol.Id.PUNCTUATORS,";");
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
		
		if(accept(Symbol.Id.KEYWORD,"while")!=null){
			if((condition = jumpConditionPro()) != null){
				foundLoopControl = false;
				if((truePart = statementPro()) != null){
					Tree  loop = new WhileStatement(condition, truePart);
					if(foundLoopControl)
						loopControl.setLoop(loop);
					loopControl = null;
					foundLoopControl = false;
					return loop; 
				}
			}
			
			return null;
		}
		
		if(accept(Symbol.Id.KEYWORD,"break")!=null){
			if(accept(Symbol.Id.PUNCTUATORS,";")!=null){
				loopControl = new LoopControlStatement("break");
				foundLoopControl = true;
				return loopControl;
			}
			
			return null;
		}
		
		if(accept(Symbol.Id.KEYWORD,"continue")!=null){
			if(accept(Symbol.Id.PUNCTUATORS,";")!=null){
				loopControl = new LoopControlStatement("continue");
				foundLoopControl = true;
				return loopControl;
			}
			
			return null;
		}
		
		if(accept(Symbol.Id.KEYWORD,"return")!=null){
			if((expression = expressionPro())!=null){
				return new ReturnStatement(expression);
			}
			
			return new ReturnStatement();
		}
		
		if((compoundStatement = compoundStatementPro())!=null){
			return compoundStatement;
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
		
		if(accept(Symbol.Id.PUNCTUATORS,"\\(")!=null){
			if((expression = expressionPro()) != null){
				if(accept(Symbol.Id.PUNCTUATORS,"\\)")!=null){
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
	
	private Tree expressionPro(){
		Tree conditionalExpression = null, expression = null;
		
		if((conditionalExpression = conditionalExpressionPro()) != null){
			if((accept(Symbol.Id.PUNCTUATORS,"=")) != null){
				if((expression = expressionPro()) != null){
					return new AssignmentExpression(conditionalExpression, expression);
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
					return new BinaryExpression(firstConditionalExpression, 
							operator, secondConditionalExpression, BinaryExpression.Type.BOOLEAN);
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
					return new BinaryExpression(firstConditionalExpression, 
							operator, secondConditionalExpression, BinaryExpression.Type.BOOLEAN);
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
					return new BinaryExpression(firstConditionalExpression, 
							operator, secondConditionalExpression, BinaryExpression.Type.RELATIONAL);
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
					return new BinaryExpression(firstConditionalExpression, 
							operator, secondConditionalExpression, BinaryExpression.Type.RELATIONAL);
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
					return new BinaryExpression(firstConditionalExpression, 
							operator, secondConditionalExpression, BinaryExpression.Type.AIRTHMETIC);
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
					return new BinaryExpression(firstConditionalExpression, 
							operator, secondConditionalExpression, BinaryExpression.Type.AIRTHMETIC);
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
			
			if((operator = accept(Symbol.Id.PUNCTUATORS,"(\\+\\+|--)")) != null){
				return new UnaryExpression(operator, new PrimaryExpression(identifier), true);
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
				if(accept(Symbol.Id.PUNCTUATORS,"\\)")!=null){
					return new BracedExpression(expression);
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
