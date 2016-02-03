package sortable.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import sortable.data.Listing;
import sortable.data.Product;

/**
 * Maps {@link Product}s to their associated {@link Match} object.
 * @author fikayo
 * */
public class MatchMap {

	private HashMap<Product, Match> matches;
	
	public MatchMap() {
		this.matches = new HashMap<Product, Match>();
	}
	
	public void put(Product product, Listing listing) {
		if(this.matches.containsKey(product)) {
			this.matches.get(product).addListing(listing);
			return;
		}
		
		Match match = new Match();
		match.setProductName(product.getName());
		match.addListing(listing);
		this.matches.put(product, match);
	}
	
	public Set<Product> getProductSet() {
		return this.matches.keySet();
	}
	
	public Collection<Match> getMatches() {
		return this.matches.values();
	}	
}
