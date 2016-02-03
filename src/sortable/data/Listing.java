package sortable.data;

import com.google.gson.Gson;

public class Listing {

	private String title;
	private String manufacturer;
	private String currency;
	private double price;
	
	public Listing(String title, String manufacturer, String currency, double price) {
		this.title = title;
		this.manufacturer = manufacturer;
		this.currency = currency;
		this.price = price;		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Listing)) return false;
		Listing var = (Listing)obj;
		
		return this.title.equals(var.title) &&
				this.manufacturer.equals(var.manufacturer) &&
				this.currency.equals(var.currency) &&
				this.price == var.price;
	}
	
	@Override
	public int hashCode() {
		return this.title.hashCode();
	}
	
	public static Listing parseJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Listing.class);
	}
}
