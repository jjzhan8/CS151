package application;

public class Asset extends General_Info{
	private Category category;
	private Location location;
	
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
	
	
}
