package com.loyinc.quiz;

import com.loyinc.util.StateProvider;

public class Question implements StateProvider {
  public static final int MAX_ANSWERS = 4;

  // States for visual rendering. Questions are always leaves, so no
  // need to accommodate open/closed options
  public static final int UNANSWERED = 0;
  public static final int CORRECT = 1;
  public static final int NOT_QUITE = 2;

  static String[] iconNames = { "unknown01", "correct01", "notquite01" };
  int number;
  String question;
  String label;
  String instructions;
  Answer[] answers = new Answer[MAX_ANSWERS];
  int answerCount; // cached count of added answers
  int selectCount; // how many answers can be selected

  public Question(int num, String question) {
    this(num, question, "", 1);
  }

  public Question(int num, String question, String instructions, int maxSelectable) {
    this.number = num;
    this.question = question;
    this.instructions = instructions;
    this.selectCount = maxSelectable;
    label = "Question " + num;
  }

  void addAnswer(Answer a) {
    if (answerCount < MAX_ANSWERS) {
      // We have an arbitrary but hard limit of answers
      answers[answerCount++] = a;
    }
  }

  public int getNumber() { return number; }
  public String getQuestion() { return question; }
  public int getAnswerCount() { return answerCount; }
  public Answer[] getAnswers() { return answers; }

  public Answer getAnswer(int a) {
    if (a >= 0 && a < answerCount) {
      return answers[a];
    }
    return null;
  }

  public void selectAnswer(String buttonLabel, boolean isSelected) {
    char first = buttonLabel.charAt(0);
    selectAnswer(first - 'A', isSelected);
  }

  public void selectAnswer(int which, boolean isSelected) {
    // Ignore any selection that cannot map to one of our answers
    if (which >= 0 && which < answerCount) {
      answers[which].selected = isSelected;
    }
  }

  public boolean isCorrect() {
    // All correct answers must be selected
    // Wrong answers cannot be selected
    boolean correct = true;
    for (int a = 0; a < answerCount; a++) {
      correct = correct && answers[a].matches();
    }
    return correct;
  }

  public boolean isUnanswered() {
    for (int a = 0; a < answerCount; a++) {
      if (answers[a].selected) {
        // If even one answer has been selected, we are no longer unanswered
        return false;
      }
    }
    return true;
  }

  public String getExplanation() {
    String exp = "";
    for (int a = 0; a < answerCount; a++) {
      if (answers[a].selected) {
        exp += answers[a].explanation;
        exp += " ";
      }
    }
    return exp;
  }

  public String toString() {
    return label;
  }

  @Override
  public int getState() {
    if (isUnanswered()) {
      return UNANSWERED;
    }
    return isCorrect() ? CORRECT : NOT_QUITE;
  }

  @Override
  public String getStateText() {
    // Questions don't change their label based on their state
    return label;
  }

  @Override
  public String getStateIconName(boolean expanded) {
    // We're always a leaf, so we can ignore the expanded flag from our tree
    return iconNames[getState()];
  }
}
