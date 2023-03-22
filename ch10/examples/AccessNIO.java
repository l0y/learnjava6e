package ch10.examples;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.channels.FileChannel;
import static java.nio.file.StandardOpenOption.*;

public class AccessNIO {
  String accessFileName = "access.txt";
  Path   accessFilePath = Path.of(accessFileName);
  int    accessCount = 0;
  FileChannel accessChannel;

  public AccessNIO() {
    try {
      boolean initial = !Files.exists(accessFilePath);
      accessChannel = FileChannel.open(accessFilePath, CREATE, READ, WRITE);
      if (initial) {
        // File doesn't exist yet; create the "0" message and save it
        String msg = buildMessage();
        accessChannel.write(ByteBuffer.wrap(msg.getBytes()));
        accessChannel.position(0);
      }
    } catch (IOException ioe) {
      accessChannel = null;
    }
  }

  String buildMessage() {
    String msg =  "This file has been accessed " + accessCount;
    // Go ahead and match the singular/plural count
    if (accessCount == 1) {
      msg += " time.\n";
    } else {
      msg += " times.\n";
    }
    return msg;
  }

  public boolean isReady() {
    return (accessChannel != null && accessChannel.isOpen());
  }

  public void updateAccess() {
    try {
      // First, grab the current contents
      int fsize = (int)accessChannel.size();
      ByteBuffer in = ByteBuffer.allocate(fsize + 2);
      accessChannel.read(in);

      // Put the buffer into a string for simpler manipulation
      String current = new String(in.array());
      int countStart = 28;
      String rawCount = current.substring(countStart, current.indexOf(" ", countStart));
      accessCount = Integer.parseInt(rawCount) + 1;

      // And finally, write the updated message back out
      String msg = buildMessage();
      accessChannel.position(0);
      accessChannel.write(ByteBuffer.wrap(msg.getBytes()));
      accessChannel.truncate(accessChannel.position());
      accessChannel.close();
    } catch (IOException ioe) {
      System.err.println("Failed to update access file");
    } catch (NumberFormatException nfe) {
      System.err.println("Failed to find valid count");
    }
  }

  public static void main(String args[]) {
    AccessNIO access = new AccessNIO();
    if (access.isReady()) {
      access.updateAccess();
    } else {
      System.err.println("Could not update.");
    }
  }
}
