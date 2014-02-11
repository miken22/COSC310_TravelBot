import java.util.*;

public final class Parser {
    private static final int MAX_SUPPORTED_MESSAGE_SIZE = 500;
    
    public static ParsedInput parseUserMessage(String userMessage) {
        ParsedInput parsedInput = new ParsedInput();

        String userMsgLower = userMessage.toLowerCase().trim();
        if (userMsgLower.compareTo("exit") == 0) System.exit(0);
        if (userMsgLower.isEmpty()) {
            parsedInput.type = ParsedInputType.None;
        } else if (userMsgLower.length() > MAX_SUPPORTED_MESSAGE_SIZE) {
            parsedInput.type = ParsedInputType.TooLong;
        } else if (userMsgLower.compareTo("stats") == 0) {
            parsedInput.type = ParsedInputType.Debug_ShowStats;
        } else {
            // Create the token collection
            parsedInput.tokenCollection.parse(userMessage);

            // In order, check for
            parseGreetingOrFarewell(parsedInput);
            parsePleaseComeBack(parsedInput);
            parseThanks(parsedInput);
            parseDestination(parsedInput);
            parseWeather(parsedInput);
            parseTravelMethod(parsedInput);
            parseHowFar(parsedInput);
            parseCities(parsedInput);
            parseBookHotel(parsedInput);
            parseBudget(parsedInput);
            parseActivities(parsedInput);
            parseGetAround(parsedInput);
            parseGetFood(parsedInput);
        }

        return parsedInput;
    }

    private static void parseGreetingOrFarewell(ParsedInput parsedInput) {
        // Check for greetings and farewells

        if (parsedInput.containsAnyPhrase(ParserDictionary.greet)) {
            parsedInput.type = ParsedInputType.Greeting;
        } else if (parsedInput.containsAnyPhrase(ParserDictionary.leave)) {
            parsedInput.type = ParsedInputType.Farewell;
        }

        // Potential TODO: parse user telling name
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

    private static void parseDestination(ParsedInput parsedInput) {
        String match = parsedInput.getMatchingPhrase(ParserDictionary.dest);

        if (!match.isEmpty()) {
            parsedInput.type = ParsedInputType.SetDestination;
            parsedInput.setField("destination", StringUtils.toTitleCase(match));
        }

        String city = parsedInput.getMatchingPhrase(ParserDictionary.cities);

        if (!city.isEmpty()) {
            parsedInput.type = ParsedInputType.SetDestination;
            parsedInput.setField("city", StringUtils.toTitleCase(city));
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

            List<String> matches = parsedInput.getMatchingPhrases(ParserDictionary.cities);

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
            String budget = parsedInput.tokenCollection.getNumbers().get(0);
            parsedInput.setField("budget", budget);
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

    private static void parseGetFood(ParsedInput parsedInput) {
        if (parsedInput.containsAnyPhrase(ParserDictionary.food)) {
            parsedInput.type = ParsedInputType.Food;
        }
    }
}