package ch11.examples;

public class VirtualDemo2 {
  public static void main(String args[]) throws Exception {
    Runnable runnable = new Runnable() {
      public void run() {
        System.out.println("Hello thread! " +
            "ID: " + Thread.currentThread().threadId());
      }
    };
    Thread t = Thread.startVirtualThread(runnable);
    t.join();
  }
}

