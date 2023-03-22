package ch06.solutions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * An upgraded graphical application with interactivity!
 * Use this template to add assertions to guarantee our
 * message displays itself in a visible part of the window.
 */
public class HelloZero {
  public static void main( String[] args ) {
    JFrame frame = new JFrame( "Chapter 6, Exercise 2" );
    frame.add( new HelloComponent0("Hello, Zero!") );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setSize( 300, 300 );
    frame.setVisible( true );
  }
}

/*
 * Our custom component to display a message and move
 * it around as the user drags their mouse. Hopefully
 * more of the extends/implements details make sense
 * now that you've worked with them more in Chapters
 * 5 and 6.
 */
class HelloComponent0 extends JComponent implements MouseMotionListener {
  String theMessage;
  int messageX = -125, messageY = 95; // Coordinates of the message

  /**
   * Create a new component that can draw its message at an arbitrary position.
   * That position can be changed by dragging the mouse; we attach a listener
   * to pick up those drag events.
   */
  public HelloComponent0( String message ) {
    theMessage = message;
    addMouseMotionListener(this);
    assert messageX > 0 : "X coordinate is too small";
    assert messageY > 0 : "Y coordinate is too small";
  }

  public void paintComponent( Graphics g ) {
    g.drawString( theMessage, messageX, messageY );
  }

  public void mouseDragged(MouseEvent e) {
    // Save the mouse coordinates and paint the message.
    messageX = e.getX();
    messageY = e.getY();
    repaint();
  }

  public void mouseMoved(MouseEvent e) { 
    // Ignore simple movements
  }
}
