package ch09.exercises;

import java.awt.*;
import javax.swing.*;

public class Clock extends JFrame {
  JLabel clockLabel;

  public Clock() {
    super("Java Clock");
    setSize(400,200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    clockLabel = new JLabel("00:00:00", JLabel.CENTER);
    clockLabel.setFont(new Font("Arial", Font.BOLD, 24));
    getContentPane().add(clockLabel, BorderLayout.CENTER);
  }

  public void startClock() {
    // Set up your thread here. It should sleep for one second
    // and then update the text in clockLabel.
  }

  public static void main(String args[]) {
    Clock c = new Clock();
    c.setVisible(true);
    c.startClock();
  }
}
