package ch08.exercises;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Starting with the HelloJava3 class as the basis for this
 * new variation. For this exercise, you need to process the
 * command-line arguments for the message and its initial
 * position on the screen.
 */
public class HelloChapter8 extends JFrame {

    public static void main(String[] args) {
        String msg = "Hello, utilities!";
        int x = 150;
        int y = 150;
        HelloChapter8 demo = new HelloChapter8(msg, x, y);
        demo.setVisible(true);
    }

    public HelloChapter8(String msg, int x, int y) {
        super("Chapter 8, Exercise 1");
        add(new HelloComponent8(msg, x, y));
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize(300, 300);
    }

    class HelloComponent8 extends JComponent {
        String theMessage;
        int messageX = 125, messageY = 95; // Coordinates of the message

        public HelloComponent8(String message, int x, int y) {
            theMessage = message;
            messageX = x;
            messageY = y;
            addMouseMotionListener(new MouseMotionListener() {
                public void mouseDragged(MouseEvent e) {
                    messageX = e.getX();
                    messageY = e.getY();
                    repaint();
                }

                public void mouseMoved(MouseEvent e) { }
            });
        }

        public void paintComponent( Graphics g ) {
            g.drawString( theMessage, messageX, messageY );
        }
    }
}
