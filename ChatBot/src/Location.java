import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    LocationFactory lf = new LocationFactory();
    public HashMap<String, ArrayList<String>> places = new HashMap<>();
    public String origin = "Kelowna";
    public String destination;
    public double tempInCelcius;
    public String weatherDescription;
    public double distanceFromOrigin;

    public Location(String destination) {
        this.destination = destination;
        lf.build(this);
    }

    public Location(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
        lf.build(this);
    }
    @SuppressWarnings("static-access")
    public String estimateTravelCost() {
        return "Driving to " + this.destination + ", from " + this.origin + " would cost approximately $" + lf.round(this.distanceFromOrigin / 2.02, 2);
    }
    @SuppressWarnings("static-access")
    public String estimateFlightCost() {
        return "Flying to " + this.destination + ", from " + this.origin + " would cost approximately $" + lf.round(this.distanceFromOrigin / 2.92, 2);
    }

    @SuppressWarnings("static-access")
	public ArrayList<String> getPlaces(String keyword) {
        if (!places.containsKey(keyword)) {
            if (!lf.getPlaces(this, keyword))
                return null;
        } 
        ArrayList<String> pl = places.get(keyword);
        return pl;
    }
}