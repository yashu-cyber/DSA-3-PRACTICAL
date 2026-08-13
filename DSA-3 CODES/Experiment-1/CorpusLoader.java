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
            this.wordCount = 0;
        } else {
            this.wordCount = content.trim().split("\\s+").length;
        }
    }

    // Display article details
    public void display() {

        System.out.println("-------------------------------------------");
        System.out.println("Article ID : " + id);
        System.out.println("Title      : " + title);
        System.out.println("Word Count : " + wordCount);
        System.out.println("Content    : ");
        System.out.println(content);
        System.out.println("-------------------------------------------");
        System.out.println();
    }
}

public class CorpusLoader {

    public static void main(String[] args) {

        // Article Repository
        ArrayList<Article> repository = new ArrayList<>();

        // Corpus files
     String[] files = {
    "a1.txt",
    "a2.txt",
    "a3.txt",
    "a4.txt",
    "a5.txt"
};

        // Starting Article ID
        int id = 101;

        // Total words in all articles
        int totalWords = 0;

        // Read each file
        for (String fileName : files) {

            try {

                // File location
                File file = new File("corpus/" + fileName);

                // Open file
                BufferedReader br =
                        new BufferedReader(new FileReader(file));

                // Read title
                String title = br.readLine();

                // Skip blank line
                br.readLine();

                // Store article content
                StringBuilder content = new StringBuilder();

                String line;

                // Read remaining lines
                while ((line = br.readLine()) != null) {

                    content.append(line).append(" ");
                }

                // Create Article object
                Article article = new Article(
                        id,
                        title,
                        content.toString().trim()
                );

                // Add article to repository
                repository.add(article);

                // Add word count
                totalWords += article.wordCount;

                // Next ID
                id++;

                // Close file
                br.close();

            } catch (IOException e) {

                System.out.println(
                    "Cannot read file : " + fileName
                );
            }
        }

        // Display Repository
        System.out.println("======================================");
        System.out.println("      TEXTHACK ARTICLE REPOSITORY");
        System.out.println("======================================");
        System.out.println();

        // Display all articles
        for (Article article : repository) {
            article.display();
        }

        // Repository statistics
        System.out.println("Repository Statistics");
        System.out.println("----------------------");

        System.out.println(
            "Total Articles Loaded : " + repository.size()
        );

        System.out.println(
            "Total Words           : " + totalWords
        );
    }
}