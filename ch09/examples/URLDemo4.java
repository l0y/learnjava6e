package ch09.examples;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Manage multiple producers and consumers to demonstrate how
 * threads work in tandem. Creates a pair of producers and a
 * pair of consumers all with access to a shared queue.
 *
 * This version uses virtual threads and the built-in
 * ConcurrentLinkedQueue instead of the platform threads and
 * custom queue of the original URLDemo.java
 *
 * @see URLQueue
 * @see URLProducer
 * @see URLConsumer
 */
public class URLDemo4 {
  public static void main(String args[]) {
    // Create our shared queue object
    ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    
    // Now create some producers with unique names and a reference to our queue
    URLProducer4 p1 = new URLProducer4("P1", 3, queue);
    URLProducer4 p2 = new URLProducer4("P2", 3, queue);
    
    // And some consumers with their own names and a reference to our queue
    URLConsumer4 c1 = new URLConsumer4("C1", queue);
    URLConsumer4 c2 = new URLConsumer4("C2", queue);
    
    // Get everyone going!
    System.out.println("Starting virtual threads...");
    Thread vp1 = Thread.startVirtualThread(p1);
    Thread vp2 = Thread.startVirtualThread(p2);
    Thread vc1 = Thread.startVirtualThread(c1);
    Thread vc2 = Thread.startVirtualThread(c2);
    
    // First wait around for the producers to finish
    try {
      vp1.join();
      vp2.join();
    } catch (InterruptedException ie) {
      System.err.println("Interrupted waiting for producers to finish");
    }
    
    // OK, we know there won't be any more URLs made, so let the consumers
    // finish once the queue is empty
    c1.setKeepWorking(false);
    c2.setKeepWorking(false);
    try {
      vc1.join();
      vc2.join();
    } catch (InterruptedException ie) {
      System.err.println("Interrupted waiting for consumers to finish");
    }
    
    System.out.println("Done");
  }
}
