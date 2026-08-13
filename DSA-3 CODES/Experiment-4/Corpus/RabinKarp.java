import java.io.*;
import java.util.*;

public class RabinKarp {

    // Base value for hashing
    static final int BASE = 256;

    // Prime number used for modulo hashing
    static final int PRIME = 101;

    // Rabin-Karp search
    public static ArrayList<Integer> search(String text, String pattern) {

        ArrayList<Integer> positions = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) {
            return positions;
        }

        long patternHash = 0;
        long textHash = 0;

        long highestPower = 1;

        // Calculate BASE^(m-1) % PRIME
        for (int i = 0; i < m - 1; i++) {
            highestPower = (highestPower * BASE) % PRIME;
        }

        // Calculate initial hash values
        for (int i = 0; i < m; i++) {

            patternHash =
                    (BASE * patternHash + pattern.charAt(i))
                    % PRIME;

            textHash =
                    (BASE * textHash + text.charAt(i))
                    % PRIME;
        }

        // Slide pattern over text
        for (int i = 0; i <= n - m; i++) {

            // If hash values match, verify characters
            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {

                    if (text.charAt(i + j)
                            != pattern.charAt(j)) {

                        match = false;
                        break;
                    }
                }

                if (match) {
                    positions.add(i);
                }
            }

            // Calculate hash for next window
            if (i < n - m) {

                textHash =
                        (BASE *
                        (textHash
                        - text.charAt(i) * highestPower)
                        + text.charAt(i + m))
                        % PRIME;

                // Make hash positive
                if (textHash < 0) {
                    textHash += PRIME;
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
        System.out.println("       TEXTHACK RABIN-KARP SEARCH");
        System.out.println("=====================================");

        System.out.print("Enter keyword to search : ");

        String pattern = scanner.nextLine().toLowerCase();

        System.out.println();

        System.out.println("=====================================");
        System.out.println("       RABIN-KARP PATTERN SEARCH");
        System.out.println("=====================================");

        int articleId = 101;

        for (String fileName : files) {

            try {

                BufferedReader br =
                        new BufferedReader(
                                new FileReader("corpus/" + fileName));

                // Read title
                String title = br.readLine();

                // Skip blank line
                br.readLine();

                // Read article content
                StringBuilder content =
                        new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {

                    content.append(line).append(" ");
                }

                br.close();

                String text =
                        content.toString().trim().toLowerCase();

                // Search using Rabin-Karp
                ArrayList<Integer> positions =
                        search(text, pattern);

                // Display only matching articles
                if (!positions.isEmpty()) {

                    System.out.println();
                    System.out.println(
                            "Article ID : " + articleId);

                    System.out.println(
                            "Title : " + title);

                    for (int position : positions) {

                        System.out.println(
                                "Pattern found at position : "
                                + position);
                    }

                    System.out.println(
                            "Total occurrences : "
                            + positions.size());

                    System.out.println(
                            "----------------------------------------");
                }

                articleId++;

            } catch (IOException e) {

                System.out.println(
                        "Cannot read file : " + fileName);
            }
        }

        scanner.close();
    }
}