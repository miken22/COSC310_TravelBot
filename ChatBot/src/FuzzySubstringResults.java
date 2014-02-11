public final class FuzzySubstringResults {
    int levenshteinDistance = Integer.MAX_VALUE;
    int indexOfEndOfMatch = -1;
    double similarity = 0;

    FuzzySubstringResults(int levenshteinDistance, int indexOfEndOfMatch, double similarity) {
        this.levenshteinDistance = levenshteinDistance;
        this.indexOfEndOfMatch = indexOfEndOfMatch;
        this.similarity = similarity;
    }
}