package ch02.exercises;

import javax.swing.*;

/**
 * A simple variation on the HelloJava class.
 * Added the step to quit when the window is closed.
 * (See HelloWorld.java for the commandline version.)
 */
public class HelloJava {
  public static void main( String[] args ) {
    JFrame frame = new JFrame( "Chapter 2, Exercise 1" );
    JLabel label = new JLabel("Hello, Java!", JLabel.CENTER );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.add(label);
    frame.setSize( 300, 200 );
    frame.setVisible( true );
  }
}
