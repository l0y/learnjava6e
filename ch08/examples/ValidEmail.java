package ch08.examples;

import java.util.regex.Pattern;

public class ValidEmail {
  static String[] validators = {
    ".*@.*",
    "[^@]+@[^@]+",
    "[^@]+@[^@]+\\.(com|org)",
    "[^@]+@[^@]+\\.[a-z]+",
    "(?i)[^@]+@[^@]+\\.[a-z]+"
  };

  public static void main(String args[]) {
    String sample = "my.name@some.domain";
    if (args.length == 1) {
      // Got one command-line arg, use it as the address to check
      sample = args[0];
    } else {
      System.out.println("Using default email address: " + sample);
      System.out.println("To use your own address, provide it as the sole command-line argument.");
    }

    // To make pretty output, let's find the longest pattern
    int max = 0;
    for (String v : validators) {
      if (v.length() > max) { max = v.length(); }
    }

    // Now let's try each validator and show our result
    for (String v : validators) {
      System.out.print(sample + " : ");
      System.out.print(v + " => ");
      for (int s = (max - v.length()); s > 0; s--) {
        System.out.print(" ");
      }
      System.out.println(Pattern.matches(v, sample));
    }
  }
}
