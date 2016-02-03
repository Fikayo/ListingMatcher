package sortable.util;

import java.util.HashMap;

/**
 * Represents a token which has a corresponding lexeme.
 * @author fikayo
 */
public class Terminal extends Variable {
	
	private static HashMap<String, Terminal> existingTerminals;
	
	static {
		Terminal.existingTerminals = new HashMap<String, Terminal>();
	}
	
	private Token token;
	private String lexeme;
	
	public Terminal(Token token) {
		super(null);		
		this.token = token;
		this.lexeme = null;
	}
	
	public Terminal(String lexeme) {
		super(lexeme);
		this.token = Token.parse(lexeme);
		this.lexeme = lexeme;
	}
	
	public Terminal(String lexeme, Token token) {
		super(lexeme);
		this.lexeme = lexeme;
		this.token = token;
		
		if(!token.matches(lexeme)) {
			throw new IllegalArgumentException("Token does not match lexeme");
		}
	}
	
	private Terminal(Token token, String lexeme) {
		super(lexeme);
		this.lexeme = lexeme;
		this.token = token;
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public String getLexeme() {
		return this.lexeme;
	}
	
	@Override
	public Variable simplify() {
		// Default implementation is to return self
		return this;
	}
	
	@Override
	public int hashCode() {
		return this.token.hashCode() + 17;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Terminal)) return false;
		if(this == obj) return true;
		Terminal var = (Terminal)obj;
				
		if(this.lexeme != null && var.lexeme != null) {
			return this.lexeme.equals(var.lexeme) && this.token.equals(var.token);
		}
		
		// Check tokens if one of us have a null lexeme (general terminal)
		return this.token.equals(var.token);
	}
	
	public static Terminal create(String lexeme) {
		if(!Terminal.existingTerminals.containsKey(lexeme)) {
			Terminal term = new Terminal(lexeme);
			Terminal.existingTerminals.put(lexeme, term);
			return term;
		}
		
		return Terminal.existingTerminals.get(lexeme);
	}
	
	public static Terminal create(Token token) {
		return new Terminal(token);
	}
	
	@Override
	public String toString() {
		if(lexeme == null) {
			return token.toString();
		} 
		
		return super.toString();
	}
}
