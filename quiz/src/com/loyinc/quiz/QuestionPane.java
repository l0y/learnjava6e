package com.loyinc.quiz;

import com.loyinc.util.Config;
import com.loyinc.util.RollingButtonGroup;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class QuestionPane extends JPanel implements ItemListener {
  Question currentQuestion;
  QuizPane quiz;

  JLabel qNumLabel = new JLabel("#. ");
  JTextPane questionText = new JTextPane();
  JCheckBox[] options = new JCheckBox[Question.MAX_ANSWERS];
  JTextPane[] answers = new JTextPane[Question.MAX_ANSWERS];
  JLabel instructions = new JLabel("Select one");
  JTextPane explanation = new JTextPane();
  RollingButtonGroup rbg = new RollingButtonGroup();
  GridBagConstraints gbc = new GridBagConstraints();

  void position(int x, int y, int w, int h, double wx, double wy) {
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = w;
    gbc.gridheight = h;
    gbc.weightx = wx;
    gbc.weighty = wy;
  }

  public QuestionPane() {
    super(new GridBagLayout());
    setOpaque(true);
    setBackground(Color.WHITE);
    // 3 columns (Qnum/Anum/QAtext)
    // 16 rows, 3 each for the question and 4 answers (2 content, 1 gap), one explanation
    Config.applyPrefs(qNumLabel);
    position(0, 0, 1, 1, 0.0, 0.0);
    gbc.anchor = GridBagConstraints.FIRST_LINE_END;
    gbc.fill = GridBagConstraints.NONE;
    add(qNumLabel, gbc);

    position(0, 1, 1, 1, 0.0, 1.0);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.VERTICAL;
    add(Box.createHorizontalStrut(40), gbc);

    position(1, 0, 2, 2, 1.0, 1.0);
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    gbc.fill = GridBagConstraints.BOTH;
    Config.applyPrefs(questionText);
    add(questionText, gbc);

    position(0, 2, 3, 1, 1.0, 0.0);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    instructions.setHorizontalAlignment(JLabel.CENTER);
    instructions.setBorder(BorderFactory.createEmptyBorder(6,0,4,0));
    add(instructions, gbc);

    for (int a = 0; a < Question.MAX_ANSWERS; a++) {
      int row = 3 + 3 * a; // First option starts on "row" 3 of GBL, and each option uses 3 rows
      position(1, row, 1, 1, 0.0, 0.0);
      gbc.anchor = GridBagConstraints.FIRST_LINE_END;
      gbc.fill = GridBagConstraints.NONE;
      options[a] = new JCheckBox((char)('A' + a) + ".");
      options[a].setBackground(Color.WHITE);
      options[a].addItemListener(this);
      Config.applyPrefs(options[a]);
      add(options[a], gbc);
      rbg.add(options[a]);

      position(1, row + 1, 1, 1, 0.0, 1.0);
      gbc.anchor = GridBagConstraints.CENTER;
      gbc.fill = GridBagConstraints.VERTICAL;
      add(Box.createHorizontalStrut(40), gbc);

      position(2, row, 1, 2, 0.0, 1.0);
      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc.fill = GridBagConstraints.BOTH;
      answers[a] = new JTextPane();
      Config.applyPrefs(answers[a]);
      add(answers[a], gbc);

      position(0, row + 2, 3, 1, 1.0, 0.0);
      gbc.anchor = GridBagConstraints.CENTER;
      add(Box.createVerticalStrut(10), gbc);
    }

    position(0, 15, 3, 1, 1.0, 1.0);
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    gbc.fill = GridBagConstraints.BOTH;
    Config.applyPrefs(explanation);
    add(explanation, gbc);
  }

  void setQuizPane(QuizPane qp) {
    quiz = qp;
  }

  public void setQuestion(Question qu) {
    currentQuestion = qu;
    qNumLabel.setText(qu.getNumber() + ". ");
    questionText.setText(qu.getQuestion());
    instructions.setText(qu.instructions);
    explanation.setText("");
    rbg.setRollSize(qu.selectCount > 0 ? qu.selectCount : 1);
    int aCount = currentQuestion.getAnswerCount();
    for (int a = 0; a < options.length; a++) {
      if (a < aCount) {
        Answer an = currentQuestion.getAnswer(a);
        options[a].setEnabled(true);
        options[a].setSelected(an.selected);
        answers[a].setText(an.answer);
      } else {
        options[a].setEnabled(false);
        options[a].setSelected(false);
        answers[a].setText("");
      }
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getSource() instanceof AbstractButton) {
      AbstractButton b = (AbstractButton)e.getSource();
      currentQuestion.selectAnswer(b.getText(), e.getStateChange() == ItemEvent.SELECTED);
    }
    if (quiz != null) {
      // We know our parent quiz, so update its question navigation buttons
      quiz.syncButtonStates();
    }
  }
}
