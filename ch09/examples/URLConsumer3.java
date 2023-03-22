package ch09.examples;

import java.util.Random;

/**
 * A threaded client for our URL producer. Uses a synchronized queue
 * to safely read a URL for processing. (Our simple demo "processes"
 * by printing out the ID of this consumer and the url it consumed.)
 *
 * This version handles one URL and ends. There is no "keep working"
 * flag nor delay.
 */
public class URLConsumer3 implements Runnable {
  String consumerID;
  URLQueue queue;

  /**
   * Creates a new consumer with the given ID and reference to the shared queue.
   *
   * @param id A unique (unenforced) name for this consumer
   * @param queue The shared queue for storing and distributing URLs
   */
  public URLConsumer3(String id, URLQueue queue) {
    if (queue == null) {
      throw new IllegalArgumentException("Shared queue cannot be null");
    }
    consumerID = id;
    this.queue = queue;
  }

  /**
   * Our working method for the thread. Watches the boolean flag
   * keepWorking as well as the state of the queue to determine
   * whether or not to complete the loop. While working, grab a
   * URL and print it out then repeat.
   */
  public void run() {
    String url = queue.getURL();
    if (url != null) {
      if (url.endsWith("0000")) {
        System.out.println(consumerID + " consumed " + url);
      }
    } else {
      System.out.println(consumerID + " skipped empty queue");
    }
  }
}
