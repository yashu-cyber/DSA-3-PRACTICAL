public class DPExperiments {

    // --- EXPERIMENT 5: Wagner-Fischer Algorithm ---
    
    public static int wagnerFischerEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases: transforming to/from an empty string
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // Characters match
                } else {
                    int delete = dp[i - 1][j];
                    int insert = dp[i][j - 1];
                    int substitute = dp[i - 1][j - 1];
                    // Take 1 + minimum of the three operations
                    dp[i][j] = 1 + Math.min(substitute, Math.min(delete, insert));
                }
            }
        }
        return dp[m][n];
    }

    // Helper class to hold fuzzy search results
    public static class MatchResult {
        public String word;
        public int distance;
        public MatchResult(String w, int d) { 
            this.word = w; 
            this.distance = d; 
        }
    }

    public static MatchResult fuzzySearch(String query, String[] dictionary) {
        String bestMatch = "";
        int minDistance = Integer.MAX_VALUE;

        for (String word : dictionary) {
            int dist = wagnerFischerEditDistance(query, word);
            if (dist < minDistance) {
                minDistance = dist;
                bestMatch = word;
            }
        }
        return new MatchResult(bestMatch, minDistance);
    }

    // --- MAIN EXECUTION ---
    public static void main(String[] args) {
        System.out.println("--- Testing Wagner-Fischer Edit Distance ---");
        String s1 = "kitten";
        String s2 = "sitting";
        System.out.println("Edit distance between '" + s1 + "' and '" + s2 + "': " + 
                           wagnerFischerEditDistance(s1, s2));

        System.out.println("\n--- Testing Fuzzy Search ---");
        String[] dictionary = {"algorithm", "alignment", "apartment", "application"};
        String query = "aligmnent"; // Deliberately misspelled
        
        MatchResult result = fuzzySearch(query, dictionary);
        System.out.println("Search Query: " + query);
        System.out.println("Closest match: " + result.word + " (Distance: " + result.distance + ")");
    }
}