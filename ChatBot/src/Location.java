import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class holds the location information for the users
 * conversation. This class is built by LocationFactory which
 * queries Google for general information, such as distances,
 * weather, and nearby searches.
 * 
 * @author Brett Dupree
 *
 */
public class Location {
    LocationFactory lf = new LocationFactory();
    // Convert list into list of object that store places with name, address information
    public HashMap<String, ArrayList<Places>> places = new HashMap<>();
    public HashMap<String, String> placesAddress = new HashMap<>();
    public String origin = "Kelowna";
    public String destination;
    public double tempInCelcius;
    public String weatherDescription;
    public double distanceFromOrigin;

    public Location(String destination) {
        this.destination = destination.toLowerCase();
        lf.build(this);
    }

    public Location(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
        lf.build(this);
    }
    @SuppressWarnings("static-access")
    public String estimateTravelCost(String location) {
        return "Driving to " + location + " from " + StringUtils.toTitleCase(this.origin) + " would cost approximately $" + lf.round((this.distanceFromOrigin /2)*1.3, 2) + " round trip.";
    }
    @SuppressWarnings("static-access")
    public String estimateFlightCost(String location) {
        return "Flying to " + location + " from " + this.origin + " would cost approximately $" + lf.round(this.distanceFromOrigin / 2.92, 2);
    }

    @SuppressWarnings("static-access")
	// Query Google for nearby locations, such as shopping_centers, restaurants, lodging, etc
    public String getPlaces(String keyword) {
        if (!places.containsKey(keyword)) {
            if (!lf.getPlaces(this, keyword))
                return null;
        } 
        ArrayList<Places> pl = places.get(keyword);
        // Build a record of directions to each place found for later use.
        for(Places p : pl){
        	placesAddress.put(p.getName(), p.getAddress());
        }
        int r = new java.util.Random().nextInt(pl.size());
        String name = pl.get(r).getName();
        return name;
    }
    
    public String getDirections(String name){
    	try{
    		if(placesAddress.containsKey(name)){
    			return lf.getDirections(this, placesAddress.get(name));	
    		}
    	} catch (NullPointerException e){
    		return "I have no record of " + name + ".";
    	}
    	return "I have no record of " + name + ".";
    }
}