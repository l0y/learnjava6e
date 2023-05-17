package com.loyinc.quiz;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.net.URL;

import com.loyinc.util.*;

public class QuizPane extends JPanel {
  JLabel titleLabel = new JLabel("Chapter Title");
  QuestionPane questionPane = new QuestionPane();
  JPanel buttonPane = new JPanel(new BorderLayout(5, 2));
  CWButton prevButton = new CWButton(150, "Previous");
  CWButton nextButton = new CWButton(150, "Next");
  JButton checkButton = new JButton("Check My Answer");
  CardLayout introMgr = new CardLayout();
  JPanel introQuestionPane = new JPanel(introMgr);
  JPanel introPane = new JPanel(new BorderLayout());
  JTextPane introText = new JTextPane();

  int chNum = 0; // No chapter selected/Root node selected
  int qNum = 0;
  int qCount = 0;
  Chapter currentChapter;
  Question currentQuestion;

  QuizTree navTree;
  DefaultTreeModel navModel;
  TreeSelectionModel navSelect;

  public QuizPane() {
    super(new BorderLayout(8,8));
    this.setBackground(Color.WHITE);
    this.setOpaque(true);
    this.setBorder(BorderFactory.createEmptyBorder(5,8,5,8));
    buildButtonPane();
    titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
    introText.setFont(new Font("Arial", Font.BOLD, 16));
    URL iconUrl = ClassLoader.getSystemClassLoader().getResource("res/cover-480.png");
    JLabel iconLabel = new JLabel(new ImageIcon(iconUrl), JLabel.LEFT);
    iconLabel.setBackground(Color.WHITE);
    iconLabel.setOpaque(true);
    introPane.add(introText, BorderLayout.CENTER);
    introPane.add(iconLabel, BorderLayout.SOUTH);

    questionPane.setQuizPane(this);
    introQuestionPane.add(questionPane, "questions");
    introQuestionPane.add(introPane, "intro");
    introMgr.show(introQuestionPane, "intro");

    this.add(BorderLayout.NORTH, titleLabel);
    this.add(BorderLayout.CENTER, introQuestionPane);
    this.add(BorderLayout.SOUTH, buttonPane);
  }

  void buildButtonPane() {
    prevButton.addActionListener((e) -> showPreviousQuestion());
    nextButton.addActionListener((e) -> showNextQuestion());
    checkButton.addActionListener((e) -> checkCurrentAnswer());
    buttonPane.add(BorderLayout.WEST, prevButton);
    buttonPane.add(BorderLayout.EAST, nextButton);
    buttonPane.add(BorderLayout.CENTER, checkButton);
    syncButtonStates();
  }

  /**
   * If question navigation is tied to a JTree, store its root and
   * use its selection model to keep the selected question in sync.
   *
   * @param tree The navigation tree to track
   */
  public void attachNavTree(QuizTree tree) {
    this.navTree = tree;
    this.navModel = (DefaultTreeModel)navTree.getModel();
    this.navSelect = navTree.getSelectionModel();
    navTree.setSelectionRow(0);
  }

  /**
   * Sync the Question text areas, include the question itself,
   * the question number, and the various answers. We also update
   * the chapter title just to be safe.
   */
  void syncDisplay() {
    titleLabel.setText(currentChapter.title);
    introMgr.show(introQuestionPane, (qNum == 0) ? "intro" : "questions");
  }

  void syncTree() {
    if (navSelect != null) {
      // We have a tree, select the correct path;
      // updates to the question pane will follow
      int cIndex = currentChapter.number - 1;
      int qIndex = qNum - 1;
      DefaultMutableTreeNode root = (DefaultMutableTreeNode)navModel.getRoot();
      DefaultMutableTreeNode chap = (DefaultMutableTreeNode)root.getChildAt(cIndex);
      DefaultMutableTreeNode ques = (DefaultMutableTreeNode)chap.getChildAt(qIndex);
      TreePath path = new TreePath(new DefaultMutableTreeNode[] { root, chap, ques });
      navSelect.setSelectionPath(path);
    }
  }

  /**
   * Sync the intra-quiz navigation button availability with the current question.
   * On the chapter intro page, you cannot go to a previous question.
   * On the chapter intro page, there is also no question to check.
   * On the final page, we don't allow jumping to the next chapter, so the
   * next button should be disabled.
   */
  void syncButtonStates() {
    //System.out.println("Syncing buttons: " + qNum + " of " + qCount);
    prevButton.setEnabled(qNum > 1);
    checkButton.setEnabled(qNum > 0 && !currentQuestion.isUnanswered());
    nextButton.setEnabled(qNum < qCount);
  }

  void showNextQuestion() {
    if (qNum < qCount) {
      qNum++;
      syncTree();
    }
  }

  void showPreviousQuestion() {
    if (qNum > 0) {
      qNum--;
      syncTree();
    }
  }

  void checkCurrentAnswer() {
    if (currentQuestion.isCorrect()) {
      questionPane.explanation.setText("Correct! " + currentQuestion.getExplanation());
    } else {
      questionPane.explanation.setText("Not quite. " + currentQuestion.getExplanation());
    }
    navTree.repaint();
}

  public void setQuestion(Question qu) {
    this.currentQuestion = qu;
    this.qNum = qu.getNumber();
    if (qNum == 0) {
      introText.setText(qu.getQuestion().replace("\\n", "\n"));
    } else {
      questionPane.setQuestion(qu);
    }
    syncDisplay();
    syncButtonStates();
  }

  public void setQuestion(int qNum) {
    Question qu = currentChapter.getQuestion(qNum);
    if (qu != null) {
      // Only update the display if a valid question was requested (and found)
      setQuestion(qu);
    } else {
      System.err.println("Requested invalid question: " + currentChapter.number + "." + qNum);
    }
  }

  public void setChapter(Chapter ch) {
    this.currentChapter = ch;
    this.qCount = currentChapter.getQuestionList().size() - 1;
  }
}
