package ch08.solutions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Starting with the HelloJava3 class as the basis for this
 * new variation. For this exercise, you need to process the
 * command-line arguments for the message and its initial
 * position on the screen.
 */
public class HelloChapter8 extends JFrame {
    static String niceDate() {
      DateTimeFormatter nice = DateTimeFormatter.ofPattern("LLL d, yyyy");
      return nice.format(LocalDate.now());
    }

    static int safeSpot() {
      // return a number between 1 and 300 (our arbitrary window size)
      return (int)(Math.random() * 300) + 1;
    }

    public static void main(String[] args) {
        String msg = "Hello, utilities!";
        int x = 150;
        int y = 150;
        if (args.length > 0 && args.length < 3) {
	  // At least one argument, so there's a message
          if (args[0].equalsIgnoreCase("today")) {
            msg = niceDate();
          } else {
            msg = args[0];
          }
          // Do we have coordinates?
          if (args.length == 2) {
            if (Pattern.matches("r(andom|and|ndm)?", args[1])) {
              // Got the random position keyword
              x = safeSpot();
              y = safeSpot();
            } else {
              // It should be a pair of numbers separated by
              // comma and optional whitespace
              String[] coords = args[1].split("\\s*,\\s*");
              if (coords.length == 2) {
                try {
                  x = Integer.parseInt(coords[0]);
                  y = Integer.parseInt(coords[1]);
                } catch (NumberFormatException nfe) {
                  System.err.println("Invalid coordinates; using defaults");
                  x = 150;
                  y = 150;
                }
              } else {
                System.err.println("Wrong number of coordinates; using defaults");
              }
            }
          }
        } else if (args.length > 2) {
          // Too many args; print and error and exit
          System.err.println("Too many arguments. Max is two: name and location.");
          System.exit(0);
	      }
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
