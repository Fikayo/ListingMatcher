package sortable.util;

import java.util.ArrayList;
import java.util.List;

import sortable.data.Listing;

/**
 * A object representing a product to listing match.
 * @author fikayo
 * */
public class Match {
	
	private String product_name;
	private List<Listing> listings;
	
	public Match() {
		this.listings = new ArrayList<>();
	}
	
	public void setProductName(String product_name) {
		this.product_name = product_name;
	}
	
	public void addListing(Listing listing) {
		this.listings.add(listing);
	}
}