package ch09.examples;

import java.util.Random;
import java.util.Queue;

/**
 * Simple producer for use in our multithreaded example.
 * Uses a synchronized queue to safely store URLs for processing.
 */
public class URLProducer4 implements Runnable {
  String producerID;
  int urlCount;
  Queue<String> queue;

  Random delay;

  /**
   * Create a new producer with the given name. It will produce the
   * specified number of URLs and store them in the provided queue.
   *
   * @param id A unique (unenforced) name for this producer
   * @param count How many URLs this producer will create before quitting
   * @param queue The shared, synchronized queue for URLs
   */
  public URLProducer4(String id, int count, Queue<String> queue) {
    // Don't even create this producer if a negative count was supplied or there's no queue
    if (count <= 0) {
      throw new IllegalArgumentException("Count must be positive");
    }
    if (queue == null) {
      throw new IllegalArgumentException("Shared queue cannot be null");
    }
    producerID = id;
    urlCount = count;
    this.queue = queue;
    delay = new Random();
  }

  /**
   * Our working method for the thread. Uses the count supplied to
   * the constructor to produce a batch of URLs and store them to
   * the shared queue. To make it a little more interesting, a random
   * delay is added at the end of each iteration. 
   */
  public void run() {
    for (int i = 1; i <= urlCount; i++) {
      String url = "https://some.url/at/path/" + i;
      queue.add(producerID + " " + url);
      System.out.println(producerID + " produced " + url);
      try {
        Thread.sleep(delay.nextInt(500));
      } catch (InterruptedException ie) {
        System.err.println("Producer " + producerID + " interrupted. Quitting.");
        break;
      }
    }
  }
}
