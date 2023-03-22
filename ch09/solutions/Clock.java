package ch09.solutions;

import java.awt.*;
import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock extends JFrame {
  JLabel clockLabel;
  Thread runner;
  DateTimeFormatter hms = DateTimeFormatter.ofPattern("hh:mm:ss");

  public Clock() {
    super("Java Clock");
    setSize(400,200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    clockLabel = new JLabel("00:00:00", JLabel.CENTER);
    clockLabel.setFont(new Font("Arial", Font.BOLD, 24));
    getContentPane().add(clockLabel, BorderLayout.CENTER);
  }

  public void startClock() {
    runner = new Thread(new Runnable() {
      public void run() {
        while (true) {
          String time = hms.format(LocalTime.now());
          clockLabel.setText(time);
          clockLabel.repaint();
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ie) {
            clockLabel.setText("--:--:--");
            break;
          }
        }
      }
    });
    runner.start();
  }

  public static void main(String args[]) {
    Clock c = new Clock();
    c.setVisible(true);
    c.startClock();
  }
}
