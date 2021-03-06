import java.util.*;

/**
 * This class holds the canned responses for visits to Tropical Destinations.
 * 
 * @author Mike Nowicki
 *
 */

public final class TropicResponses {
    private static java.util.Random rand = new java.util.Random();

    public static List<String> locations = Arrays.asList("Mexico");
    public static List<String> cities = Arrays.asList("Tijuana", "Canc�n", "Mexicali", "Chihuahua");
    public static List<String> activities = Arrays.asList("relax on the beach", "swim in crystal clear waters", "drink some awesome margaritas", "enjoy the beach and tan", "visit some ancient Mayan ruins", "see some of the historical landmarks", "play beach volleyball", "explore the local wildlife areas", "swim with dolphins", "buy some street art");
    public static List<String> lang = Arrays.asList("Well the national language is Spanish.", "Spanish is the native language in <Dest>, although some people do speak English", "People in <Dest> speak Spanish.");
    public static List<String> transport = Arrays.asList("Well people in <Dest> usually use buses. There are also government owned taxis.", "If you want to get between cities coach bus is the way to go.", "We could help set you up with a rental car if you'd like.", "You should be able to walk to most places within the city. Otherwise coach buses are a good way to get around the country.");
    public static List<String> niceAccom = Arrays.asList("The Flamingo, in <Dest>, is one of the top hotels in the country. Excellent all inclusive accommodations just minutes from downtown <Dest>.", "The Fiesta Americana Condesa is a fantastic resort right on the white sandy beaches of <Dest>. The resort is all inclusive!");
    public static List<String> medAccom = Arrays.asList("We have a nice hotel in <Dest> with a pool and swim up bar. Only $105 per night.", "I suggest BeachScape Kin Ha Villas. It just takes seconds to walk from your room to the beach!", "Hotel Tulipan is one of our highest rated hotels. Right in the heart of <Dest> and only $95 a night.", "In <Dest> we have a fantastic beachfront hotel. Just steps outside your room, and only $100 a night.", "");
    public static List<String> cheapAccom = Arrays.asList("Our top discount hotels in <Dest> are Hotel Plaza and Hotel Tropicoco. Only $55 a night.", "We've partnered with some great places. You might like Hotel Tropicoco, only $50, a night!", "One of our best reviewed hotels is Club Amigo Caracol in <Dest>. It should fit your budget.");
    public static List<String> boatResponses = Arrays.asList("Sure, we are partnered with many different cruise lines.", "Of course! I've heard that it's one of the best places to for a cruise.", "Yes! The Maya Riviera is one of the most popular cruise destinations.");
    
    public static String getRandomResponse(List<String> responses, String keyword, String value) {
        return responses.get(rand.nextInt(responses.size())).replace(keyword, value);
    }

    public static String getRandomResponse(List<String> responses) {
        return responses.get(rand.nextInt(responses.size()));
    }
}