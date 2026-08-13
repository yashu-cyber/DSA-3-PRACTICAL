import java.io.*;
import java.util.*;

class Article {

    int id;
    String title;
    String content;
    int wordCount;

    // Constructor
    public Article(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

        // Count words
        if (content.trim().isEmpty()) {
            wordCount = 0;
        } else {
            wordCount = content.trim().split("\\s+").length;
        }
    }

    // Check whether keyword exists in title or content
    public boolean matches(String keyword) {

        return title.toLowerCase().contains(keyword.toLowerCase())
                || content.toLowerCase().contains(keyword.toLowerCase());
    }

    // Display article
    public void display() {

        System.out.println("----------------------------------------");
        System.out.println("Article ID : " + id);
        System.out.println("Title      : " + title);
        System.out.println("Word Count : " + wordCount);
        System.out.println("Content    : ");
        System.out.println(content);
        System.out.println("----------------------------------------");
    }
}

public class QueryProcessor {

    public static void main(String[] args) {

        // Article Repository
        ArrayList<Article> repository = new ArrayList<>();

        // Corpus files
        String[] files = {
            "a1.txt",
            "a2.txt",
            "a3.txt"
        };

        int id = 101;

        // Load articles
        for (String fileName : files) {

            try {

                File file = new File("corpus/" + fileName);

                BufferedReader br =
                        new BufferedReader(new FileReader(file));

                // Read title
                String title = br.readLine();

                // Skip blank line
                br.readLine();

                // Read content
                StringBuilder content = new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {
                    content.append(line).append(" ");
                }

                // Create Article
                Article article = new Article(
                        id,
                        title,
                        content.toString().trim()
                );

                // Add to repository
                repository.add(article);

                id++;

                br.close();

            } catch (IOException e) {

                System.out.println(
                    "Cannot read file : " + fileName
                );
            }
        }

        // Query Processor
        Scanner scanner = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("      TEXTHACK QUERY PROCESSOR");
        System.out.println("=====================================");

        System.out.print("Enter keyword to search : ");

        String keyword = scanner.nextLine();

        System.out.println();
        System.out.println("Matching Articles");
        System.out.println("----------------------------------------");

        boolean found = false;

        // Search repository
        for (Article article : repository) {

            if (article.matches(keyword)) {

                article.display();

                found = true;
            }
        }

        // No matching article
        if (!found) {

            System.out.println(
                "No matching articles found."
            );
        }

        scanner.close();
    }
}