import java.util.*;

public final class Responses {
    private static java.util.Random rand = new java.util.Random();

    public static List<String> greetings = Arrays.asList("Good <TimeOfDay>, I'm Travel Bot. How can I help?", "Hi, how can I help?", "Hello. Can I help you plan a trip?", "Hey there, I'm TravelBot!", "Greetings, welcome to my Travel Agency.", "Welcome to our travel center. How can I help?", "Hi there, how can I help you this <TimeOfDay>.");

    public static List<String> farewells = Arrays.asList("Good <TimeOfDay>", "Bye", "Goodbye", "Farewell", "See ya", "See you later", "Take care", "Ciao", "Thanks for stopping by");

    public static List<String> sorrybusys = Arrays.asList("I've moved on to helping someone else.", "Sorry, I'm busy now.", "I'm busy, try again later");

    public static List<String> locations = Arrays.asList("Mexico");

    public static List<String> noRestaurants = Arrays.asList("There aren't any places that I'd recommend.", "There may be some local places, but nothing outstanding.", "It's all about street vendors and local food there.");

    public static List<String> cities = Arrays.asList("Mexico City", "Tijuana", "Juarez", "Cancun", "Mexicali", "Chihuahua");

    public static List<String> niceDest = Arrays.asList("<Dest> is very nice!", "Everybody loves <Dest>!", "<Dest> is a wonderful place!");

    public static List<String> activities = Arrays.asList("relax on the beach", "swim in crystal clear waters", "drink some awesome margaritas", "enjoy the beach and tan", "visit some ancient Mayan ruins", "see some of the historical landmarks", "play beach volleyball", "explore the local wildlife areas", "swim with dolphins", "buy some street art");

    public static List<String> lang = Arrays.asList("Well the national language is Spanish.", "Spanish is the native language in <Dest>, although some people do speak English", "People in <Dest> speak Spanish.");

    public static List<String> transport = Arrays.asList("Well people in <Dest> usually use buses. There are also government owned taxis.", "If you want to get between cities coach bus is the way to go.", "We could help set you up with a rental car if you'd like.", "You should be able to walk to most places within the city. Otherwise coach buses are a good way to get around the country.");

    public static List<String> flightResponses = Arrays.asList("Yes, we have two direct flights to <Dest> and numerous other flights that leave daily. ", "I can certainly help you with that. All our flights are direct to <Dest>.");

    public static List<String> boatResponses = Arrays.asList("Sure, we are partnered with many different cruise lines.", "Of course! I've heard that it's one of the best places to for a cruise.", "Yes! The Maya Riviera is one of the most popular cruise destinations.");

    public static List<String> niceAccom = Arrays.asList("The Flamingo, in <Dest>, is one of the top hotels in the country. Excellent all inclusive accommodations just minutes from downtown <Dest>.", "The Fiesta Americana Condesa is a fantastic resort right on the white sandy beaches of <Dest>. The resort is all inclusive!");

    public static List<String> medAccom = Arrays.asList("We have a nice hotel in <Dest> with a pool and swim up bar. Only $105 per night.", "I suggest BeachScape Kin Ha Villas. It just takes seconds to walk from your room to the beach!", "Hotel Tulipan is one of our highest rated hotels. Right in the heart of <Dest> and only $95 a night.", "In <Dest> we have a fantastic beachfront hotel. Just steps outside your room, and only $100 a night.", "");

    public static List<String> cheapAccom = Arrays.asList("Our top discount hotels in <Dest> are Hotel Plaza and Hotel Tropicoco. Only $55 a night.", "We've partnered with some great places. You might like Hotel Tropicoco, only $50, a night!", "One of our best reviewed hotels is Club Amigo Caracol in <Dest>. It should fit your budget.");

    public static List<String> genAccom = Arrays.asList("We offer a wide variety of accomodations. Do you have a budget?", "We offer lots of different hotels. Do you have a price in mind?", "Were you interested in a family resort, or one of our luxury offerings?");

    public static List<String> searching = Arrays.asList("Well let me find out... ", "Let me look that up... ", "Determining what's best for you.", "Searching...");

    public static List<String> simpleYes = Arrays.asList("Yes.", "Of course, let me look at some options.", "Sure! We can do that.", "I think that can be done.");

    public static List<String> simpleNo = Arrays.asList("No.", "That's not possible", "Sorry, I can't help with that.");

    public static List<String> dontKnow = Arrays.asList("I do not know the answer to that.");

    public static List<String> youreWelcome = Arrays.asList("No problem.", "You're welcome.", "Of course, anytime.", "My pleasure.");

    public static List<String> badDestination = Arrays.asList("Sorry, we don't go to <Dest>.", "Sorry, I do not think that we arrange trips to <Dest>.", "Sorry, <Dest> is not a place we travel go to.", "Our guide stopped going to <Dest> a while ago, sorry.");
    
    public static List<String> notEnoughInfo = Arrays.asList("Sorry, you need to tell me more about what you mean by '<userinput>'");

	public static List<String> NoDestinationSet = Arrays.asList("Sorry, you have to decide where you want to go before we can talk about <userinput>.", "I need to know a place before I can help you with <userinput>.");
    
<<<<<<< HEAD
    public static List<String> NoDestinationSet = Arrays.asList("Sorry, you need to tell me where you want to go before we can talk about <userinput>");
    
=======
	public static List<String> PUSH;
	
	
>>>>>>> rebuild_parsing
    public static String getRandomResponse(List<String> responses, String keyword, String value) {
        return responses.get(rand.nextInt(responses.size())).replace(keyword, value);
    }

    public static String getRandomResponse(List<String> responses) {
        return responses.get(rand.nextInt(responses.size()));
    }
}