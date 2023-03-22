package ch06.examples;

import java.util.logging.*;

public class LogTest {
  public static void main(String argv[]) {
    Logger logger = Logger.getLogger("ch06.LogTest");

    logger.severe("Power lost - running on backup!");
    logger.warning("Database connection lost, retrying...");
    logger.info("Startup complete.");
    logger.config("Server configuration: standalone, Java 20");
    logger.fine("Loading graphing package.");
    logger.finer("Doing pie chart");
    logger.finest("Starting bubble sort: value ="+42);
  }
}

