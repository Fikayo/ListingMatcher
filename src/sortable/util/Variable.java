package sortable.util;

public abstract class Variable {
	
	private String symbol;
	
	public Variable(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the symbol of the NonTermial
	 */
	public String getSymbol() {
		return symbol;
	}
	
	public abstract Variable simplify();
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Variable)) return false;
		Variable var = (Variable)obj;
		
		return this.symbol.equals(var.symbol);
	}
	
	@Override
	public int hashCode() {
		return this.symbol.hashCode();
	}
	
	@Override
	public String toString() {
		return this.symbol;
	}
}
