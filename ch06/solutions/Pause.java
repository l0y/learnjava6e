package ch06.solutions;

public class Pause {
  public static void main(String args[]) {
    System.out.print("Starting... ");
    try {
      Thread.sleep(5000);
    } catch(InterruptedException ie) {
      System.out.println("Interrupted!");
    }
    System.out.println("done");
  }
}
