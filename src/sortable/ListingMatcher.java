package sortable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sortable.data.Listing;
import sortable.data.Product;
import sortable.util.Match;
import sortable.util.MatchMap;
import sortable.util.SortedList;
import sortable.util.Token;
import sortable.util.Tokenizer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

/**
 * @author fikayo
 * */
public class ListingMatcher {

	private String listingsFile;
	private String productsFile;
	private MatchMap matches;
	
	// Map by manufacturer, then by family
	private HashMap<String, HashMap<String, SortedList<Product>>> products = 
			new HashMap<String, HashMap<String, SortedList<Product>>>();
	
	public ListingMatcher(String listingsFile, String productsFile) {
		this.listingsFile = listingsFile;
		this.productsFile = productsFile;
		this.matches = new MatchMap();
	}	
	
	public void run() {

		System.out.println("Beginning program");
		
		 // Add products and sort them by manufacturer
		 for(String json : readFile(productsFile)) {
			 this.addProduct(Product.parseJson(json));
		 }
		 
		 // Add all listings to a multiset		 
		 HashMultiset<Listing> listings = HashMultiset.create();
		 for(String json : readFile(listingsFile)) {
			 Listing listItem = Listing.parseJson(json);
			 listings.add(listItem);
			 
			 this.matchListing(listItem);
		 }
	}
	
	public void printResults(String resultsFile) {
		
		Gson gson = new Gson();
		Collection<Match> list = this.matches.getMatches();
		
		StringBuilder builder = new StringBuilder();
		for(Match match : list) {
			builder.append(gson.toJson(match) + "\n");
		}

		System.out.println("Printing to output file");
		this.writeFile(resultsFile, builder.toString());
		System.out.println("Filewrite complete");			
	}
	
	public void getDiffs(String diffFile) {
		
		Set<Product> matchedProds = this.matches.getProductSet();
		Set<Product> allProds = new HashSet<>();
		
		for(HashMap<String, SortedList<Product>> famMap : this.products.values()) {
			for(SortedList<Product> prodList : famMap.values()) {
				allProds.addAll(prodList);
			}
		}
		
		allProds.removeAll(matchedProds);

		Gson gson = new Gson();
		StringBuilder builder = new StringBuilder();
		for(Product prod : allProds) {
			builder.append(gson.toJson(prod) + "\n");
		}

		System.out.println("\nPrinting to diff file");
		this.writeFile(diffFile, builder.toString());
		System.out.println("Diff write complete");
	}
	
	private void addProduct(Product prod)	{
		
		if(products.containsKey(prod.getManufacturer())) {
			
			if(products.get(prod.getManufacturer()).containsKey(prod.getFamily())) {
				// Add product to sorted list
				products.get(prod.getManufacturer()).get(prod.getFamily()).add(prod);
			} else {				
				// Add family to manufacturer
				SortedList<Product> list = new SortedList<>();
				list.add(prod);
				products.get(prod.getManufacturer()).put(prod.getFamily(), list);				
			}
			
		} else {			
			// Add manufacturer to the product list
			products.put(prod.getManufacturer(), new HashMap<String, SortedList<Product>>());
			addProduct(prod);
		}		
	}
	
	private void matchListing(Listing listing) {
		
		String manufacturer = null;
		String family = null;
		ListMultimap<Token, String> tokens = Tokenizer.tokenize(listing.getTitle());
		
		// First check the given manufacturer
		if(products.containsKey(listing.getManufacturer())) {
			manufacturer = listing.getManufacturer();			
		} else {
			
			// Try to get manufacturer from tokens
			if(tokens.containsKey(Token.Manufacturer)) {				
				manufacturer = tokens.get(Token.Manufacturer).get(0);				
			} else {
				// Manufacturer not found
				error("Can't find manufacturer");
			}
			
		}
		
		if(manufacturer != null) {
			// Now check for the family
			if(tokens.containsKey(Token.Family)) {				
				family = tokens.get(Token.Family).get(0);				
			} else {
				// Family not found
				error("Can't find family");
			}		
			
			if(family != null) {
				List<Product> options = products.get(manufacturer).get(family);
				if(tokens.containsKey(Token.Model)) {
					
					Product match = this.matchOptions(options, tokens.get(Token.Model));
					if(match != null) {
						this.matches.put(match, listing);
					}
					
				} else {
					// No keywords
					error("There are no keywords found");
				}
			}
		}
	}
	
	/**
	 * Uses the keywords from the title of a {@link Listing} to match that Listing one of the 
	 * {@link Product}s which have been marked as options
	 * 
	 * @param options - list of {@link Product}s which have been narrowed by some criteria
	 * */
	private Product matchOptions(List<Product> options, List<String> possibleModels) {
		
		// Given options are sorted by model and product name
		String manufacturer = options.get(0).getManufacturer();
		String family = options.get(0).getFamily();
		
		// It's ok to iterate the possible models because there would be so few (maybe 2 or at most 3)
		for(String modelStr : possibleModels) {
			
			// Made a dummy product for the binary search.
			String prod_name = manufacturer + "_" + family + "_" + modelStr;
			Product find = new Product(prod_name, family, modelStr, manufacturer, null);
			int index = Collections.binarySearch(options, find);
			if(index > 0) {
				return options.get(index);
			}
		}
		
		return null;
	}
	
	private List<String> readFile(String file) {
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			lines = new ArrayList<String>();
		}
		
		return lines;
	}
	
	private boolean writeFile(String path, String str) {
		
		boolean success = true;
		PrintWriter writer = null;
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();
			writer = new PrintWriter(file, "UTF-8");
			writer.write(str);
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			success = false;
		} finally {
			if(writer != null) {
				writer.flush();
				writer.close();
			}
		}
		
		return success;
	}

	private void error(String message) {
		// throw new Exception(message);
	}	
}
