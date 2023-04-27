package com.loyinc.quiz;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;

public class QuizFrame extends JFrame implements TreeSelectionListener {
  QuizTree nav;
  QuizPane qPane;
  JProgressBar qProgress;

  public QuizFrame(List<Chapter> chapterList) {
    super("Learning Java 6 Review");
    setSize(960,600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create the left side with the chapter/quiz tree and the progress bar
    if (chapterList == null) {
      nav = new QuizTree(); // rely on dummy data to test tree rendering
    } else {
      nav = new QuizTree(chapterList);
    }
    nav.addTreeSelectionListener(this);
    qProgress = new JProgressBar();
    JPanel leftPane = new JPanel(new BorderLayout(0,5));
    JScrollPane tsp = new JScrollPane(nav);
    tsp.setMinimumSize(nav.getMinimumSize());
    tsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    leftPane.add(BorderLayout.CENTER, tsp);
    leftPane.add(BorderLayout.SOUTH, qProgress);

    // Create the right side that will display individual questions
    qPane = new QuizPane();
    qPane.attachNavTree(nav);

    // And finally assemble them together with a resizable splitPane
    JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, qPane);
    setContentPane(jsp);
  }

  public void valueChanged(TreeSelectionEvent tse) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
    Object obj = node.getUserObject();
    // One of three possibilities for our object: root, node, leaf
    if (obj instanceof Chapter) {
      // Same type of user object for both the root and all chapter nodes
      Chapter ch = (Chapter) obj;
      qPane.setChapter(ch);  // For the root, this chapter object will not have any questions
      qPane.setQuestion(0);  // Question #0 shows the introductory text for the chapter
    } else if (obj instanceof Question) {
      // Must be a question/leaf. Grab the chapter from the parent node
      // and the page number from this leaf
      Question qu = (Question) obj;
      DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
      Chapter ch = (Chapter)parent.getUserObject();
      qPane.setChapter(ch);
      qPane.setQuestion(qu);
    } else {
      System.out.println("Unknown node selected: " + obj);
    }
  }
}
