package application;

public class General_Info extends Name{
	String description;
	
	public General_Info(String argN, String argD) {
		// first argument: name		second argument: description
		super(argN);
		
	}
	//accessor & mutator
	public void setDesciption(String arg) {
		description = arg;
	}
	public String getDescription() {
		return description;
	}
	
}
