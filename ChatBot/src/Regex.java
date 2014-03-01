import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String matching based on regular expressions.
 * 
 * @author Manny Haller
 *
 */

public final class Regex {
    public static Pattern wordsAndNumbers = Pattern.compile("(\\s[0-9]*\\.[0-9]*\\s)|((?i)\\b([A-Z0-9]*'[A-Z0-9]*|[A-Z0-9]*-[A-Z0-9]*|[A-Z0-9]*)\\b)");
    public static Pattern words = Pattern.compile("(?i)\\b([A-Z]*'[A-Z]*|[A-Z]*-[A-Z]*|[A-Z]*|)\\b");
    public static Pattern anyNum = Pattern.compile("[+-]?(\\d{1,3}(,\\d{3})+\\.\\d*|\\d{1,3}(,\\d{3})+|\\d*\\.\\d*|\\d*)");
    public static Pattern mostPunctuation = Pattern.compile("\\~*+`*+\\!*+\\@*+\\#*+\\%*+\\^*+\\&*+\\**+\\(*+\\)*+\\_*+\\=*+\\{*+\\}*+\\[*+\\]*+\\|*+\\\\*+\\:*+\\;*+\\\"*+<*+>*+\\?*+\\.*+"); // doesn't include $ - + ' ,
    public static Pattern whitespace = Pattern.compile("\\s+");

    public static String getFirstMatch(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public static List<String> getAllMatches(Pattern pattern, String input) {
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = pattern.matcher(input);

        String tmp = "";
        while (matcher.find()) {
            tmp = matcher.group().trim();
            if (!tmp.isEmpty()) allMatches.add(matcher.group());
        }

        return allMatches;
    }

    public static List<String> tokenizeOnWhitespace(String input) {
        return Arrays.asList(input.split(Regex.whitespace.pattern()));
    }
}