package com.loyinc.quiz;

import java.util.*;

import javax.swing.SwingUtilities;

public class Review {
  QuizFrame quiz;
  List<Chapter> chapterList = new ArrayList<>();

  // Hard-coded options for now. Could certainly move these to command-line
  // arguments if more quizzes get built.
  // Production, use the bundled quiz content
  String buildMode = Quizzes.JAR;
  String quizFile = "res/quiz-data.txt";

  // Late-stage development, use a file from the local filesystem
  //String buildMode = Quizzes.FILE;
  //String quizFile = "/Users/marc/work/books/examples/learnjava6e/quiz/rez/quiz-data.txt";

  // Early development, use mock data
  //String buildMode = Quizzes.MOCKUP;
  //String quizFile = null;

  public Review() {
    // Follow global switch to pick either mockup data or the real thing
    chapterList = Quizzes.buildList(buildMode, quizFile);
    if (chapterList == null || chapterList.size() == 0) {
      // No errors, but no chapters either; must be in test mode
      quiz = new QuizFrame(null);
    } else {
      quiz = new QuizFrame(chapterList);
    }
  }

  public boolean loadChapters(String quizFile) {
    // Return true on successful load

    return chapterList != null;
  }

  public void start() {
    if (quiz != null) {
      quiz.setVisible(true);
    } else {
      System.err.println("Review quizzes are not yet initialized.");
    }
  }

  public static void main(String[] args) {
    System.setProperty("awt.useSystemAAFontSettings","on");
    System.setProperty("swing.aatext", "true");
    Review r = new Review();
    SwingUtilities.invokeLater(() -> r.start());
  }
}