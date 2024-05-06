package model;

import java.util.HashMap;

public class Location extends General_Info implements DataProcess{
	
	public static HashMap<String, Location> location = new HashMap<String, Location>();
	
	public Location() {
		super();
	}
	
	public Location(String argN, String argD) {
		super(argN,argD);
	}
	
	public void display() {
		System.out.println("Location: " + this.getName());
		System.out.println("Description: " + this.getDescription());
	}
	
	@Override
	public String toString() {
		return getName();
	}
	public String saveToCsv() {
		String res;
		res = new String(this.getName() + "," + this.getDescription());
		
		return res;
	}
}
