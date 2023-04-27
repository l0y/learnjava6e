package com.loyinc.quiz;

import com.loyinc.util.Config;
import com.loyinc.util.StateProviderCellRenderer;

import javax.swing.tree.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizTree extends JTree {
  final static int MARGIN = 8;
  final static int rHeight = 24;
  final static int qCount = 100;
  final static Insets GAPPED_INSETS = new Insets(MARGIN,MARGIN,MARGIN,MARGIN);
  final static Dimension thin = new Dimension(200, rHeight * qCount);
  final static Dimension thick = new Dimension(300, rHeight * qCount);

  public QuizTree() {
    loadDummyData();
    tailor();
  }

  protected QuizTree(List<Chapter> chapterList) {
    loadChapterData(chapterList);
    tailor();
  }

  protected void tailor() {
    Config.applyPrefs(this);
    this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    StateProviderCellRenderer spcr = new StateProviderCellRenderer();
    String prefix = "res/";
    String suffix = ".png";
    for (int i = 0; i < Question.iconNames.length; i++) {
      spcr.addIcon(prefix + Question.iconNames[i] + suffix);
    }
    for (int i = 0; i < Chapter.collapsedNames.length; i++) {
      spcr.addIcon((prefix + Chapter.collapsedNames[i] + suffix));
      spcr.addIcon((prefix + Chapter.expandedNames[i] + suffix));
    }
    this.setCellRenderer(spcr);
    // And last but not least, tailor for particular operating systems
    // Write once, debug everywhere ;)
    if (Config.isMacOS()) {
      this.setRowHeight(rHeight);
    }
  }

  public Insets getInsets() {
    return GAPPED_INSETS;
  }
  public Dimension getMinimumSize() { return thin; }
  public Dimension getMaximumSize() { return thick; }
  public Dimension getPreferredSize() { return thin; }

  /**
   * Helper method to populate the tree with chapter/question data.
   */
  void loadChapterData(List<Chapter> chapterList) {
    // Grab the first chapter and make it our root node
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(chapterList.get(0));
    // Load the chapters and attach them to our root
    for (int c = 1; c < chapterList.size(); c++) {
      Chapter ch = chapterList.get(c);
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(ch);
      root.add(node);
      // For each chapter, load its questions with the chapter as the branch
      List<Question> qList = ch.getQuestionList();
      for (int i = 1; i < qList.size(); i++) {
        Question q = qList.get(i);
        node.add(new DefaultMutableTreeNode(q));
      }
    }

    DefaultTreeModel dtm = new DefaultTreeModel(root);
    this.setModel(dtm);
  }

  /**
   * Simple helper method to populate the tree with example data for testing.
   */
  void loadDummyData() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Quiz Root");
    DefaultMutableTreeNode ch1 = new DefaultMutableTreeNode("Chapter 1");
    DefaultMutableTreeNode ch2 = new DefaultMutableTreeNode("Chapter 2");
    DefaultMutableTreeNode q11 = new DefaultMutableTreeNode("Question 1");
    DefaultMutableTreeNode q12 = new DefaultMutableTreeNode("Question 2");
    DefaultMutableTreeNode q21 = new DefaultMutableTreeNode("Question 1");
    DefaultMutableTreeNode q22 = new DefaultMutableTreeNode("Question 2");
    DefaultMutableTreeNode q23 = new DefaultMutableTreeNode("Question 3");
    root.add(ch1);
    root.add(ch2);
    ch1.add(q11);
    ch1.add(q12);
    ch2.add(q21);
    ch2.add(q22);
    ch2.add(q23);
    DefaultTreeModel dtm = new DefaultTreeModel(root);
    this.setModel(dtm);
  }
}
