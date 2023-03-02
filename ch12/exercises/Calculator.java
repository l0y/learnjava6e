package ch12.exercises;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

  public Calculator() {
    super("Ex. 12.1");
    setSize(300,450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent ae) {
    System.out.println(ae.getActionCommand());
  }

  public static void main(String args[]) {
    Calculator calc = new Calculator();
    SwingUtilities.invokeLater(() -> calc.setVisible(true));
  }
}