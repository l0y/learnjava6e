package ch12.solutions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator2 extends JFrame implements ActionListener {
  // Calculator UI elements
  JLabel display;
  String buttonLabels = "789/456*123-.0=+";
  Font displayFont = new Font(Font.MONOSPACED, Font.BOLD, 36);
  Font buttonFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

  // Calculator state helpers
  boolean startNewNumber = true;
  boolean startNewExpression = true;
  double operand1 = 0.0;
  double operand2 = 0.0;
  String operator = "+";

  public Calculator2() {
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

  double evaluate(double num1, String op, double num2) {
    switch(op) {
      case "+":
        return num1 + num2;
      case "-":
        return num1 - num2;
      case "*":
        return num1 * num2;
      case "/":
        if (num2 == 0.0) return 0.0;
        return num1 / num2;
    }
    return 0.0;
  }

  void updateDisplay(double val) {
    SwingUtilities.invokeLater(() -> display.setText(String.valueOf(val)));
  }

  void handleNumber(String num) {
    // Is this a new operand? If yes, replace the display.
    // Otherwise, append to it.
    if (startNewNumber) {
      display.setText(num);
    } else {
      display.setText(display.getText() + num);
    }
    startNewNumber = false;
  }

  double getOperand() {
    String raw = display.getText();
    double val = 0.0;
    try {
      val = Double.parseDouble(raw);
    } catch (NumberFormatException nfe) {
      System.err.println("Current display is not a valid number: " + raw);
      val = 0.0;
    }
    return val;
  }

  void handleOperator(String op) {
    if (startNewExpression) {
      // New expression, so just store the currently displayed number
      operand1 = getOperand();
    } else {
      // Not a new expression, so reduce on the previous operator
      // before storing the new one
      operand1 = evaluate(operand1, operator, getOperand());
    }
    operator = op;
    startNewExpression = false;
    startNewNumber = true;
    updateDisplay(operand1);
  }

  void showResult() {
    double result = evaluate(operand1, operator, getOperand());
    startNewExpression = true;
    startNewNumber = true;
    updateDisplay(result);
  }

  public void actionPerformed(ActionEvent ae) {
    String label = ae.getActionCommand();
    switch(label) {
      case "0":
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
      case "8":
      case "9":
      case ".":
        handleNumber(label);
        break;
      case "+":
      case "-":
      case "*":
      case "/":
        handleOperator(label);
        break;
      case "=":
        showResult();
        break;
    }
    // No default case; just ignore impossible inputs
  }

  public static void main(String args[]) {
    Calculator2 calc2 = new Calculator2();
    SwingUtilities.invokeLater(() -> calc2.setVisible(true));
  }
}