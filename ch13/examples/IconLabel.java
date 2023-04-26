package ch13.examples;

import javax.swing.*;
import java.net.*;

public class IconLabel extends JFrame {
  public IconLabel() {
    super("Web Icon Demo");
    setSize(400,220);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    try {
      URL favUrl = new URL("https://www.oracle.com/asset/web/favicons/favicon-192.png");
      ImageIcon image1 = new ImageIcon(favUrl);
      JLabel iconLabel = new JLabel(image1);
      add(iconLabel);
    } catch (MalformedURLException mfe) {
      add(new JLabel("Error: " + mfe));
    }
  }

  public static void main(String args[]) {
    IconLabel demo = new IconLabel();
    SwingUtilities.invokeLater(() -> demo.setVisible(true));
  }
}