import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * This class handles all the major work of the program. It is called
 * by the TravelAgent class. This class then breaks the sentence apart
 * using the Tokenizer that was written (mainly Regex/TokenCollection classes).
 * The sentence is further parsed by the OpenNLP parsers to try to find
 * Named Entities in the input that can be saved for later to make the conversation
 * more realistic (such as remembering the users name etc). Each word also gets
 * tagged as it's part of speech (POS) in the sentence. Words that appear to be
 * formal nouns are capitalized and checked against the NER and Parser Dictionary
 * to add a further level of spell-checking.
 * 
 * @author Manny Haller, Mike Nowicki
 *
 */

public final class Parser {
    // Sentence tokenizer
 	private static Tokenizer t;
 	
 	// Three different NER's to be used on text
 	private static NameFinderME nf;
 	private static NameFinderME of;
 	private static NameFinderME lf;
 	private static POSTaggerME tagger;
 	
 	private static String[] tokens;
 	
 	public Parser() throws InvalidFormatException, IOException{
	
		trainTokenizer();
		trainTagger();
		// Initialize NER's
		trainNF();
		trainOF();
		trainLF();
		// Add currency finder;
	}
    
    public static ParsedInput parseUserMessage(String userMessage) {
        ParsedInput parsedInput = new ParsedInput();
        
        String userMsgLower = userMessage.toLowerCase().trim();
        
        // OpenNLP tokenization of sentence. To catch pos and named entities that have not been explicitly coded
        // in the parser dictionary.
        tokens = t.tokenize(userMessage);
        String[] taggedString = tagger.tag(tokens);
        
        // This checks all parts of speech. If something appears like it could be a person, place, organization
        // name then it forces the formatting to be first letter upper cased so the NE finders can try to match
        // the object.
        for(int i = 0; i < taggedString.length; i++){
//        	System.out.print(taggedString[i] + " ");
        	if(taggedString[i].equals("NNP") || taggedString[i].equals("NN") || taggedString[i].equals("POS")){
        		tokens[i] = StringUtils.toTitleCase(tokens[i]);
//        		System.out.println(tokens[i]); // to see what is happening
        	}
        }
        
        if (userMsgLower.compareTo("exit") == 0) System.exit(0);
        if (userMsgLower.compareTo("stats") == 0) {
            parsedInput.type = ParsedInputType.Debug_ShowStats;
        } else {
        	
            // Create the token collection
            parsedInput.tokenCollection.parse(userMessage);
            
            // Use name parser from openNLP to identify user name
            String name = findNames();
            if(!name.isEmpty()){
            	parsedInput.setField("username", name);
            }
            // Identify organizations in used sentence.
            String org = findOrgs();
            if(!org.isEmpty()){
            	parsedInput.setField("organization",org);
            }
            
            // Sometimes lower cased locations with potential other English meanings
            // will not get tagged. IE "cuba" is a modal verb, is very hard to find a non-hard coded
            // solution to always catch instances of cuba that imply it is the destination.
            
            // In order, check for
            parseGreetingOrFarewell(parsedInput);
            parsePleaseComeBack(parsedInput);
            parseThanks(parsedInput);
            parseBookHotel(parsedInput);
            parseColdDestination(parsedInput);
            parseTropicDestination(parsedInput);
            parseWeather(parsedInput);
            parseTravelMethod(parsedInput);
            parseHowFar(parsedInput);
            parseCities(parsedInput);
            parseBudget(parsedInput);
            parseActivities(parsedInput);
            parseGetAround(parsedInput);
            parseGetFood(parsedInput);
            parseGoSkiing(parsedInput);
        }
        return parsedInput;
    }

	public static String getUserMessage(){
    	StringBuilder sb = new StringBuilder();
    	for(String s:tokens){
    		sb.append(s + " ");
    	}
    	return sb.toString();
    }
    
    private static void parseGreetingOrFarewell(ParsedInput parsedInput) {
        // Check for greetings and farewells
        if (parsedInput.containsAnyPhrase(ParserDictionary.greet)) {
            parsedInput.type = ParsedInputType.Greeting;
        } else if (parsedInput.containsAnyPhrase(ParserDictionary.leave)) {
            parsedInput.type = ParsedInputType.Farewell;
        }
    }

