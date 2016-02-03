package sortable.data;

import java.util.Objects;

import sortable.util.Token;
import com.google.gson.*;

/**
 * Represents a single product from the input file 'products.txt'.
 * @author fikayo
 * */
public class Product implements Comparable<Product> {

	private String product_name;
	private String model;
	private String family;
	private String manufacturer;
	private String announced_date;
	
	public Product(String name, String family, String model, String manufacturer, String announced_date) {
		this.product_name = name;
		this.announced_date = announced_date;

		this.manufacturer = Token.addManufacturer(manufacturer);
		this.family = Token.addFamily(family);
		this.model = Token.addModel(model);
	}
	
	public Product(Product prod) {
		this(prod.product_name, prod.family, prod.model, prod.manufacturer, prod.announced_date);
	}
	
	public String getName() {
		return product_name;
	}
	
	public String getFamily() {
		return family;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public String getAnnouncedDate() {
		return announced_date;
	}

	public static Product parseJson(String json) {
		Gson gson = new Gson();
		return new Product(gson.fromJson(json, Product.class));
	}

	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Product)) return false;
		Product var = (Product)obj;
	
		boolean manEqu = (this.manufacturer == null && var.manufacturer == null) || (this.manufacturer != null && this.manufacturer.equalsIgnoreCase(var.manufacturer));
		boolean famEqu = (this.family == null && var.family == null) || (this.family != null && this.family.equalsIgnoreCase(var.family));
		boolean modEqu = (this.model == null && var.model == null) || (this.model != null && this.model.equalsIgnoreCase(var.model));
		
		return manEqu && famEqu && modEqu;
	}
	
	@Override
	public int hashCode() {
		int code = 17;
		if(this.manufacturer != null) {
			code += this.manufacturer.hashCode();
		}
		
		if(this.family != null) {
			code += this.family.hashCode();
		}
		
		if(this.model != null) {
			code += this.model.hashCode();
		}
		
		return code;
	}
	
	@Override
	public int compareTo(Product o) {
		if(o == null) return 1;

		// First by manufacturer
		int result = compStr(this.manufacturer, o.manufacturer);
		
		if(result == 0) {
			
			// Then by family
			result = compStr(this.family, o.family);
			if(result == 0) {
				
				// Then by model
				return compStr(this.model, o.model);
			}
		}
		
		return result;
	}
	
	private int compStr(String a, String b) {
		
		if(Objects.equals(a, b)) return 0;
		
		if(a == null && b != null) return -1;
		
		if(a != null && b == null) return 1;
		
		return a.compareToIgnoreCase(b);
	}
}
