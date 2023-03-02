package ch12.solutions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
  JLabel display;
  String buttonLabels = "789/456*123-.0=+";
  Font displayFont = new Font(Font.MONOSPACED, Font.BOLD, 36);
  Font buttonFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

  public Calculator() {
    super("Ex. 12.1");
    setSize(300,450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel content = (JPanel)getContentPane();

    // Set up our display label with a nice, readable font
    display = new JLabel("0", SwingConstants.RIGHT);
    display.setFont(displayFont);
    display.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
    content.add(display, BorderLayout.NORTH);

    // Set up our buttons
    JPanel buttonPane = new JPanel(new GridLayout(4,4));
    for (String label : buttonLabels.split("")) {
      JButton b = new JButton(label);
      b.setFont(buttonFont);
      b.addActionListener(this);
      buttonPane.add(b);
    }
    content.add(buttonPane, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent ae) {
    System.out.println(ae.getActionCommand());
  }

  public static void main(String args[]) {
    Calculator calc = new Calculator();
    SwingUtilities.invokeLater(() -> calc.setVisible(true));
  }
}