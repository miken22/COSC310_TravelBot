import java.util.Arrays;
import java.util.List;

/**
 * This class holds the canned responses for the second conversation
 * topic, booking a vacation to a winter destination in BC/Alberta.
 * 
 * @author Mike Nowicki
 *
 */
public class WinterResponses {
    
	private static java.util.Random rand = new java.util.Random();
    
    public static List<String> locations = Arrays.asList("Canada");
    public static List<String> bccities = Arrays.asList("Penticton","Kamloops","Golden","Kelowna");
    public static List<String> albertacities = Arrays.asList("Banff","Calgary","Canmore");
    public static List<String> activities = Arrays.asList("go skiing", "try cross-country skiing", "rent a snowmobile", "go heliskiing","try paraskiing","play hockey on an outdoor rink");
    public static List<String> transport = Arrays.asList("Well the best way to get around <Dest> is to drive. I can arrange a rental car for you if you would like..", "If you want to get between cities Greyhound is the way to go.", "We could help set you up with a rental car if you'd like. Be sure to drive carefully on the mountain roads!", "You should be able to walk to most places within the city. Otherwise buses are a good way to get around <Dest>.");
    public static List<String> niceAccom = Arrays.asList("Well you could stay at the Fairmont. One of the best global chains reserved only for the best and only minutes away from the resorts of <Dest>", "The <Dest> Resort is one of the nicest hotels in the area. For $100 a night treat yourself to live in luxury!");
    public static List<String> medAccom = Arrays.asList("Theres a Super 8 motel in downtown <Dest>. Only $85 a night with a pool and free breakfast.", "A common place is the Days Inn in <Dest>. Very affordable and close to all the major ski hills.", "In <Dest> a lot of people like the Best Western. Close to the downtown area letting you walk almost anywhere.");
    public static List<String> cheapAccom = Arrays.asList("There is a famous hostel just outside of <Dest>. It would only cost $35 a night if you don't mind sharing a room.", "There is a great budget hotel in downtown <Dest> with access to almost anywhere.");
	public static List<String> searchedAccom = Arrays.asList("One of the most popular locations is <hotel>. I can set you up with a room for ", "In that price range there is the <hotel> for only ", "I can set you up at the <hotel> for only ");
    
    public static String getRandomResponse(List<String> responses, String keyword, String value) {
        return responses.get(rand.nextInt(responses.size())).replace(keyword, value);
    }

    public static String getRandomResponse(List<String> responses) {
        return responses.get(rand.nextInt(responses.size()));
    }
}
