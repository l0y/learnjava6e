package ch13.solutions;

import java.net.Socket;
import java.io.*;

public class FDClient {
  static int fdPort = 3283; // D-A-T-E on a phonepad :)

  public static void printDateAndTime(String host) {
    try (Socket fdSocket = new Socket(host, fdPort)) {
      InputStreamReader isr =
        new InputStreamReader(fdSocket.getInputStream());
      BufferedReader br = new BufferedReader(isr);
      String dt = br.readLine();
      System.out.println(host + ": " + dt);
      br.close();
    } catch(IOException ioe) {
      System.err.println("Failed to retrieve date and time: " + ioe);
    }
  }

  public static void main(String args[]) {
    if (args.length != 1) {
      System.err.println("Please provide a host to contact.");
      System.exit(1);
    }
    printDateAndTime(args[0]);
  }
}
