package ch09.examples;

public class VirtualThready {
  public static void main( String args [] ) throws InterruptedException {
    Thread t1 = Thread.startVirtualThread(new ShowThread("VFoo"));
    Thread t2 = Thread.startVirtualThread(new ShowThread("VBar"));
    t1.join();
    t2.join();
  }

  static class ShowThread implements Runnable {
    String message;

    ShowThread( String message ) {
        this.message = message;
    }
    public void run() {
        while ( true )
            System.out.println( message );
    }
  }
}
