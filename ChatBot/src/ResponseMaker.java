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
            return substituteParameters(GeneralResponses.getRandomResponse(GeneralResponses.greetings)) + ".";
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
    	return WinterResponses.getRandomResponse(WinterResponses.transport, "<Dest>", location);
    }
    
    public String getTravelMethod(String travelMethod, String location, boolean tropicDest) {
    	
    	if (travelMethod == "car" || travelMethod == "drive") {
		    
    		String response = "You can if you want." + "\r\n";
            response += getTravelCost(travelMethod);
            return response;
            
        } else if (travelMethod == "boat" || travelMethod == "cruise") {
        	
            String response = TropicResponses.getRandomResponse(GeneralResponses.searching) + "\r\n";
            
            if(!tropicDest){
            	response += "It's a little hard to go on a cruise when you're in Canada's Interior. I can redirect you to our Alaskan Cruise Line Partners if you'd like.";
            } else {
            	response += TropicResponses.getRandomResponse(TropicResponses.boatResponses, "<Dest>", location);
            }
            return response;
            
        } else if (travelMethod == "fly" || travelMethod == "flight" || travelMethod == "plane") {
            
        	String response = TropicResponses.getRandomResponse(GeneralResponses.searching) + "\r\n";
        
        	if(!tropicDest && location != "Calgary,AB" ){
        		location = location.substring(0,location.length()-3);
        		response += TropicResponses.getRandomResponse(GeneralResponses.cantFly, "<Dest>", location);
           	} else {
           		response += TropicResponses.getRandomResponse(GeneralResponses.flightResponses, "<Dest>", location) + "\r\n";
               	response += getTravelCost(travelMethod);
            }
           	return response;
        
        }
	
        return "Sorry, we don't book trips by " + travelMethod;
    }
    
    public String getNoDestinationSet(String input){
    	return TropicResponses.getRandomResponse(GeneralResponses.NoDestinationSet, "<userinput>",input);
    }

    public String getGenAccommodation() {
        return GeneralResponses.getRandomResponse(GeneralResponses.genAccom);
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

    public String getLocalFood(String place) {
        String response = "";
        String city = l.destination;        if(!place.isEmpty()){
        	response = "A very popular place in " + city + " is " + place + ".";
        } else {
        	response += GeneralResponses.getRandomResponse(GeneralResponses.noRestaurants);
        }
        
        return response;
    }

    public String getDestinationInfo(String location, String city, boolean tropicDest) {
        String destination = "";
        
        if (StringUtils.isNullOrEmpty(location) && StringUtils.isNullOrEmpty(city)) {
            return "Sorry you need to say where you want to go!";
        } else if (!StringUtils.isNullOrEmpty(location) && StringUtils.isNullOrEmpty(city)) {
            destination = location;
            return GeneralResponses.getRandomResponse(GeneralResponses.niceDest, "<Dest>", location) + " Where would you like to go in " + location + "?";
        } else if (StringUtils.isNullOrEmpty(location) && !StringUtils.isNullOrEmpty(city)) {
        	destination = city;
        } else {
        	destination = city;
        }
        l = new Location(destination);
        locationSet.add(l);
        if(!tropicDest){
        	city = destination.substring(0,destination.length()-3);
        	return GeneralResponses.getRandomResponse(GeneralResponses.niceDest, "<Dest>", city);
        }
        return GeneralResponses.getRandomResponse(GeneralResponses.niceDest, "<Dest>", destination);
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
        response = "The distance between " + city2 + " and " + city + " is ";
        response += (l.distanceFromOrigin == 0) ? "...I don't know." : l.distanceFromOrigin + "km";
        return response;
    }

    public String getDontKnow(String userMessage) {
            return GeneralResponses.getRandomResponse(GeneralResponses.dontKnow,"<usermessage>",userMessage);
    }

    public String getWeather(String destination,boolean tropicDest) {

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
        String city = locationSet.get(locationSet.size()-1).destination;
        if(!tropicDest){
        	String cleanedCity = city.substring(0, city.length()-3);
        	city = cleanedCity;
        }
        return "It is currently " + locationSet.get(locationSet.size()-1).tempInCelcius + " degrees C in " + StringUtils.toTitleCase(city)+ join + desc + ".";
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
    
    public String getSkiResorts(String city){
    	String response = "";
    	
    	switch(city.toLowerCase()){
    	case "revelstoke,bc":
    		response = "One of the best resorts in the area is Revelstoke Mountain. It will provide you with an excellent challenge.";
    		break;
    	case "kamloops,bc":
    		response = "Kamloops is home to a great family resort, Sunshine Village. Not too challenging with activities for the whole family!";
        	break;
    	case "kelowna,bc":
    		response = "Voted best tree skiing in Canada, Big White Ski Resort boast some of the best powder in the interior in some technical terrain.";
    		break;
    	case "golden,bc":
    		response = "Golden is the home of back-country skiing. Warm up at Kicking Horse with its steep valleys before trying a trip out to the back-country by helicopter or snow-cat!";
    		break;
    	case "banff,ab":
    		response = "Banff is one of the most unique areas, providing you with access to numerous resorts in a relatively short drive. Lake Louise is one of the most historic and beautiful resorts in Canada, while Sunshine Village has the terrain to challenge everyone!";
    		break;
    	case "calgary,ab":
    		response = "While you're in Calgary you have to stop by Olympic Park. The resort was host to the winter Olympics and you can still tour many of the buildings from then.";
    		break;
    	}
    	return response;
    }
    
    public String getWinterActivities() {
        String s1 = WinterResponses.getRandomResponse(WinterResponses.activities);
        String s2 = s1;
        while (s2.equals(s1)) {
            s2 = TropicResponses.getRandomResponse(WinterResponses.activities);
        }

        return "While you are there you could " + s1 + ", or you could " + s2;
     
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

	public String getWinterAccom(int amount, String city) {
		String response = "Searching for the best accommodations that match you budget. " + "\n";
		List<String> places = new ArrayList<>();
		try{
			places = l.getPlaces("lodging");
			int r = 0;
			if(places.size()>1){
				r = new java.util.Random().nextInt(places.size());
			} 
			String hotel = places.get(r);
			
			if(hotel.substring(0, 4)=="The "){
				hotel = hotel.substring(4, hotel.length());
			}
			
			response += WinterResponses.getRandomResponse(WinterResponses.searchedAccom, "<hotel>", hotel);
			return response + "$"+(amount-5)+" a night.";
		} catch (NullPointerException e){}
		
		
        if (amount >= 130) {
            response += WinterResponses.getRandomResponse(WinterResponses.niceAccom, "<Dest>", city);
        } else if (amount > 90) {
            response += WinterResponses.getRandomResponse(WinterResponses.medAccom, "<Dest>", city);
        } else {
            response += WinterResponses.getRandomResponse(WinterResponses.cheapAccom, "<Dest>", city);
        }
        return response;
	}

	public String getSearchResults(String search) {
		if(StringUtils.isNullOrEmpty(search)){
			return "Sorry you haven't told me what you'd like to search for.";
		}
		String response = "";
		ArrayList<String> places = new ArrayList<>();
		try{
			places = l.getPlaces(search);
			int r = new java.util.Random().nextInt(places.size());
			response = GeneralResponses.getRandomResponse(GeneralResponses.searchAnswers, "<result>", places.get(r));
			response.replaceAll("<search>", search);
			System.out.println(places.toString() + ", " + r);
		} catch (NullPointerException e){
			response = GeneralResponses.getRandomResponse(GeneralResponses.searchMiss, "<query>", search);
		}
		
		return response;
	}
}