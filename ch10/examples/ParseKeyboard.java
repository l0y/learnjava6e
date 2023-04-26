package ch10.examples;

import java.io.*;
import java.text.*;

public class ParseKeyboard {
  public static void main(String args[]) {
    try {
      System.out.println("Enter a line starting with a number:");
      
      InputStream in = System.in;
      InputStreamReader charsIn = new InputStreamReader( in );
      BufferedReader bufferedCharsIn = new BufferedReader( charsIn );

      String line = bufferedCharsIn.readLine();
      int i = NumberFormat.getInstance().parse( line ).intValue();

      System.out.println("Read line: " + line);
      System.out.println("Parsed number: " + i);
      bufferedCharsIn.close();
    } catch ( IOException e ) {
      System.err.println("Error reading data: " + e);
    } catch ( ParseException pe ) {
      System.err.println("Error parsing data: " + pe);
    }
  }
}
