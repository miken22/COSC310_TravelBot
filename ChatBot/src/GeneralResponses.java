import java.util.Arrays;
import java.util.List;

/**
 * This class will hold all general purpose responses for the
 * Chat Agent such as greetings and handling issues with the users
 * input.
 * 
 * @author Mike Nowicki
 *
 */

public class GeneralResponses {
	private static java.util.Random rand = new java.util.Random();

	public static List<String> AlreadyLeft = Arrays.asList("You already said bye, I must help the next customer. Please come back again later!");
	public static List<String> StartUp = Arrays.asList("Hi! Welcome to our travel center. We help arrange trips to Mexico and ski resorts in BC and Alberta. How can I help you today?");
	public static List<String> greetings = Arrays.asList("Good <TimeOfDay>, I'm Travel Bot. How can I help", "Hi, how can I help", "Hello. Can I help you plan a trip", "Hey there, I'm TravelBot! How are you", "Greetings, welcome to my Travel Agency", "Welcome to our travel center. How can I help", "Hi there, how can I help you this <TimeOfDay>");
    public static List<String> farewells = Arrays.asList("Good <TimeOfDay>", "Bye", "Goodbye", "Farewell", "See ya", "See you later", "Take care", "Ciao", "Thanks for stopping by");
    public static List<String> niceDest = Arrays.asList("<Dest> is very nice!", "Everybody loves <Dest>!", "<Dest> is a wonderful place!");
    public static List<String> noRestaurants = Arrays.asList("There aren't any places that I'd recommend.", "There may be some local places, but nothing outstanding.", "It's all about street vendors and local food there.");
	public static List<String> searching = Arrays.asList("Well let me find out... ", "Let me look that up... ", "Determining what's best for you.", "Searching...");
	public static List<String> simpleYes = Arrays.asList("Yes.", "Of course, let me look at some options.", "Sure! We can do that.", "I think that can be done.");
	public static List<String> simpleNo = Arrays.asList("No.", "That's not possible", "Sorry, I can't help with that.");
    public static List<String> dontKnow = Arrays.asList("I do not know the answer to '<usermessage>'. Maybe we can talk about your trip?", "Sorry, I don't know how to answer '<usermessage>'. Maybe we can talk about your trip some more?", "I'm not sure how to respond to that. Maybe we should get back on point.", "Sorry '<usermessage>' isn't something I can help you with. Did you want to ask me something about your vacation?");
    public static List<String> genAccom = Arrays.asList("We offer a wide variety of accomodations. Do you have a budget?", "We offer lots of different hotels. Do you have a price in mind?", "Were you interested in a family resort, or one of our luxury offerings?");
    public static List<String> youreWelcome = Arrays.asList("No problem.", "You're welcome.", "Of course, anytime.", "My pleasure.");
    public static List<String> badDestination = Arrays.asList("Sorry, we don't go to <Dest>.", "Sorry, I do not think that we arrange trips to <Dest>.", "Sorry, <Dest> is not a place we travel go to.", "Our guide stopped going to <Dest> a while ago, sorry.");
    public static List<String> notEnoughInfo = Arrays.asList("Sorry, you need to tell me more about what you mean by '<userinput>'.", "Sorry, can you please tell me what you mean by '<userinput>'.", "Sorry, I don't understand what '<userinput>' means.");
	public static List<String> NoDestinationSet = Arrays.asList("Sorry, you have to decide where you want to go before we can talk about <userinput>.", "I need to know a place before I can help you with <userinput>.");
	public static List<String> flightResponses = Arrays.asList("Yes, we have two direct flights to <Dest> and numerous other flights that leave daily. ", "I can certainly help you with that. All our flights are direct to <Dest>.");
	public static List<String> cantFly = Arrays.asList("It's hard to fly to <Dest> since there's no airport.", "Sorry, we don't book private planes that can fly to <Dest>.", "We don't arrange flights to <Dest>.");

	public static List<String> searchAnswers = Arrays.asList("I've found a result for '<search>'. There is a place called '<result>' that might be perfect for you.", "Yes, I've found a place called '<result>' that might be just what you're looking for.", "You could try '<result>', it looks perfect for you.");
	public static List<String> searchMiss = Arrays.asList("Sorry, I could not find anything matching '<query>', would you like to look for something else?", "Sorry, there are no matches for '<query>'. You can enter another search if you would like.");
	
	public static String getRandomResponse(List<String> responses, String keyword, String value) {
        return responses.get(rand.nextInt(responses.size())).replace(keyword, value);
    }

    public static String getRandomResponse(List<String> responses) {
        return responses.get(rand.nextInt(responses.size()));
    }  
}