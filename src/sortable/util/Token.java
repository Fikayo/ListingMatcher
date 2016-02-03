package sortable.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents a set of strings
 * 
 * @author fikayo
 */
public enum Token {

	/**
	 * Access modifiers like public, private
	 */
	Manufacturer {
		@Override
		public boolean matches(java.lang.String input) {
			return Token.manufacturers.contains(input);
		}
	},
	
	/**
	 * static
	 */
	Family {
		@Override
		public boolean matches(java.lang.String input) {
			return Token.families.contains(input);
		}
	},
	
	Model {
		@Override
		public boolean matches(java.lang.String input) {
			String model = input.trim().replace(" ", "");
//			char[] chars = model.toCharArray();
//			Arrays.sort(chars);
//			String sortedChars = String.valueOf(chars);
//			
//			return Token.modelMap.containsKey(sortedChars); 
			
			return Token.models.contains(model);
		}
	},
	
	;
	
	public final static HashSet<String> manufacturers = new HashSet<>();
	public final static HashSet<String> families = new HashSet<>();
	private final static HashSet<String> models = new HashSet<>();
	private final static HashMap<String, SortedSet<String>> modelMap = new HashMap<>();
	
	public abstract boolean matches(String input);

	/**
	 * Returns the first {@link TokenInterface} that matches the input string.
	 * Currently O(n)
	 * 
	 * @param input
	 * @return The first {@link TokenInterface} that matches the input.
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
	
	public static void addModelOld(String m) {
		String model = m.trim().replace(" ", "");
		char[] chars = model.toCharArray();
		Arrays.sort(chars);
		String sortedChars = String.valueOf(chars);
		
		// Use the sorted string as the key to the map
		if(!Token.modelMap.containsKey(sortedChars)) {
			Token.modelMap.put(sortedChars, new TreeSet<String>());
		}
		
		// Add the actual model as one of the values;
		Token.modelMap.get(sortedChars).add(model);
	}
}
