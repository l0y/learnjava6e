package com.loyinc.quiz;

public class Answer {
  String answer;
  String explanation;
  boolean correct;
  boolean selected;

  public Answer(String answer) {
    this(answer, "", false);
  }

  public Answer(String answer, boolean correct) {
    this(answer, "", correct);
  }

  public Answer(String answer, String explanation, boolean correct) {
    this.answer = answer;
    this.explanation = explanation;
    this.correct = correct;
    this.selected = false;
  }

  public boolean matches() {
    return correct == selected;
  }
}
