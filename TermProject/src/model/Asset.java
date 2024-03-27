package model;

public class Asset extends General_Info implements DataProcess{
	private Category category;
	private Location location;
	private String purchaseDate;
	private Double purchaseValue;
	private String warrantyExpDate;

	public Asset() {
		super();
		this.setCategory(new Category());
		this.setLocation(new Location());
		this.setPurchaseDate("");
		this.setPurchaseValue(0.0);
		this.setWarrantyExpDate("");

	}

	public Asset(String argN, String argD, Category argC, Location argL) {
		super(argN, argD);
		this.setCategory(argC);
		this.setLocation(argL);
		this.setPurchaseDate("");
		this.setPurchaseValue(0.0);
		this.setWarrantyExpDate("");

	}

	// accessor & mutator
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

	public void setPurchaseValue(Double arg) {
		purchaseValue = arg;
	}

	public Double getPurchaseValue() {
		return purchaseValue;
	}

	public void setWarrantyExpDate(String arg) {
		warrantyExpDate = arg;
	}

	public String getWarrantyExpDate() {
		return warrantyExpDate;
	}

	public String saveToCsv() {
		String res;
		res = new String(this.getName() + "," + this.getDescription() + "," + this.getCategory().getName() + ","
				+ this.getLocation().getName() + "," + this.getPurchaseDate() + "," + this.getPurchaseValue() + ","
				+ this.getWarrantyExpDate());

		return res;
	}

}
