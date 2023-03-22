package ch06.solutions;

public class GCDException extends Exception {
  private int badA;
  private int badB;

  GCDException(int a, int b) {
    super("No common factors for " + a + ", " + b);
    badA = a;
    badB = b;
  }

  public int getA() { return badA; }
  public int getB() { return badB; }
}
