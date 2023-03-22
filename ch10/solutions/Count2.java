package ch10.solutions;

import java.io.*;

public class Count2 {
  File toCount;
  long lineCount;
  long wordCount;

  public Count2(String fileToCount) {
    toCount = new File(fileToCount);
  }

  public void printStats() {
    System.out.println("Analyzing " + toCount.getName());
    if (toCount.canRead()) {
      System.out.println("  Size: " + toCount.length() + " bytes");
      try (BufferedReader br = new BufferedReader(new FileReader(toCount))) {
        String line = null;
        while ((line = br.readLine()) != null) {
          lineCount++;
          String words[] = line.split("\\s+");
          wordCount += words.length;
        }
        System.out.println("  Lines: " + lineCount);
        System.out.println("  Words: " + wordCount);
      } catch (IOException ioe) {
        System.err.println("Failed to complete the analysis: " + ioe);
        ioe.printStackTrace(System.err);
      }
    } else {
      // Hmm, two possible reasons we can't read the file:
      // either it doesn't exist, or it exists but we don't have permission
      if (toCount.exists()) {
        System.out.println("  (No permission)");
      } else {
        System.out.println("  (Not found)");
      }
    }
  }

  public static void main(String args[]) {
    // Check for a filename argument; if it's missing, print an error
    if (args.length != 1) {
      System.err.println("Must supply one filename to analyze.");
      System.exit(1);
    }

    // Create a new instance of Count with the given file and get its stats
    Count2 c = new Count2(args[0]);
    c.printStats();
  }
}
