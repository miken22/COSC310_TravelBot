import java.util.*;

/**
 * This class builds the response strings using the 
 * stored responses in the Responses class. This class
 * is called by the TravelAgent based on the parsed input type
 * cases.
 * 
 * 
 * @author Mike Nowicki
 *
 */

public final class ResponseMaker {
    List<Location> locationSet = new ArrayList<>();
    Location l;

    public String getGreeting(String username) {
    	if (StringUtils.isNullOrEmpty(username)) {
            return substituteParameters(TropicResponses.getRandomResponse(GeneralResponses.greetings));
        } else {
            return substituteParameters(TropicResponses.getRandomResponse(GeneralResponses.greetings)) + " " + username + ".";
        }
    }

    public String getGreetingRepeat() {
        return "Yes, we've said our greetings. Is there something I can help with?";
    }

    public String getFarewell(String username) {
        if (StringUtils.isNullOrEmpty(username)) {
            return substituteParameters(TropicResponses.getRandomResponse(GeneralResponses.farewells));
        } else {
            return substituteParameters(TropicResponses.getRandomResponse(GeneralResponses.farewells)) + " " + username + ".";
        }
    }
    
    public String getBadLocations(String location){
    	return TropicResponses.getRandomResponse(GeneralResponses.badDestination, "<Dest>", location);
    }
    
    public String noDestinationInfo(String input){
    	return TropicResponses.getRandomResponse(GeneralResponses.NoDestinationSet, "<userinput>", input);
    }

    public String getImBack() {
        return "Okay, I'm back. What can I help with?";
    }
    
    public String getMissingInfo(String userinput){
    	return TropicResponses.getRandomResponse(GeneralResponses.notEnoughInfo,"<userinput>",userinput);
    }

    public String getYoureWelcome() {
        return TropicResponses.getRandomResponse(GeneralResponses.youreWelcome);
    }

    public String getCities() {
        String cities = "Well the biggest are ";

        for (String s : TropicResponses.cities) {
            cities += s + ", ";
        }
        cities += ".";
        return cities;
    }

    public String getKeywordPlaces(String keyword) {
        return l.getPlaces(keyword);
    }

    public String getAround(String location) {
        return TropicResponses.getRandomResponse(TropicResponses.transport, "<Dest>", location);
    }

    public String getTravelMethod(String travelMethod, String location) {
    	
    	if (travelMethod == "car" || travelMethod == "drive") {
		    String response = "You can if you want to." + "\r\n";
            response += getTravelCost(travelMethod) + ".";
            return response;
        } else if (travelMethod == "boat" || travelMethod == "cruise") {
            String response = TropicResponses.getRandomResponse(GeneralResponses.searching) + "\r\n";
            response += TropicResponses.getRandomResponse(TropicResponses.boatResponses, "<Dest>", location);
            return response;
        } else if (travelMethod == "fly" || travelMethod == "flight" || travelMethod == "plane") {
            String response = TropicResponses.getRandomResponse(GeneralResponses.searching) + "\r\n";
            response += TropicResponses.getRandomResponse(GeneralResponses.flightResponses, "<Dest>", location) + "\r\n";
            response += getTravelCost(travelMethod) + ".";
            return response;
        }
	
        return "Sorry, we don't book trips by " + travelMethod;
    }
    
    public String getNoDestinationSet(String input){
    	return TropicResponses.getRandomResponse(GeneralResponses.NoDestinationSet, "<userinput>",input);
    }

    public String getGenAccommodation() {
        return TropicResponses.getRandomResponse(TropicResponses.genAccom);
    }

    public String getBudgetAccom(int amount, String location) {
        String response = "Searching for the best accommodations that match you budget. " + "\n";

        if (amount >= 130) {
            response += TropicResponses.getRandomResponse(TropicResponses.niceAccom, "<Dest>", location);
        } else if (amount > 90) {
            response += TropicResponses.getRandomResponse(TropicResponses.medAccom, "<Dest>", location);
        } else {
            response += TropicResponses.getRandomResponse(TropicResponses.cheapAccom, "<Dest>", location);
        }

        return response;
    }

    public String getLocalFood() {
        String response = TropicResponses.getRandomResponse(GeneralResponses.searching) + "\n";

        if (l.getPlaces("food").isEmpty()) {
            response += TropicResponses.getRandomResponse(TropicResponses.noRestaurants);
        } else {
            response += l.getPlaces("food") + ".";
        }
        return response;
    }

