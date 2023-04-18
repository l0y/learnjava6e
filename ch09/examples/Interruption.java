package ch09.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Interruption extends JFrame implements MouseListener {

  Thread fiveSeconds;
  JLabel message;

  // label bounds
  int labelX = 10;
  int labelY = 20;
  int labelW = 120;
  int labelH = 20;

  // frame size and edges (including a margin based on label bounds)
  int frameW = 400, frameH = 200;
  int margin = 10;
  int left = margin, right = frameW - labelW - margin;
  int top = margin, bottom = frameH - labelH - margin - 32; // 32 for average title bar

  public Interruption() {
    super("interrupt() Demo");
    setSize(frameW, frameH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);

    // Setup our label and give it an initial size and location
    message = new JLabel ("Pausing...");
    message.setBounds(labelX, labelY, labelW, labelH);
    add(message);

    // And make sure our mouse clicks get heard
    addMouseListener(this);
  }

  public static void main(String args[]) {
    Interruption i = new Interruption();
    i.setVisible(true);
    i.startPause();
  }

  public void moveMessage() {
    // Keep our message visible within the frame using the range formula from ch8
    //int randomValue = min + (int)(Math.random() * (max - min));
    labelX = left + (int)(Math.random() * (right - left));
    labelY = top + (int)(Math.random() * (bottom - top));
    message.setLocation(labelX, labelY);
    repaint();
  }

  public void startPause() {
    fiveSeconds = new Thread(new Runnable() {
      public void run() {
        while (true) {
          try {
            moveMessage();
            Thread.sleep(5000);
            message.setForeground(Color.BLACK);
            message.setText("Done. Pausing...");
          } catch (InterruptedException ie) {
            message.setForeground(Color.RED);
            message.setText("Interrupted!");
          }
        }
      }
    });
    fiveSeconds.start();
  }

  public void mouseClicked(MouseEvent e) {
    fiveSeconds.interrupt();
  }

  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
}
