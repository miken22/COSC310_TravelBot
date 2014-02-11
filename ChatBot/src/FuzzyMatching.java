
public final class FuzzyMatching {
    // Wrapper to do string to char array for us
    public static FuzzySubstringResults Substring(String s, String t) {
        return Substring(s.toCharArray(), t.toCharArray());
    }

    // Modified from Dynamic programming implementation of Levenshtein distance
    //  Copied almost directly from Wikipedia (http://en.wikipedia.org/wiki/Levenshtein_distance)
    // Except we don't care how many characters we have to skip to start matching
    // and we don't care how many characters come after a successful match
    public static FuzzySubstringResults Substring(char[] s, char[] t) {
        int m = s.length;
        int n = t.length;

        // Base cases
        if (m == 0) return new FuzzySubstringResults(Integer.MAX_VALUE, -1, 0);
        if (n == 0) return new FuzzySubstringResults(m, 0, 1);

        int[][] d = new int[m + 1][n + 1];

        for (int j = 1; j <= n; j++) {
            d[0][j] = j;
        }

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (s[i - 1] == t[j - 1]) {
                    d[i][j] = d[i - 1][j - 1];    // characters match
                } else {
                    d[i][j] = getMinimum(
                            d[i - 1][j] + 1,      // deletion
                            d[i][j - 1] + 1,      // insertion
                            d[i - 1][j - 1] + 1); // substitution
                }
            }
        }

        // Check for the minimum in the bottom row
        // We don't care how many characters come after a match
        int min = Integer.MAX_VALUE;
        int indexOfMin = 0;
        for (int i = 0; i <= m; i++) {
            if (d[i][n] <= min) {
                min = d[i][n];
                indexOfMin = i;
            }
        }

        return new FuzzySubstringResults(min, indexOfMin, getSimilarity(m, n, min));
    }

    // Gets a 'similarity index' for two fuzzy matched strings
    // higher values indicate higher similarity
    private static double getSimilarity(int sourceLen, int matchLen, int distance) {
        return (double) (matchLen) / (double) (distance + 1);
    }

    // Gets the right index of the source string where the best match of substring is found
    // Returns -1 if errors are over the allowed amount
    public static int IndexOfEndOfMatch(String source, String substring, double errorAllowed) {
        int smallerLength = Math.min(source.length(), substring.length());
        int allowedDistance = (int) Math.round(errorAllowed * (double) smallerLength);

        FuzzySubstringResults results = Substring(source, substring);

        if (results.levenshteinDistance <= allowedDistance) {
            return results.indexOfEndOfMatch;
        } else {
            return -1;
        }
    }

    private static int getMinimum(int... values) {
        int min = values[0];

        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) min = values[i];
        }

        return min;
    }

    public static int numDigits(int x) {
        if (x == 0) {
            return 1;
        } else if (x > 0) {
            return 1 + (int) Math.floor(Math.log10(Math.abs(x)));
        } else { // x < 0
            return 2 + (int) Math.floor(Math.log10(Math.abs(x)));
        }
    }

    // Returns a string repeating padding, length times
    public static String pad(int length, char padding) {
        return new String(new char[length]).replace('\0', padding); // Admittedly slightly hacky
    }
}