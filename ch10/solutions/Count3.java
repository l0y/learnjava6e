package ch10.solutions;

import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class Count3 {
  Path toCount;
  long lineCount;
  long wordCount;

  public Count3(String fileToCount) {
    toCount = Path.of(fileToCount);
  }

  public void printStats() {
    System.out.println("Analyzing " + toCount.getFileName());
    try {
      if (Files.isReadable(toCount)) {
        System.out.println("  Size: " + Files.size(toCount) + " bytes");
        List<String> lines = Files.readAllLines(toCount);
        for (String line : lines) {
          lineCount++;
          String words[] = line.split("\\s+");
          wordCount += words.length;
        }
        System.out.println("  Lines: " + lineCount);
        System.out.println("  Words: " + wordCount);
      } else {
        // Hmm, two possible reasons we can't read the file:
        // either it doesn't exist, or it exists but we don't have permission
        if (Files.exists(toCount)) {
          System.out.println("  (No permission)");
        } else {
          System.out.println("  (Not found)");
        }
      }
    } catch (IOException ioe) {
      System.err.println("Failed to complete the analysis: " + ioe);
      ioe.printStackTrace(System.err);
    }
  }

  public static void main(String args[]) {
    // Check for a filename argument; if it's missing, print an error
    if (args.length != 1) {
      System.err.println("Must supply one filename to analyze.");
      System.exit(1);
    }

    // Create a new instance of Count with the given file and get its stats
    Count3 c = new Count3(args[0]);
    c.printStats();
  }
}
