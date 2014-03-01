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
            return substituteParameters(GeneralResponses.getRandomResponse(GeneralResponses.greetings));
        } else {
            return substituteParameters(GeneralResponses.getRandomResponse(GeneralResponses.greetings)) + " " + username + ".";
        }
    }

    public String getGreetingRepeat() {
        return "Yes, we've said our greetings. Is there something I can help with?";
    }

    public String getFarewell(String username) {
        if (StringUtils.isNullOrEmpty(username)) {
            return substituteParameters(GeneralResponses.getRandomResponse(GeneralResponses.farewells));
        } else {
            return substituteParameters(GeneralResponses.getRandomResponse(GeneralResponses.farewells)) + " " + username + ".";
        }
    }
    
    public String getBadLocations(String location){
    	return GeneralResponses.getRandomResponse(GeneralResponses.badDestination, "<Dest>", location);
    }
    
    public String noDestinationInfo(String input){
    	return GeneralResponses.getRandomResponse(GeneralResponses.NoDestinationSet, "<userinput>", input);
    }
    public String getImBack() {
        return "Okay, I'm back. What can I help with?";
    }
    
    public String getMissingInfo(String userinput){
    	return GeneralResponses.getRandomResponse(GeneralResponses.notEnoughInfo,"<userinput>",userinput);
    }

    public String getYoureWelcome() {
        return GeneralResponses.getRandomResponse(GeneralResponses.youreWelcome);
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
        return l.getPlaces(keyword).get(0);
    }

    public String getAroundTropical(String location) {
        return TropicResponses.getRandomResponse(TropicResponses.transport, "<Dest>", location);
    }

    public String getAroundWinter(String location) {
    	// TODO: Add responses once new class is built
    	// return WinterResponses.getRandomResponse(WinterResponses.transport, "<Dest>", location);
    	return null;
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
        String response = GeneralResponses.getRandomResponse(GeneralResponses.searching) + "\n";

        try{
        	if(!l.getPlaces("food").isEmpty()){
                response += l.getPlaces("food").get(0) + ".";
        	}
        } catch (NullPointerException e){
        	 response += GeneralResponses.getRandomResponse(GeneralResponses.noRestaurants);
        }
        
        return response;
    }

    public String getDestinationInfo(String location, String city) {
        String destination = "";

        if (StringUtils.isNullOrEmpty(location) && StringUtils.isNullOrEmpty(city)) {
            return "Sorry you need to say where you want to go!";
        } else if (!StringUtils.isNullOrEmpty(location) && StringUtils.isNullOrEmpty(city)) {
            destination = location;
            return GeneralResponses.getRandomResponse(GeneralResponses.niceDest, "<Dest>", location) + " Where would you like to go in " + location + "?";
        } else if (StringUtils.isNullOrEmpty(location) && !StringUtils.isNullOrEmpty(city)) {
            destination = city;
        } else {
            destination = city + ", " + location;
        }
        l = new Location(destination);
        locationSet.add(l);
        return GeneralResponses.getRandomResponse(GeneralResponses.niceDest, "<Dest>", destination);
    }

    public String getDestinationInfo(String city) {
        
        if (StringUtils.isNullOrEmpty(city)) {
            return "Sorry you need to say where you want to go!";
        }
        l = new Location(city);
        locationSet.add(l);
//        String cleanedCity = city.substring(0, city.length()-3);
        return GeneralResponses.getRandomResponse(GeneralResponses.niceDest, "<Dest>", city);
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
            if (locationSet.size() == 0) {
                return GeneralResponses.getRandomResponse(GeneralResponses.NoDestinationSet, "<userinput>", "weather");
            }
        }
        
        String desc = locationSet.get(locationSet.size()-1).weatherDescription.toLowerCase();
        String join = "";
        for(String s: desc.split(" ")){
        	if(s.equals("is")){
        		join = " and the ";
        		break;
        	} else {
        		join = " with ";
        	}
        }
        
        return "It is currently " + locationSet.get(locationSet.size()-1).tempInCelcius + " degrees C in " + StringUtils.toTitleCase(locationSet.get(locationSet.size() - 1).destination) 
        		+ join + desc + ".";
    }

    public String getTropicalActivities() {
    	String s1 = TropicResponses.getRandomResponse(TropicResponses.activities);
    	String s2 = s1;
    	while (s2.equals(s1)) {
    		s2 = TropicResponses.getRandomResponse(TropicResponses.activities);
    	}
    	String response = "While you are there you could " + s1 + ", or you could " + s2;
        return response;
    }
    
    // TODO: Fix class to do winter things
    public String getWinterActivities() {
//        String s1 = TropicResponses.getRandomResponse(TropicResponses.activities);
//        String s2 = s1;
//        while (s2.equals(s1)) {
//            s2 = TropicResponses.getRandomResponse(TropicResponses.activities);
//        }

        String response = "While you are there you could try something new!";
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