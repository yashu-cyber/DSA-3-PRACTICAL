import java.io.*;
import java.util.*;

public class PatternSearch {

    // Naive Pattern Matching
    public static ArrayList<Integer> naiveSearch(String text, String pattern) {

        ArrayList<Integer> positions = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return positions;
        }

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m &&
                   text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {
                positions.add(i);
            }
        }

        return positions;
    }

    // Build LPS array for KMP
    public static int[] buildLPS(String pattern) {

        int m = pattern.length();

        int[] lps = new int[m];

        int length = 0;
        int i = 1;

        while (i < m) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // KMP Pattern Matching
    public static ArrayList<Integer> kmpSearch(String text, String pattern) {

        ArrayList<Integer> positions = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return positions;
        }

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == m) {

                    positions.add(i - j);

                    j = lps[j - 1];
                }

            } else {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return positions;
    }

    public static void main(String[] args) {

        String[] files = {
            "a1.txt",
            "a2.txt",
            "a3.txt"
        };

        Scanner scanner = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("       TEXTHACK PATTERN SEARCH");
        System.out.println("=====================================");

        System.out.print("Enter keyword to search : ");

        String pattern = scanner.nextLine();

        // Convert keyword to lowercase
        pattern = pattern.toLowerCase();

        System.out.println();

        // -------------------------------
        // NAIVE PATTERN MATCHING
        // -------------------------------

        System.out.println("=====================================");
        System.out.println("       NAIVE PATTERN MATCHING");
        System.out.println("=====================================");

        int articleId = 101;

        for (String fileName : files) {

            try {

                BufferedReader br =
                        new BufferedReader(
                                new FileReader("corpus/" + fileName));

                String title = br.readLine();

                // Skip blank line
                br.readLine();

                StringBuilder content =
                        new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {
                    content.append(line).append(" ");
                }

                br.close();

                String text = content.toString().trim();

                ArrayList<Integer> positions =
                        naiveSearch(text.toLowerCase(), pattern);

                if (!positions.isEmpty()) {

                    System.out.println();
                    System.out.println("Article ID : " + articleId);
                    System.out.println("Title : " + title);

                    for (int position : positions) {

                        System.out.println(
                            "Pattern found at position : " + position
                        );
                    }

                    System.out.println(
                        "Total occurrences : " + positions.size()
                    );
                }

                articleId++;

            } catch (IOException e) {

                System.out.println(
                    "Cannot read file : " + fileName
                );
            }
        }

        // -------------------------------
        // KMP PATTERN MATCHING
        // -------------------------------

        System.out.println();
        System.out.println("=====================================");
        System.out.println("          KMP PATTERN MATCHING");
        System.out.println("=====================================");

        articleId = 101;

        for (String fileName : files) {

            try {

                BufferedReader br =
                        new BufferedReader(
                                new FileReader("corpus/" + fileName));

                String title = br.readLine();

                // Skip blank line
                br.readLine();

                StringBuilder content =
                        new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {
                    content.append(line).append(" ");
                }

                br.close();

                String text = content.toString().trim();

                ArrayList<Integer> positions =
                        kmpSearch(text.toLowerCase(), pattern);

                if (!positions.isEmpty()) {

                    System.out.println();
                    System.out.println("Article ID : " + articleId);
                    System.out.println("Title : " + title);

                    for (int position : positions) {

                        System.out.println(
                            "Pattern found at position : " + position
                        );
                    }

                    System.out.println(
                        "Total occurrences : " + positions.size()
                    );
                }

                articleId++;

            } catch (IOException e) {

                System.out.println(
                    "Cannot read file : " + fileName
                );
            }
        }

        scanner.close();
    }
}