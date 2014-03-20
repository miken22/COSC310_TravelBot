/**
 * This class stores information about places that have been searched for
 * using the Google Nearby Search API. These are stored in the ArrayList in the
 * Location class to be referenced later when the user asks for directions between locations.
 * 
 * 
 * @author Mike-Laptop
 *
 */
public class Places {
	private final String name;
	private final String address;
	
	public Places(){
		name = "";
		address = "";
	}
	
	public Places(String name, String address){
		this.name = name;
		this.address = address.replaceAll(" ", "");
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
}