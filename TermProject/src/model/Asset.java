package model;

public class Asset extends General_Info{
	private Category category;
	private Location location;
	private String purchaseDate;
	private String purchaseValue;
	private String warrantyExpDate;
	
	public Asset(String argN, String argD, Category argC, Location argL) {
		super(argN,argD);
		this.setCategory(argC);
		this.setLocation(argL);
		
	}
	
	//accessor & mutator
	public void setCategory(Category arg) {
		category = arg;
	}
	public void setLocation(Location arg) {
		location = arg;
	}
	public Category getCategory() {
		return category;
	}
	public Location getLocation() {
		return location;
	}
	public void setPurchaseDate(String arg) {
		purchaseDate = arg;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseValue(String arg) {
		purchaseValue = arg;
	}
	public String getPurchaseValue() {
		return purchaseValue;
	}
	public void setWarrantyExpDate(String arg) {
		warrantyExpDate = arg;
	}
	public String getWarrantyExpDate() {
		return warrantyExpDate;
	}
	
	
}
