package ch09.examples;

public class Thready2 {
  public static void main( String args [] ) {
    Thread foo = new Thread(new ShowThread("Foo"));
    foo.setPriority( Thread.MIN_PRIORITY );
    Thread bar = new Thread(new ShowThread("Bar"));
    bar.setPriority( Thread.MAX_PRIORITY );

    foo.start();
    bar.start();
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
