package sortable.util;

import java.util.HashSet;

/**
 * Represents a set of strings
 * 
 * @author fikayo
 */
public enum Token {

	/**
	 * A possible manufacturer from the product manufacturers.
	 */
	Manufacturer {
		@Override
		public boolean matches(java.lang.String input) {
			return Token.manufacturers.contains(input);
		}
	},
	
	/**
	 * A possible product family from the product families.
	 */
	Family {
		@Override
		public boolean matches(java.lang.String input) {
			return Token.families.contains(input);
		}
	},
	
	/**
	 * A possible product model from the product models.
	 * */
	Model {
		@Override
		public boolean matches(java.lang.String input) {
			String model = input.trim().replace(" ", "");			
			return Token.models.contains(model);
		}
	},
	
	;
	
	private final static HashSet<String> manufacturers = new HashSet<>();
	private final static HashSet<String> families = new HashSet<>();
	private final static HashSet<String> models = new HashSet<>();
	
	public abstract boolean matches(String input);

	/**
	 * Returns the first {@link Token} that matches the input string.
	 * Currently O(n)
	 * 
	 * @param input
	 * @return The first {@link Token} that matches the input.
	 */
	public static Token parse(String input) {

		for (Token token : Token.values()) {
			if (token.matches(input)) {
				return token;
			}
		}

		throw new IllegalArgumentException("No token matches the given input: "
				+ input);
	}

	public static Token tryGetValue(String input) {
		try {
			return Token.parse(input);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}
	
	public static String addManufacturer(String m) {
		if(m != null && !m.isEmpty()) {
			String manu = m.toLowerCase().trim();
			Token.manufacturers.add(manu);
			return manu;
		}
		
		return m;
	}
	
	public static String addFamily(String f) {
		if(f != null && !f.isEmpty()) {
			String fam = f.toLowerCase().trim();
			Token.families.add(fam);
			return fam;
		}
		
		return f;
	}
	
	public static String addModel(String m) {
		if(m != null && !m.isEmpty()) {
			String model = m.toLowerCase().trim().replace(" ", "");
			Token.models.add(model);
			return model;
		}
		
		return m;
	}
}
