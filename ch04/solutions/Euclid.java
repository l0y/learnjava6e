package ch04.solutions;

/**
 * A basic implementation of Euclid's greatest common denominator
 * algorithm.
 *
 * https://en.wikipedia.org/wiki/Algorithm
 */
public class Euclid {
  public static void main(String args[]) {
    // For now, just "hard code" the two numbers to compare
    int a = 2701;
    int b = 222;
    int original_a = a;
    int original_b = b;
    while (b != 0) {
      if (a > b) {
        a = a - b;
      } else {
        b = b - a;
      }
    }
    System.out.println("The GCD of " + original_a + " and " + original_b + " is " + a);
  }
}
