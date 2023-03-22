package ch09.examples;

import java.util.Random;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A threaded client for our URL producer. Uses a synchronized queue
 * to safely read a URL for processing. (Our simple demo "processes"
 * by printing out the ID of this consumer and the url it consumed.)
 */
public class URLConsumer4 implements Runnable {
  String consumerID;
  Queue<String> queue;
  AtomicBoolean keepWorking;

  Random delay;

  /**
   * Creates a new consumer with the given ID and reference to the shared queue.
   *
   * @param id A unique (unenforced) name for this consumer
   * @param queue The shared queue for storing and distributing URLs
   */
  public URLConsumer4(String id, Queue<String> queue) {
    if (queue == null) {
      throw new IllegalArgumentException("Shared queue cannot be null");
    }
    consumerID = id;
    this.queue = queue;
    keepWorking = new AtomicBoolean(true);
    delay = new Random();
  }

  /**
   * Our working method for the thread. Watches the boolean flag
   * keepWorking as well as the state of the queue to determine
   * whether or not to complete the loop. While working, grab a
   * URL and print it out then repeat.
   */
  public void run() {
    while (keepWorking.get() || !queue.isEmpty()) {
      String url = queue.poll();
      if (url != null) {
        System.out.println(consumerID + " consumed " + url);
      } else {
        System.out.println(consumerID + " skipped empty queue");
      }
      try {
        Thread.sleep(delay.nextInt(1000));
      } catch (InterruptedException ie) {
        System.err.println("Consumer " + consumerID + " interrupted. Quitting.");
        break;
      }
    }
  }

  /**
   * Allow for politely halting this consumer.
   * Watched in the run() method.
   * 
   * @see #run()
   */
  public void setKeepWorking(boolean newState) {
    keepWorking.set(newState);
  }
}
