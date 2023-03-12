package ch13.solutions;

import java.net.*;
import java.io.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * An example of implementing a web service client in Java.
 * This example retrieves three random integers (between 1 and 100)
 * from random.org.
 *
 * You'll have to sign up for a developer account. The free tier is
 * quite generous and more than enough for our needs. After getting verified
 * and signing in, you can create a new API Key (the name is arbitrary, but
 * we chose "Learning Java 6e Demo". Be sure to fill in your key before
 * trying to connect.
 */
public class NetworkInt {
  // random.org uses the JSON format for both requests and responses.
  // You can read more at https://www.json.org/json-en.html
  String jsonReq = """ 
    {
      "jsonrpc": "2.0",
      "method": "generateIntegers",
      "params": {
          "apiKey": "PUT-YOUR-KEY-HERE",
          "n": 3,
          "min": 1,
          "max": 100,
          "replacement": true
      },
      "id": 1
    }
    """;

  String postURL = "https://api.random.org/json-rpc/4/invoke";

  protected void postData(  ) {
    try {
      URL url = new URL( postURL );
      HttpURLConnection urlcon =
          (HttpURLConnection) url.openConnection(  );
      urlcon.setRequestMethod("POST");
      urlcon.setRequestProperty("Content-type", "application/json");
      urlcon.setDoOutput(true);
      urlcon.setDoInput(true);
      OutputStreamWriter out = new OutputStreamWriter(
          urlcon.getOutputStream(  ), UTF_8);
      PrintWriter pout = new PrintWriter( out, true );
      pout.print( jsonReq );
      pout.flush();
      pout.close();

      // Did the post succeed?
      if ( urlcon.getResponseCode() == HttpURLConnection.HTTP_OK )
        System.out.println("Posted ok!");
      else {
        System.out.println("Bad post...");
        return;
      }
      // Hooray! Go ahead and read the results...
      InputStreamReader in = new InputStreamReader(urlcon.getInputStream(), UTF_8);
      BufferedReader br = new BufferedReader(in);
      String line = br.readLine();
      while (line != null) {
        System.out.println(line);
        line = br.readLine();
      }
      br.close();
    } catch (MalformedURLException e) {
      System.out.println(e);     // bad postURL
    } catch (IOException e2) {
      System.out.println(e2);    // I/O error
    }
  }

  public static void main( String [] args ) {
    NetworkInt netInt = new NetworkInt();
    netInt.postData();
  }
}
