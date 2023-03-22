package ch06.solutions;

public class Euclid3 {
  public static void main(String args[]) {
    int a1, b1;
    int a = 2701;
    int b = 222;
    // Only try to parse arguments if we have exactly 2
    if (args.length == 2) {
      try {
        a = Integer.parseInt(args[0]);
        b = Integer.parseInt(args[1]);
      } catch (NumberFormatException nfe) {
        System.err.println("Arguments were not both numbers.");
        System.err.println("Using defaults.");
      }
    } else {
      System.err.print("Wrong number of arguments");
      System.err.println(" expected 2).");
      System.err.println("Using defaults.");
    }
    // Store our originals for messaging and such
    a1 = a;
    b1 = b;
    while (b != 0) {
      if (a > b) {
        a = a - b;
      } else {
        b = b - a;
      }
    }
    try {
      if (a == 1) {
        throw new GCDException(a1, b1);
      } else {
        System.out.print("The GCD of " + a1 + " and " + b1 + " is ");
        System.out.println(a);
      }
    } catch (GCDException gcde) {
      System.err.println(gcde.getA() + " and " + gcde.getB() + " have no common denominator.");
    }
  }
}

