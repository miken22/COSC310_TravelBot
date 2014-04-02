/**
 * This class stores information about places that have been searched for
 * using the Google Nearby Search API. These are stored in the ArrayList in the
 * Location class to be referenced later when the user asks for directions between locations.
 * 
 * 
 * @author Mike Nowicki
 *
 */
public class Places {
	/**
	 * Place name
	 */
	private final String name;
	/**
	 * Place address
	 */
	private final String address;
	
	/**
	 * Set the place information
	 * @param name Name of the place
	 * @param address Complete address of the place
	 */
	public Places(String name, String address){
		this.name = name;
		this.address = address;
	}
	
	/**
	 * Get the place name
	 * @return Place name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get the place address
	 * @return Place address
	 */
	public String getAddress(){
		return address;
	}
}