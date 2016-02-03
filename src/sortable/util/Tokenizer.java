package sortable.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * This class tokenizes an input string from a product {@link Listing}.
 * @author fikayo
 * */
public final class Tokenizer {
		
	public static ListMultimap<Token, String> tokenize(String input) {
		
		ListMultimap<Token, String> tokens = ArrayListMultimap.create();
		String[] words = input.split("\\s+|\\(|\\)|\\/");
		
		for(String word : words) {
			String toLower = word.toLowerCase();
			Token token = Token.tryGetValue(toLower);
			if(token != null) {
				tokens.put(token, toLower);
			}
		}
		
		return tokens;
	}	
}
