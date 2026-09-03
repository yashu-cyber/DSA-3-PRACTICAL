import java.util.Arrays;

public class Experiment6 {

    // ==========================================================
    // --- EXPERIMENT 6A: Sequence Alignment ---
    // ==========================================================
    
    public static int sequenceAlignment(String seq1, String seq2, int match, int mismatch, int gap) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases: penalties for gaps against empty strings
        for (int i = 0; i <= m; i++) dp[i][0] = i * gap;
        for (int j = 0; j <= n; j++) dp[0][j] = j * gap;

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Calculate match or mismatch score
                int score = (seq1.charAt(i - 1) == seq2.charAt(j - 1)) ? match : mismatch;
                
                // Calculate options
                int align = dp[i - 1][j - 1] + score;      // Diagonal
                int gapInSeq2 = dp[i - 1][j] + gap;        // Up (Deletion)
                int gapInSeq1 = dp[i][j - 1] + gap;        // Left (Insertion)
                
                // Store the maximum score
                dp[i][j] = Math.max(align, Math.max(gapInSeq1, gapInSeq2));
            }
        }
        return dp[m][n];
    }

    // ==========================================================
    // --- EXPERIMENT 6B: Bitmask DP (Traveling Salesperson) ---
    // ==========================================================
    
    public static int tspBitmask(int[][] distMatrix) {
        int n = distMatrix.length;
        int numSubsets = 1 << n; // 2^n total possible subsets
        int[][] dp = new int[numSubsets][n];
        
        // Use a safe large number to represent infinity to avoid overflow during addition
        int INF = 1000000000; 
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        
        // Base case: Start at city 0 (mask = 1, which is binary 0001)
        dp[1][0] = 0; 

        for (int mask = 1; mask < numSubsets; mask++) {
            for (int u = 0; u < n; u++) {
                // If city 'u' is in the current subset (mask)
                if ((mask & (1 << u)) != 0) {
                    for (int v = 0; v < n; v++) {
                        // If city 'v' is NOT in the current subset, transition to it
                        if ((mask & (1 << v)) == 0) {
                            int nextMask = mask | (1 << v);
                            dp[nextMask][v] = Math.min(dp[nextMask][v], dp[mask][u] + distMatrix[u][v]);
                        }
                    }
                }
            }
        }

        // Add the distance back to the starting city (city 0)
        int finalMask = numSubsets - 1; 
        int minCost = INF;
        for (int i = 1; i < n; i++) {
            minCost = Math.min(minCost, dp[finalMask][i] + distMatrix[i][0]);
        }
        
        return minCost;
    }

    // ==========================================================
    // --- MAIN EXECUTION ---
    // ==========================================================
    
    public static void main(String[] args) {
                           
        System.out.println("--- Experiment 6A: Sequence Alignment ---");
        String s1 = "GATTACA";
        String s2 = "GCATGCU";
        
        // Using standard bioinformatics scores: match = +1, mismatch = -1, gap = -1
        System.out.println("Sequence 1: " + s1);
        System.out.println("Sequence 2: " + s2);
        System.out.println("Alignment Score: " + sequenceAlignment(s1, s2, 1, -1, -1));
                           
        System.out.println("\n--- Experiment 6B: Bitmask DP (TSP) ---");
        // Example distance matrix between 4 cities
        int[][] graph = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        System.out.println("Distance Matrix (4 cities):");
        for (int[] row : graph) {
            System.out.println(Arrays.toString(row));
        }
        
        System.out.println("Minimum cost to visit all cities and return: " + tspBitmask(graph));
    }
}