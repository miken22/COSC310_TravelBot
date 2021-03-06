import java.util.*;
/**
 * This class holds the dictionary for the parser. These act as the 
 * known words that the parser looks to compare against.
 * 
 * @author Mike Nowicki
 *
 */

public class ParserDictionary {
    public static List<String> greet = Arrays.asList("good morning", "good afternoon", "good evening", "hi", "hello", "hey", "greetings", "howdy");
    public static List<String> leave = Arrays.asList("good night", "bye", "goodbye", "farewell", "see ya", "see you later", "take care", "ciao");
    public static List<String> comeBack = Arrays.asList("please help", "come back", "please come back", "i need help", "that's rude");
    public static List<String> tropicdest = Arrays.asList("Mexico");
    public static List<String> colddest = Arrays.asList("Canada");
    public static List<String> tropiccities = Arrays.asList( "Tijuana", "Juarez", "Cancun", "Mexicali", "Chihuahua", "Chichen Itza");
    public static List<String> bccities = Arrays.asList("Revelstoke","Kamloops","Golden","Kelowna","Penticton");
    public static List<String> albertacities = Arrays.asList("Canmore","Calgary","Banff");
    public static List<String> askForCities = Arrays.asList("cities", "places", "towns", "destinations");
    public static List<String> thanks = Arrays.asList("thanks", "thank you", "appreciated");
    public static List<String> travelMethods = Arrays.asList("fly", "flight", "plane", "boat", "cruise", "bus", "drive", "car");
    public static List<String> distance = Arrays.asList("far", "long", "distance");
    public static List<String> food = Arrays.asList("eat", "food", "restaurant", "dine", "lunch", "dinner", "bar");
    public static List<String> budget = Arrays.asList("budget", "afford", "cost", "spend", "$","a night");
    public static List<String> activities = Arrays.asList("things to do", "activities", "do");
    public static List<String> getAround = Arrays.asList("get around", "transportation","travel around");
    public static List<String> bookInfo = Arrays.asList("passport", "reservation", "book", "booking");
    public static List<String> lang = Arrays.asList("english", "spanish", "french");
    public static List<String> skiactivities = Arrays.asList("ski", "skiing", "snowboard","snowboarding");
    public static List<String> weather = Arrays.asList("weather", "temperature", "rain", "sun", "warm", "cool", "time of year");
    public static List<String> seasons = Arrays.asList("spring", "summer", "fall", "winter");
    public static List<String> searchKeys = Arrays.asList("airport", "amusement_park", "aquarium", "bar", "caf�", "campground", "casino", "clothing_store", "convenience_store", "department_store", "grocery_or_supermarket", "hospital", "library", "liquor_store", "lodging", "movie_theater", "museum", "night_club", "park", "parking", "restaurant", "shopping_mall", "spa", "stadium", "travel_agency", "university", "zoo");
	public static List<String> Directions = Arrays.asList("directions","get there");
	public static List<String> wiki = Arrays.asList("tell","about","information");
	public static List<String> translate = Arrays.asList("say","translate");
	
	
  // Stores the phrase lists as a list of phrases, each phrase being a list of words in the phrase
    private static HashMap<List<String>, List<List<String>>> cachedLists = new HashMap<>();


    public static List<List<String>> getTokenizedPhraseList(List<String> phraseList) {
        List<List<String>> tokenizedPhraseList;
        // Check if result is cached
        if (!cachedLists.containsKey(phraseList)) { // Not found in cache
            tokenizedPhraseList = tokenizePhrases(phraseList);  // Break down the phrases into a list of tokenized phrases
            cachedLists.put(phraseList, tokenizedPhraseList); // Save the result
        } else {
            tokenizedPhraseList = cachedLists.get(phraseList); // Result found in cache
        }
        return tokenizedPhraseList;
    }

    // Creates a list of tokenized phrases
    // Each phrase is itself a list of tokens (words)
    private static List<List<String>> tokenizePhrases(List<String> phraseList) {
        List<List<String>> tokenizedPhraseList = new ArrayList<List<String>>();

        for (String phrase : phraseList) { // For every phrase in the list
            // Break down the phrase into tokens (words)
            // then add the list of tokens to our list of tokenized phrases
            tokenizedPhraseList.add(Regex.tokenizeOnWhitespace(phrase));
        }
        return tokenizedPhraseList;
    }
}