package ch04.examples;

public class WhileDemo {
  public static void main(String args[]) {
    // A simple example of counting down
    int count = 10;
    while( count > 0 ) {
      System.out.println("Counting down: " + count);
      // maybe do other useful things
      // and decrement our count
      count = count - 1;
    }
    System.out.println("Done");

  }
}
