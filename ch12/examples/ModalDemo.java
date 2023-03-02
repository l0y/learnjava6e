package ch12.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// dynamic form
// - pre-populated entries for title and message?
// - first dropdown picks between Message, Confirm, Input
// - second dropdown changes based on choice in first,
//   - Message options: different icons: error, warning, info, etc.
//   - Confirm options: choice of buttons: OK/Cancel, Yes/No/Cancel, etc.
//   - Input options: looks like same icon-based options as for Message
// - show dialog button
public class ModalDemo extends JFrame implements ActionListener {
  JTextField titleField;
  JTextField messageField;
  JComboBox<String> modalPicker;
  JComboBox<Option> typePicker;
  JComboBox<Option> buttonPicker;

  // Select which type of dialog to show.
  // JOptionPane has many variations on dialogs, including several we're not
  // tackling in this demo. Check out the docs online to see all the options.
  String[] modalOptions = {
    "Message Dialog",
    "Confirmation Dialog",
    "Input Dialog"
  };

  // All dialogs have a "message type" although the default is usually PLAIN.
  // This choice mostly affects the icon displayed inside the dialog.
  Option[] typeOptions = {
    new Option("Plain Message", JOptionPane.PLAIN_MESSAGE),
    new Option("Error Message", JOptionPane.ERROR_MESSAGE),
    new Option("Info Message", JOptionPane.INFORMATION_MESSAGE),
    new Option("Warning Message", JOptionPane.WARNING_MESSAGE),
    new Option("Question Message", JOptionPane.QUESTION_MESSAGE)
  };

  // The confirmation dialogs can also take an argument that dictates which
  // buttons are shown at the bottom of the dialog. This choice does not
  // apply to message or input dialogs.
  Option[] buttonOptions = {
    new Option("Default Set", JOptionPane.DEFAULT_OPTION),
    new Option("Yes/No", JOptionPane.YES_NO_OPTION),
    new Option("Yes/No/Cancel", JOptionPane.YES_NO_CANCEL_OPTION),
    new Option("OK/Cancel", JOptionPane.OK_CANCEL_OPTION)
  };

  // Pick up any selection changes from the message type combobox.
  // Selections on other comboboxes will be queried as needed. We don't
  // need to track their changes as the occur.
  public void actionPerformed(ActionEvent ae) {
    buttonPicker.setEnabled(modalPicker.getSelectedIndex() == 1);
  }

  // The workhorse. Grab all the form values and use them to show the desired dialog.
  void showModal() {
    String title = titleField.getText();
    String message = messageField.getText();
    int type = ((Option)typePicker.getSelectedItem()).getValue();
    int buttons = ((Option)buttonPicker.getSelectedItem()).getValue();
    switch (modalPicker.getSelectedIndex()) {
      case 0: // message dialog
        JOptionPane.showMessageDialog(this, message, title, type);
        break;
      case 1: // confirmation dialog
        JOptionPane.showConfirmDialog(this, message, title, buttons, type);
        break;
      case 2: // input dialog
        JOptionPane.showInputDialog(this, message, title, type);
        break;
    }
  }

  public ModalDemo() {
    // Setup our frame
    super("Modal Demo");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    titleField = new JTextField("Example Title");
    messageField = new JTextField("Your message here");
    modalPicker = new JComboBox<>(modalOptions);
    modalPicker.addActionListener(this);
    typePicker = new JComboBox<>(typeOptions);
    buttonPicker = new JComboBox<>(buttonOptions);
    buttonPicker.setEnabled(false); // unavailable until user selects confirmation type

    // Setup a "go" button that will show a popup matching the current form selections
    JButton goButton = new JButton("Show Dialog");
    goButton.addActionListener(event -> showModal());

    // for this simple demo, just go with a two-column GridLayout
    JPanel content = new JPanel(new GridLayout(0,2));
    content.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));

    content.add(new JLabel("Dialog Title"));
    content.add(titleField);
    content.add(new JLabel("Message"));
    content.add(messageField);
    content.add(new JLabel("Dialog Type"));
    content.add(modalPicker);
    content.add(new JLabel("Message Type"));
    content.add(typePicker);
    content.add(new JLabel("Buttons"));
    content.add(buttonPicker);
    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(new JLabel(""));
    content.add(goButton);

    getContentPane().add(content, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    ModalDemo demo = new ModalDemo();
    SwingUtilities.invokeLater(() -> demo.setVisible(true));
  }
}

/**
 * Simple helper class to encapsulate a named int value for
 * dispaly with components like JComboBox, JList, and JTree.
 */
class Option {
  private String label;
  private int value;

  public Option(String label, int value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() { return label; }
  public int getValue() { return value; }
  public String toString() { return label; }
}