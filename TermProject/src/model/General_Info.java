package model;

public class General_Info extends Name{
	String description;
	
	public General_Info() {
		super("");
		this.setDescription("");
	}
	
	public General_Info(String argN, String argD) {
		// first argument: name		second argument: description
		super(argN);
		this.setDescription(argD);
	}
	//accessor & mutator
	public void setDescription(String arg) {
		description = arg;
	}
	public String getDescription() {
		return description;
	}
	
}
