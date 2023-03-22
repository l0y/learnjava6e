package ch02.solutions;

import javax.swing.*;

/**
 * A simple variation on the HelloJava class.
 * Added the step to quit when the window is closed.
 * (See GoodbyeWorld.java for the commandline version.)
 */
public class GoodbyeJava {
  public static void main( String[] args ) {
    JFrame frame = new JFrame( "Exercise: Goodbye, Java" );
    JLabel label = new JLabel("Goodbye, Java!", JLabel.CENTER );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.add(label);
    frame.setSize( 300, 200 );
    frame.setVisible( true );
  }
}