    private static void parsePleaseComeBack(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.comeBack)) {
            parsedInput.type = ParsedInputType.PleaseComeBack;
        }
    }

    private static void parseThanks(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.thanks)) {
            parsedInput.type = ParsedInputType.Thanks;
        }
    }

    public static void parseColdDestination(ParsedInput parsedInput){
        String city = parsedInput.getMatchingPhrase(ParserDictionary.bccities);
        if (!city.isEmpty()) {
            parsedInput.type = ParsedInputType.SetDestination;
            parsedInput.setField("city", StringUtils.toTitleCase(city)+",BC");
            parsedInput.setField("destination", "Canada");
        } else {
        	city = parsedInput.getMatchingPhrase(ParserDictionary.albertacities);
        	if (!city.isEmpty()) {
                parsedInput.type = ParsedInputType.SetDestination;
                parsedInput.setField("city", StringUtils.toTitleCase(city)+",AB");
                parsedInput.setField("destination", "Canada");
            }
        }   
    }
    
    private static void parseTropicDestination(ParsedInput parsedInput) {
        String match = parsedInput.getMatchingPhrase(ParserDictionary.tropicdest);
        String places = "";
        
        if (!match.isEmpty()) {
            parsedInput.type = ParsedInputType.SetDestination;
            parsedInput.setField("destination", StringUtils.toTitleCase(match));
        }
        
        String city = parsedInput.getMatchingPhrase(ParserDictionary.tropiccities);
        
        if (!city.isEmpty()) {
            parsedInput.type = ParsedInputType.SetDestination;
            parsedInput.setField("city", StringUtils.toTitleCase(city));
            parsedInput.setField("destination", "Mexico");
            return;
        }
        
        if(match.isEmpty() && city.isEmpty()){
        	/* If the sentence does not contain a destination in our list, try finding
        	 * one using the OpenNLP parser. That way a response can be created using
        	 * the users input even though the agent does not know what it is.        */
        	places = findDest();
        }
        if(!parsedInput.containsAnyPhrase(ParserDictionary.greet)){
        	if(!places.isEmpty()){
            	parsedInput.setField("bad destination", StringUtils.toTitleCase(places));
            	parsedInput.type = ParsedInputType.BadDestination;
            	return;
            }
        }
    }

    private static void parseWeather(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.weather)) {
            parsedInput.type = ParsedInputType.CheckWeather;
        }
    }

    private static void parseTravelMethod(ParsedInput parsedInput) {
        String match = parsedInput.getMatchingPhrase(ParserDictionary.travelMethods);

        if (!match.isEmpty()) {
            parsedInput.type = ParsedInputType.TravelMethod;
            parsedInput.setField("travel method", match);
        }
    }

    private static void parseHowFar(ParsedInput parsedInput) {
    	if (parsedInput.containsAnyPhrase(ParserDictionary.distance)) {
    		parsedInput.type = ParsedInputType.Distance;
            
            /* This checks the input for one or more cities if the input is about distance.
             * It will check each list of cities for a match before moving on.
             */
            List<String> matches = new ArrayList<>();
            
            for(String s: parsedInput.getMatchingPhrases(ParserDictionary.tropiccities)){
            	matches.add(s);
            }
            for(String s: parsedInput.getMatchingPhrases(ParserDictionary.albertacities)){
            	matches.add(s);
        	}
            for(String s: parsedInput.getMatchingPhrases(ParserDictionary.bccities)){
        	   matches.add(s);
            }
            
            if (matches.size() == 0) {
                // No cities given
            } else if (matches.size() == 1) {
                parsedInput.setField("city", matches.get(0));
            } else {
                parsedInput.setField("city", matches.get(0));
                parsedInput.setField("city2", matches.get(1));
            }
        }
    }

    private static void parseCities(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.askForCities)) {
            parsedInput.type = ParsedInputType.ListCities;
        }
    }

    private static void parseBookHotel(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.bookInfo)) {
            parsedInput.type = ParsedInputType.Accomodations;
        }
    }

    private static void parseBudget(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.budget)) {
            parsedInput.type = ParsedInputType.Budget;
            try{
            String budget = parsedInput.tokenCollection.getNumbers().get(0);
            parsedInput.setField("budget", budget);
            } catch(IndexOutOfBoundsException e){
            	parsedInput.type = ParsedInputType.NotEnoughInfo;
            }
        }
    }

    private static void parseActivities(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.activities)) {
            parsedInput.type = ParsedInputType.Activity;
        }
    }

    private static void parseGetAround(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.getAround)) {
            parsedInput.type = ParsedInputType.GetAround;
        }
    }
    
    private static void parseGoSkiing(ParsedInput parsedInput){
    	if(parsedInput.containsAnyPhrase(ParserDictionary.skiactivities)){
    		parsedInput.type = ParsedInputType.SkiResort;
    	}
    }

    private static void parseGetFood(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.food)) {
            parsedInput.type = ParsedInputType.Food;
        }
    }
	
	// All have been added for Assignment 3
    private static String findDest(){
    	Span d[] = lf.find(tokens);
    	String[] holder = Span.spansToStrings(d, tokens);
    	StringBuilder fs = new StringBuilder();
    	
    	for(int i = 0; i < holder.length; i++){
    		fs = fs.append(holder[i] + " ");
    	}
    	return fs.toString();
    }
    
    private static String findNames(){
    	Span d[] = nf.find(tokens);
    	String[] holder = Span.spansToStrings(d, tokens);
    	StringBuilder fs = new StringBuilder();
    	
    	for(int i = 0; i < holder.length; i++){
    		fs = fs.append(holder[i]);
    	}
    	return fs.toString();
    }
    
    private static String findOrgs(){
    	Span d[] = of.find(tokens);
    	String[] holder = Span.spansToStrings(d, tokens);
    	StringBuilder fs = new StringBuilder();
    	
    	for(int i = 0; i < holder.length; i++){
    		fs = fs.append(holder[i] + " ");
    	}
    	return fs.toString();
    }   
    // These methods all train the appropriate NER part of the parser, and the tokenizer
	private void trainNF() throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream("OpenNLP/en-ner-person.bin");
        TokenNameFinderModel tnf = new TokenNameFinderModel(is);
        nf = new NameFinderME(tnf);
    }
    
    private void trainOF() throws InvalidFormatException, IOException{
		InputStream is = new FileInputStream("OpenNLP/en-ner-organization.bin");
        TokenNameFinderModel tnf = new TokenNameFinderModel(is);
        of = new NameFinderME(tnf);
    }
    
    private void trainLF() throws InvalidFormatException, IOException{
		InputStream is = new FileInputStream("OpenNLP/en-ner-location.bin");
        TokenNameFinderModel tnf = new TokenNameFinderModel(is);
        lf = new NameFinderME(tnf);
    }
    
    private void trainTokenizer() throws InvalidFormatException, IOException{
		InputStream is = new FileInputStream("OpenNLP/en-token.bin");
        TokenizerModel tm = new TokenizerModel(is);
        t = new TokenizerME(tm);
    }
    
    private void trainTagger(){
    	POSModel model = new POSModelLoader().load(new File("OpenNLP/en-pos-maxent.bin"));
    	tagger = new POSTaggerME(model);
    }
}