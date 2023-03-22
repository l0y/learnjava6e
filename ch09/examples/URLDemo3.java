package ch09.examples;

/**
 * Another variation on our URL processing example.
 * This version prepopulates a queue with a large number
 * of URLs and then creates a new consuer thread for
 * every URL.
 *
 * @see URLQueue
 * @see URLConsumer3
 */
public class URLDemo3 {
  public static void main(String args[]) {
    int count = 100000;
    boolean useVirtual = false;
    if (args.length > 2) {
      System.err.println("Usage is: java URLDemo3 <count> <platform|virtual>");
      System.exit(1);
    }

    // Parse the command-line arguments (if any)
    try {
      for (int a = 0; a < args.length; a++) { 
        if (args[a].equalsIgnoreCase("platform")) {
          useVirtual = false;
        } else if (args[a].equalsIgnoreCase("virtual")) {
          useVirtual = true;
        } else {
          // must be the numeric arg; parse it
          count = Integer.parseInt(args[a]);
        }
      }
    } catch (Exception e) {
      System.err.println("Failed to parse command-line arguments. Using defaults.");
      count = 100000;
      useVirtual = false;
    }
    System.out.print("Using " + (useVirtual ? "virtual" : "platform"));
    System.out.println(" threads for " + count + " URLs.");

    // Create and populate our shared queue object
    URLQueue queue = new URLQueue();
    for (int u = 1; u <= count; u++) {
      queue.addURL("http://some.url/path/" + u);
    }

    // Now the fun begins! Make one consumer for every URL
    for (int c = 0; c < count; c++) {
      URLConsumer3 consumer = new URLConsumer3("C" + c, queue);
      if (useVirtual) {
        Thread.startVirtualThread(consumer);
      } else {
        new Thread(consumer).start();
      }
    }

    // And watch for the empty queue
    try {
      while (!queue.isEmpty()) {
        Thread.sleep(1000);
      }
    } catch (InterruptedException ie) {
      System.err.println("Main thread interrupted while waiting for queue to empty.");
    }
    System.out.println("Done");
  }
}
