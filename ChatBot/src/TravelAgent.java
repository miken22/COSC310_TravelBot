import java.util.*;

/**This class handles the agents side of the conversation. The
 * main method is buildResponse, which takes the users input and
 * sends it to the parser to be tokenized. This returns a type which
 * is used in the getResponse method to compare against each case.
 * The matched case then either builds a simple response with some of the
 * helper methods or invokes the ResponseMaker class to build a more dynamic
 * response. This is then used to complete the response which is returned
 * to the ChatBox class to display on screen.
 * 
 * @author Mike Nowicki
 *
 */

public class TravelAgent {
    private ResponseMaker responseMaker = new ResponseMaker();
    private Location l;
    
    // Agent state
    private ArrayList<ParsedInput> previousInputs = new ArrayList<>();
    public UserInputs<String,String> savedInputs = new UserInputs<>();
    private boolean userHasGreeted = false;
    private boolean userHasSaidFarewell = false;
    private boolean tropicDestination = true;
    
    private String botName = "Travel Bot";
    
    // Gets user input and sends it to the parser.
    public String buildResponse(String input) {

 		String message = getResponse(CustomParser.parseUserMessage(input));
       	// Write out our response with header & footer
   		String response = "\r\n\r\n" + botName + ": " + message + "\r\n";
       	return response;
    }
    
    public String getStartUp(){
    	return responseMaker.getStartup();
    }
    
    public String getResponse(ParsedInput parsedInput) {
        String response = "";
        
        if(userHasSaidFarewell){
        	return responseMaker.getAlreadyLeft();
        }
        
//      Save all user entered variables
        savedInputs.putAll(parsedInput.inputs);    
        
//      Check the destination stored so the agent knows what topic
//      to respond to. Allows the user to change topics on the agent
//      while still keeping the conversation going.
        if(savedInputs.getValue("destination") != null){
        	for(String s : ParserDictionary.tropicdest){
        		if(savedInputs.getValue("destination") == s){
        			tropicDestination = true;
        			break;
        		} else {
        			tropicDestination = false;        			
        		}
        	}
        }

        // Check which kind of question or statement the user inputted
        switch (parsedInput.getType()) {

            case SetDestination:
            	if(tropicDestination){
            		response = responseMaker.getDestinationInfo(savedInputs.getValue("destination"), savedInputs.getValue("city"));
            	} else {
            		response = responseMaker.getDestinationInfo(savedInputs.getValue("destination"),savedInputs.getValue("city"));
            	}
                break;
            
            // If OpenNLP parser flags location not known to the agent this will handle to right response.
            case BadDestination:
            	response = responseMaker.getBadLocations(savedInputs.getValue("bad destination"));
            	break;
            	
            case NotEnoughInfo:
            	response = responseMaker.getMissingInfo(CustomParser.getUserMessage());
            	break;

            case Greeting:
                response = greeting();
                break;

            case Food:
            	String place = "";
            	try{
            		place = l.getPlaces("food").get(0);
            		savedInputs.addInput("food",place);
            	} catch (NullPointerException e){}
                response = responseMaker.getLocalFood(place);
                break;

            case Farewell:
                response = farewell(parsedInput);
                break;

            case PleaseComeBack:
                response = pleaseComeBack(parsedInput);
                break;

            case Activity:
            	if(tropicDestination){
            		response = responseMaker.getTropicalActivities();
            	} else {
            		response = responseMaker.getWinterActivities();
            	}
                break;
                
            case SkiResort:
            	response = responseMaker.getSkiResorts(savedInputs.getValue("city"));
            	break;
            	
            case GetKeyword:
                response = responseMaker.getKeywordPlaces(savedInputs.getValue("keyword"));

            case ListCities:
            	if(tropicDestination){
            		response = responseMaker.getCities();
            	} else {
            		response = "The biggest cities in the area are Vancouver and Calgary.";
            	}
                break;

            // How the user wants to get to destination
            case TravelMethod:
        		if(savedInputs.getValue("travel method").toLowerCase() == "cruise" && !tropicDestination){
        			response = "It's a little hard to go on a cruise when you're in Canada's Interior. I can redirect you to our Alaskan Cruise Line Partners if you'd like.";
        		} else {
            		response = responseMaker.getTravelMethod(savedInputs.getValue("travel method"), savedInputs.getValue("city"));
            	}
                break;

            case Distance:
                if (savedInputs.getValue("city2") != null) {
                    l = new Location(savedInputs.getValue("city"), savedInputs.getValue("city2"));
                    response = responseMaker.getDistances(l.origin, savedInputs.getValue("city2"));
                } else {
                    Location l2 = new Location(savedInputs.getValue("city"));
                    response = responseMaker.getDistances(l2.origin, savedInputs.getValue("city"));
                }
                break;

            case GetAround:
            	if(tropicDestination){
            		response = responseMaker.getAroundTropical(savedInputs.getValue("city"));
            	} else {
            		response = responseMaker.getAroundWinter(savedInputs.getValue("city"));
            	}
                break;

            case Accomodations:
                response = responseMaker.getGenAccommodation();
                break;

            case Budget:
                int amount = Integer.valueOf(savedInputs.getValue("budget"));
                if(tropicDestination){
                    response = responseMaker.getBudgetAccom(amount, savedInputs.getValue("city"));
                } else {

                    response = responseMaker.getWinterAccom(amount, savedInputs.getValue("city"));
                }
                break;

            case Language:
                response = responseMaker.getLanguages(savedInputs.getValue("destination"));
                break;

            case CheckWeather:
                response = responseMaker.getWeather(savedInputs.getValue("city"));
                break;

            case Thanks:
                response = responseMaker.getYoureWelcome();
                break;

            case SimpleYes:
                response = responseMaker.getSimpleYes();
                break;

            case SimpleNo:
                response = responseMaker.getSimpleNo();
                break;

            case DontUnderstand:
                response = responseMaker.getDontKnow();
                break;

            case None:
            default:
                response = "...";
                break;

            case Debug_ShowStats:
                response = getDebugStats();
                break;
        }

        // Save valid inputs to memory
        if (parsedInput.getType().isWellFormed()) {
            previousInputs.add(parsedInput);
        }
        return response;
    }

    private String getDebugStats() {
        String stats = "";
        for (Map.Entry<String, String> entry : savedInputs.getEntrySet()) {
            stats += entry.getKey() + " = " + entry.getValue() + "\r\n";
        }
        return stats;
    }

    private String greeting() {
        if (userHasGreeted) {
            return responseMaker.getGreetingRepeat();
        } else {
            return responseMaker.getGreeting(savedInputs.getValue("username"));
        }
    }

    private String farewell(ParsedInput parsedInput) {
        userHasSaidFarewell = true;
        return responseMaker.getFarewell(savedInputs.getValue("username"));
    }

    private String pleaseComeBack(ParsedInput parsedInput) {
        userHasSaidFarewell = false;
        return responseMaker.getImBack();
    }
}