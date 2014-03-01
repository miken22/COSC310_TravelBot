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
    public static List<String> bccities = Arrays.asList("Revelstoke","Kamloops","Golden","Kelowna");
    public static List<String> albertacities = Arrays.asList("Banff","Lake Louise","Calgary");
    public static List<String> activities = Arrays.asList("go skiing", "try cross-country skiing", "rent a snowmobile", "go heliskiing","try paraskiing","play hockey on an outdoor rink");
    public static List<String> transport = Arrays.asList("Well the best way to get around <Dest> is to drive. I can arrange a rental car for you if you would like..", "If you want to get between cities Greyhound is the way to go.", "We could help set you up with a rental car if you'd like. Be sure to drive carefully on the mountain roads!", "You should be able to walk to most places within the city. Otherwise buses are a good way to get around <Dest>.");
    public static List<String> niceAccom = Arrays.asList();
    public static List<String> medAccom = Arrays.asList();
    public static List<String> cheapAccom = Arrays.asList();
    public static List<String> genAccom = Arrays.asList();
    
    public static String getRandomResponse(List<String> responses, String keyword, String value) {
        return responses.get(rand.nextInt(responses.size())).replace(keyword, value);
    }

    public static String getRandomResponse(List<String> responses) {
        return responses.get(rand.nextInt(responses.size()));
    }
}