    public String getDestinationInfo(String location, String city) {
        String destination = "";

        if (StringUtils.isNullOrEmpty(location) && StringUtils.isNullOrEmpty(city)) {
            return "Sorry you need to say where you want to go!";
        } else if (!StringUtils.isNullOrEmpty(location) && StringUtils.isNullOrEmpty(city)) {
            destination = location;
            return TropicResponses.getRandomResponse(TropicResponses.niceDest, "<Dest>", location) + " Where would you like to go in " + location + "?";
        } else if (StringUtils.isNullOrEmpty(location) && !StringUtils.isNullOrEmpty(city)) {
            destination = city;
        } else {
            destination = city + ", " + location;
        }
        l = new Location(destination);
        locationSet.add(l);
        return TropicResponses.getRandomResponse(TropicResponses.niceDest, "<Dest>", destination);
    }

    public String getTravelCost(String methodOfTravel) {
        if (methodOfTravel == "car" || methodOfTravel == "drive") {
            return l.estimateTravelCost();
        } else {
            return l.estimateFlightCost();
        }
    }

    public String getLanguages(String dest) {
        return TropicResponses.getRandomResponse(TropicResponses.lang, "<Dest>", dest);
    }

    public String getDistances(String city, String city2) {
        String response;
        l = new Location(city, city2);
        response = "The distance between " + city + " and " + city2 + " is ";
        response += (l.distanceFromOrigin == 0) ? "...I don't know." : l.distanceFromOrigin + "km";
        return response;
    }

    public String getDontKnow() {
            return GeneralResponses.getRandomResponse(GeneralResponses.dontKnow);
    }

    public String getWeather(String destination) {

        if (StringUtils.isNullOrEmpty(destination)) {
            int i = 0;
            String str = "";
            if (locationSet.size() == 0) {
                return TropicResponses.getRandomResponse(GeneralResponses.NoDestinationSet, "<userinput>", "weather");
            } else {
                while (locationSet.get(i) != null) {
                    str += locationSet.get(i).destination + ": " + locationSet.get(i).tempInCelcius + " degrees C with " + locationSet.get(i++).weatherDescription;
                }
                return str;
            }
        }
        return "It is currently " + locationSet.get(locationSet.size()-1).tempInCelcius + " degrees C in " + locationSet.get(locationSet.size() - 1).destination;
    }

    public String getActivities() {
        String s1 = TropicResponses.getRandomResponse(TropicResponses.activities);
        String s2 = s1;
        while (s2.equals(s1)) {
            s2 = TropicResponses.getRandomResponse(TropicResponses.activities);
        }

        String response = "While you are there you could " + s1 + ", or you could " + s2;
        return response;
    }

    private String substituteParameters(String paramText) {
        paramText = paramText.replace("<TimeOfDay>", getTimeOfDay());
        checkAllParamsSubbed(paramText);
        return paramText;
    }

    private void checkAllParamsSubbed(String paramText) {
        int start = paramText.indexOf('<');
        int end = paramText.indexOf('>', start);
        if (start > 0 && end > start) {
            throw new RuntimeException("Failed to expand response parameter '" + paramText.substring(start, end) + "'");
        }
    }

    public String getTimeOfDay() {
        Calendar now = Calendar.getInstance();
        if ((now.get(Calendar.HOUR_OF_DAY) <= 5) || (now.get(Calendar.HOUR_OF_DAY) > 22)) {
            return "night";     // 10pm - 5am
        } else if ((now.get(Calendar.HOUR_OF_DAY) >= 5) && (now.get(Calendar.HOUR_OF_DAY) < 12)) {
            return "morning";   // 5am  - 12pm
        } else if ((now.get(Calendar.HOUR_OF_DAY) >= 12) && (now.get(Calendar.HOUR_OF_DAY) < 17)) {
            return "afternoon"; // 12pm - 5pm
        } else { //if ((now.get(Calendar.HOUR_OF_DAY) >= 17) || (now.get(Calendar.HOUR_OF_DAY) < 22)) {
            return "evening";   // 5pm  - 10pm
        }
    }

    public String getSimpleNo() {
        return TropicResponses.getRandomResponse(GeneralResponses.simpleNo);
    }

    public String getSimpleYes() {
        return TropicResponses.getRandomResponse(GeneralResponses.simpleYes);
    }

	public String getAlreadyLeft() {
		return GeneralResponses.getRandomResponse(GeneralResponses.AlreadyLeft);
	}

	public String getStartup() {
		return GeneralResponses.getRandomResponse(GeneralResponses.StartUp);
	}
}