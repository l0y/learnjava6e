package ch12.examples;

import javax.swing.*;

/**
 * A simple label placed on a frame.
 */
public class HelloJavaAgain {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Hello, Java!" );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 300, 150 );

        JLabel label = new JLabel("Hello, Java!", JLabel.CENTER );
        frame.add(label);

        frame.setVisible( true );
    }
}
