import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * yeah....
 * 
 * @author Mike-Laptop
 *
 */
public class WikiInfo {	
	public static String getInfo(String place){
		
		// Clean location formatting to match Wikipedia format
		switch(place){
			case "Cancun":
				place = "Cancún";
				break;
			case "Juarez":
				place = "Ciudad_Juárez";
				break;
			case "Chihuahua":
				place = "Chihuahua_(state)";
				break;
			case "Revelstoke":
				place = "Revelstoke,_British_Columbia";
				break;
		}
		
		String url = "http://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exlimit=10&exintro=&explaintext=&titles="+place;
		
		try {
			Scanner scan = new Scanner(new URL(url).openStream());
			String str = new String();
			while (scan.hasNext()) {
				str += scan.nextLine() + "\n";
			}
			scan.close();

	        // The following code is based on an example from StackOverflow:
	        // http://stackoverflow.com/questions/11903641/how-can-i-fetch-the-value-from-json-in-java-program
	        
	        JSONObject json = new JSONObject(str);
	        JSONObject query = json.getJSONObject("query");
		    JSONObject pages = query.getJSONObject("pages");
		    JSONObject nestedObject = null;
		    
		    @SuppressWarnings("static-access")
		    String[] keys = pages.getNames(pages);
		    
		    // Find the intro text for the page
		    for (int i = 0; i < keys.length; i++){
		    	try{
		    		nestedObject = pages.getJSONObject(keys[i]);
		    		if (nestedObject.has("pageid"))
		    			break;
		    	} catch (JSONException e) {}
		    }
		    String s = nestedObject.getString("extract");
	    
		    int index = s.indexOf("\n");
		    
		    // If the returned info is longer then one paragraph this trims off the extra
		    // details to make the information more concise.
		    if(index>0){
		    	s = s.substring(0, index);
		    }
	    
		    return s;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
		return null;
	}
}
