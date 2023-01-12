package com.loyinc.quiz;

import com.loyinc.util.StateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Chapters contain quiz questions as well as basic details on the
 * chapter itself such as the chapter number and its title.
 */
public class Chapter implements StateProvider {

  // States for visual rendering
  public static final int UNOPENED = 0;
  public static final int ALL_CORRECT = 1;
  public static final int NOT_QUITE = 2;
  public static final int STARTED = 3;

  static String[] collapsedNames = { "ch_open", "ch_done_ok", "ch_not_ok", "ch_list_inv" };
  static String[] expandedNames = { "ch_filled", "ch_done_ok", "ch_not_ok", "ch_list" };

  List<Question> questionList = new ArrayList<>();
  String title;
  int number;
  String label;
  String introduction;

  public Chapter(int num, String title) {
    this.number = num;
    this.title = title;
    if (num == 0) {
      label = title;
    } else {
      label = "Chapter " + num;
    }
  }

  public List<Question> getQuestionList() { return questionList; }

  public void addQuestion(Question q) {
    questionList.add(q);
  }

  public Question getQuestion(int which) {
    if (which >= 0 && which < questionList.size()) {
      return questionList.get(which);
    }
    return null;
  }

  public boolean isCorrect() {
    boolean correct = true;
    for (Question q : questionList) {
      correct = correct && q.isCorrect();
    }
    return correct;
  }

  public boolean isWrong() {
    // We only report "wrong" if all questions have been answered and
    // at least one of those questions is incorrect
    boolean result = true;
    for (Question q : questionList) {
      result = result && !q.isUnanswered();
    }
    if (result) {
      // Ok, we know all the questions are answered, no check for mistakes
      for (Question q : questionList) {
        if (!q.isCorrect()) {
          // We answered at least one incorrectly, so we can return true
          return true;
        }
      }
    }
    return false;
  }

  public boolean isUnanswered() {
    boolean unanswered = true;
    for (Question q : questionList) {
      unanswered = unanswered && q.isUnanswered();
    }
    return unanswered;
  }

  public String toString() {
    return label;
  }

  @Override
  public int getState() {
    // Are we Chapter 0? If so, special case
    if (number == 0) return STARTED;      // Entire review is just always "started"
    if (isCorrect()) return ALL_CORRECT;  // Every question is correct
    if (isWrong()) return NOT_QUITE;      // Every question is answered, but not all correctly
    if (isUnanswered()) return UNOPENED;  // Every question is unanswered
    return STARTED;                       // Everything else (questions in various states)
  }

  @Override
  public String getStateText() {
    // Chapters don't change their title depending on their status
    return label;
  }

  @Override
  public String getStateIconName(boolean expanded) {
    if (expanded) {
      return expandedNames[getState()];
    } else {
      return collapsedNames[getState()];
    }
  }
}
