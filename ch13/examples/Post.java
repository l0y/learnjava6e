package ch13.examples;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A small graphical application that demonstrates use of the
 * HTTP POST mechanism. Provide a POST-able URL to the command line
 * and use the "Post" button to send sample name and password
 * data to the URL.
 * 
 * See the servlet section of this chapter for the ShowParameters
 * example that can serve (ha!) as the receiving (server) side.
 */
public class Post extends JPanel implements ActionListener {
  JTextField nameField;
  JPasswordField passwordField;

  // The Postman app is a fantastic tool for testing web service APIs.
  // https://www.postman.com/
  // Postman also runs a very handy (free!) echo service that can show
  // you the content and metadata of basic web requests.
  String postURL = "https://postman-echo.com/post";

  GridBagConstraints constraints = new GridBagConstraints(  );
  
  void addGB( Component component, int x, int y ) {
    constraints.gridx = x;  constraints.gridy = y;
    add ( component, constraints );
  }

  public Post() {
    setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
    JButton postButton = new JButton("Post");
    postButton.addActionListener( this );
    setLayout( new GridBagLayout(  ) );
    constraints.fill = GridBagConstraints.HORIZONTAL;
    addGB( new JLabel("Name ", JLabel.TRAILING), 0, 0 );
    addGB( nameField = new JTextField(20), 1, 0 );
    addGB( new JLabel("Password ", JLabel.TRAILING), 0, 1 );
    addGB( passwordField = new JPasswordField(20), 1, 1 );
    constraints.fill = GridBagConstraints.NONE;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.EAST;
    addGB( postButton, 1, 2 );
  }

  public void actionPerformed(ActionEvent e) {
    postData(  );
  }

  protected void postData(  ) {
    StringBuilder sb = new StringBuilder();
    String pw = new String(passwordField.getPassword());
    try {
      sb.append( URLEncoder.encode("Name", "UTF-8") + "=" );
      sb.append( URLEncoder.encode(nameField.getText(), "UTF-8") );
      sb.append( "&" + URLEncoder.encode("Password", "UTF-8") + "=" );
      sb.append( URLEncoder.encode(pw, "UTF-8") );
    } catch (UnsupportedEncodingException uee) {
      System.out.println(uee);
    }
    String formData = sb.toString(  );

    try {
      URL url = new URL( postURL );
      HttpURLConnection urlcon =
          (HttpURLConnection) url.openConnection(  );
      urlcon.setRequestMethod("POST");
      urlcon.setRequestProperty("Content-type",
          "application/x-www-form-urlencoded");
      urlcon.setDoOutput(true);
      urlcon.setDoInput(true);
      PrintWriter pout = new PrintWriter( new OutputStreamWriter(
          urlcon.getOutputStream(  ), "8859_1"), true );
      pout.print( formData );
      pout.flush(  );

      // Did the post succeed?
      if ( urlcon.getResponseCode() == HttpURLConnection.HTTP_OK )
        System.out.println("Posted ok!");
      else {
        System.out.println("Bad post...");
        return;
      }
      // Hooray! Go ahead and read the results
      InputStream is = urlcon.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
      br.close();
    } catch (MalformedURLException e) {
      System.out.println(e);     // bad postURL
    } catch (IOException e2) {
      System.out.println(e2);    // I/O error
    }
  }

  public static void main( String [] args ) {
    JFrame frame = new JFrame("SimplePost");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add( new Post(), "Center" );
    frame.pack();
    frame.setVisible(true);
  }
}
