// TODO remove this class?
public final class Token {
    private String fullToken;

    public Token(String fullToken) {
        this.fullToken = fullToken;
    }

    public String getFullToken() {
        return fullToken;
    }

    public String extractWord() {
        return Regex.getFirstMatch(Regex.words, fullToken);
    }

    public double extractNumber() {
        return Double.parseDouble(Regex.getFirstMatch(Regex.anyNum, fullToken));
    }
}