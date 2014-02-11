import java.util.*;

public final class TokenCollection {
    private String originalInput;
    private String lowercaseInput;
    private String strippedInput;
    private List<Token> tokens = new ArrayList<>();
    private List<String> words = new ArrayList<>();
    private List<String> numbers = new ArrayList<>();

    public String getStrippedInput() {
        return strippedInput;
    }

    public String getOriginalInput() {
        return originalInput;
    }

    public String getLowercaseInput() {
        return lowercaseInput;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void parse(String originalInput) {
        this.originalInput = originalInput;
        lowercaseInput = originalInput.toLowerCase();
        strippedInput = stripPunctuationSquashWhitespace(lowercaseInput);

        tokens = tokenize(strippedInput);
        words = parseWords(strippedInput);
        numbers = parseNumbers(strippedInput);
    }

    private static String stripPunctuationSquashWhitespace(String input) {
        // Removes most punctuation, leaves $ - + ' , . ?
        String stripped = input.replaceAll(Regex.mostPunctuation.pattern(), "");

        // Squashes multiple whitespace into a single space
        String squashed = Regex.whitespace.matcher(stripped).replaceAll(" ");

        return squashed;
    }

    private static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();

        for (String fullToken : input.split(Regex.whitespace.pattern())) {
            tokens.add(new Token(fullToken));
        }

        return tokens;
    }

    private static List<String> parseWords(String input) {
        return Regex.getAllMatches(Regex.words, input);
    }

    private static List<String> parseNumbers(String input) {
        return Regex.getAllMatches(Regex.anyNum, input);
    }
}