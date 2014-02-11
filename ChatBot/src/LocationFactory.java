import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocationFactory {
    public void build(Location loc) {
        setWeather(loc);
        setDistance(loc);
        getPlaces(loc, "food");
        getPlaces(loc, "lodging");
    }

    /**
     * @param args
     * @throws IOException
     * @throws MalformedURLException
     */
    /*
     * Return values could be to any decimal place, we only want two.
	 */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private static boolean setDistance(Location loc) {
		/*
		 * Base URL for distance query. Takes an origin and destination parameter to constructing the String 'url'.
		 * URLEncoder is used to deal with spaces and such.
		 */
        String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + URLEncoder.encode(loc.origin) + "&destinations=" + URLEncoder.encode(loc.destination) + "&mode=driving&sensor=false&language=en-EN";
		
		/*
		 *  We call the openStream() method from the URL class, and read the input line by line with the Scanner class.
		 *  On error return 0
		 */
        try {
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";
            }
		 /*
		  *  org.Json library. A JSON object is created from the above String.
		  *  
		  *  Example of the JSON object return can be seen by navigating to the following URL.
		  *  http://maps.googleapis.com/maps/api/distancematrix/json?origins=Kelowna%2C+BC&destinations=Vernon%2C+BC&mode=driving&sensor=false&language=en-EN
		  */
            JSONObject json = new JSONObject(str);
            if (json.getString("status").equalsIgnoreCase("ok")) {
                if (json.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getString("status").equalsIgnoreCase("ZERO_RESULTS")) {
                    return false;
                }
                JSONObject j1 = json.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
                JSONObject distance = j1.getJSONObject("distance");
                double distanceInMeters = distance.getDouble("value");
			/*
			 * Convert to KM before returning.
			 */
                loc.distanceFromOrigin = round((distanceInMeters / 1000), 2);
                return true;
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    /**
     * Had to comment out since it fails when the API has no information for the location.
     *
     * @param s
     * @return
     * @throws IOException
     */
    private static boolean setWeather(Location loc) {
		/*
		 * Construct URL from paramters, open the stream, read it, and create a JSON object from it.
		 */
        if (loc.destination == null) return false;
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(loc.destination);

        try {
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";
            }
		 /* Navigate JSON to find temperature and description.
		  * 
		  * Sample URL
		  * http://api.openweathermap.org/data/2.5/weather?q=kelowna,bc
		  */
            JSONObject json = new JSONObject(str);
            double tempInKelvin = json.getJSONObject("main").getDouble("temp");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            loc.tempInCelcius = round((tempInKelvin - 273.15), 2);
            loc.weatherDescription = description;
            return true;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
	 
		/*
		 * Return 0 in error case;
		 */
        return false;
    }

    private static double[] geocode(String s) throws IOException {
        String geocodeUrl = "http://maps.googleapis.com/maps/api/geocode/json?address=";
        geocodeUrl += URLEncoder.encode(s) + "&sensor=true";
        Scanner scan = new Scanner(new URL(geocodeUrl).openStream());
        String str = new String();
        while (scan.hasNext()) {
            str += scan.nextLine() + "\n";

        }
        JSONObject jsonObject = new JSONObject(str);
        if (jsonObject.getString("status").equals("OK")) {
            //geocode address.
            JSONArray routes = jsonObject.getJSONArray("results");
            JSONObject geometry = routes.getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            double[] loc = {lat, lng};
            return loc;
        }
        return new double[]{0, 0};
    }

    public static boolean getPlaces(Location loc, String keyword) {
        ArrayList toReturn = new ArrayList();

        try {
            double[] geo = geocode(loc.destination);
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                    "location=" + geo[0] + "," + geo[1] + "&types=" + keyword +
                    "&radius=100&sensor=false&key=AIzaSyD-GnR8Af9fm57GuOz9kdLTzjPMjfPeXiQ";
            Scanner scan = new Scanner(new URL(url).openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine() + "\n";

            }
            JSONObject json = new JSONObject(str);
            if (json.getString("status").equalsIgnoreCase("ok")) {
                JSONArray j = json.getJSONArray("results");
                int index = 0;
                JSONObject tmp;
                while (!j.isNull(index) && index < 4) {
                    tmp = j.getJSONObject(index);
                    toReturn.add(tmp.getString("name"));// + ", " + tmp.getString("vicinity")); Trimmed this off to just get place names.
                    index++;
                }
                loc.places.put(keyword, toReturn);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}