package ch11.examples;

public class VirtualDemo3 {
  public static void main(String args[]) throws Exception {
    Thread t = Thread.startVirtualThread( () ->
      System.out.println("Hello thread! " +
          "ID: " + Thread.currentThread().threadId())
    );
    t.join();
  }
}

