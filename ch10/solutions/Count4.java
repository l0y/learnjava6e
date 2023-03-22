package ch10.solutions;

import java.util.List;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.nio.file.StandardOpenOption.*;

public class Count4 {
  Path toCount;
  Path logPath;
  long lineCount;
  long wordCount;

  DateTimeFormatter stamp = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

  public Count4(String fileToCount, String logFile) {
    toCount = Path.of(fileToCount);
    if (logFile != null) {
      logPath = Path.of(logFile);
    }
  }

  public void printStats() {
    System.out.println("Analyzing " + toCount.getFileName());
    try {
      if (Files.isReadable(toCount)) {
        System.out.println("  Size: " + Files.size(toCount) + " bytes");
        List<String> lines = Files.readAllLines(toCount);
        for (String line : lines) {
          lineCount++;
          String words[] = line.split("\\s+");
          wordCount += words.length;
        }
        System.out.println("  Lines: " + lineCount);
        System.out.println("  Words: " + wordCount);
      } else {
        // Hmm, two possible reasons we can't read the file:
        // either it doesn't exist, or it exists but we don't have permission
        if (Files.exists(toCount)) {
          System.out.println("  (No permission)");
        } else {
          System.out.println("  (Not found)");
        }
      }
    } catch (IOException ioe) {
      System.err.println("Failed to complete the analysis: " + ioe);
      ioe.printStackTrace(System.err);
    }
  }

  public void logStats() {
    try {
      if (Files.isReadable(toCount)) {
        // Get the same stats as before, just don't print them to stdout
        long byteCount = Files.size(toCount);
        List<String> lines = Files.readAllLines(toCount);
        for (String line : lines) {
          lineCount++;
          String words[] = line.split("\\s+");
          wordCount += words.length;
        }

        // Create our log entry
        String msg = stamp.format(LocalDateTime.now()) + "  "
            + toCount.getFileName() + "  "
            + lineCount + "  " + wordCount + "  " + byteCount
            + "\n";

        // Now append to our log file
        FileChannel channel = FileChannel.open(logPath, CREATE, WRITE, APPEND);
        channel.write(ByteBuffer.wrap(msg.getBytes()));
        channel.close();
      } else {
        // Hmm, two possible reasons we can't read the file:
        // either it doesn't exist, or it exists but we don't have permission
        if (Files.exists(toCount)) {
          System.out.println("  (No permission)");
        } else {
          System.out.println("  (Not found)");
        }
      }
    } catch (IOException ioe) {
      System.err.println("Failed to complete the analysis: " + ioe);
      ioe.printStackTrace(System.err);
    }
  }

  public static void main(String args[]) {
    // Check for arguments; if they are missing, print an error
    if (args.length == 1) {
      // Old style, analyze and print to the screen as usual
      Count4 c = new Count4(args[0], null);
      c.printStats();
    } else if (args.length == 2) {
      // New style, analyze and append the results to our log file
      Count4 c = new Count4(args[0], args[1]);
      c.logStats();
    } else {
      System.err.println("Must supply one filename and an optional log file name");
      System.exit(1);
    }
  }
}
