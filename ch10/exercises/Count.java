package ch10.exercises;

import java.io.*;

public class Count {
  String fileToCount;

  public Count(String fileToCount) {
    this.fileToCount = fileToCount;
    // ...
  }

  public void printStats() {
    System.out.println("Analyzing " + fileToCount);
    // ...
  }

  public static void main(String args[]) {
    // Check for a filename argument; if it's missing, print an error
    // ...

    // Create a new instance of Count with the given file and get its stats
    Count c = new Count(args[0]);
    c.printStats();
  }
}
