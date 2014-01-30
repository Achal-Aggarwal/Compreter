package compreter;

public class Symbol {
	String clas;
	String value;
	public static enum Id {COMMENT, IDENTIFIER_NAME, NUMERIC_LITERAL, STRING_LITERAL, KEYWORD, OPERATOR};
	Id code;
	public Symbol(Id code){
		this.code = code;	
		this.clas = code.toString();
		this.value = "";
	}
	public Symbol(Id code, String value){
		this.code = code;
		this.clas = code.name();
		this.value = value;
	}
	
	public Symbol value(String value){
		this.value = value;
		return this;
	}
	public String value(){
		return this.value;
	}
	
	public String getClas(){
		return this.clas;
	}
}
