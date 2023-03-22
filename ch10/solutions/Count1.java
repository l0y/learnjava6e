package ch10.solutions;

import java.io.*;

public class Count1 {
  File toCount;

  public Count1(String fileToCount) {
    toCount = new File(fileToCount);
  }

  public void printStats() {
    System.out.println("Analyzing " + toCount.getName());
    if (toCount.exists()) {
      System.out.println("  Size: " + toCount.length() + " bytes");
    } else {
      System.out.println("  (Not found)");
    }
  }

  public static void main(String args[]) {
    // Check for a filename argument; if it's missing, print an error
    if (args.length != 1) {
      System.err.println("Must supply one filename to analyze.");
      System.exit(1);
    }

    // Create a new instance of Count with the given file and get its stats
    Count1 c = new Count1(args[0]);
    c.printStats();
  }
}
