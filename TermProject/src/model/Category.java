package model;

import java.util.HashMap;

public class Category extends Name implements DataProcess{
	
	public static HashMap<String, Category> category = new HashMap<String, Category>();
	
	public Category() {
		super();
	}
	public Category(String arg) {
		super(arg);
	
	}
	public void display() {
		System.out.println("Category: " + this.getName());
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public String saveToCsv() {
		String res;
		res = new String(this.getName());
		
		return res;
	}
	

}
