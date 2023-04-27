package com.loyinc.quiz;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A utility class for loading and creating quizzes. Static methods are provided
 * for empty, mock, hard-coded, and load-from-file options.
 */
public class Quizzes {
  public static final String EMPTY = "empty";
  public static final String MOCKUP = "mockup";
  public static final String JAR = "jar";
  public static final String FILE = "file";

  public static List<Chapter> buildList(String mode, String option) {
    return switch (mode) {
      case MOCKUP -> buildMockup();
      case JAR -> buildFromJar(option);
      case FILE -> buildFromFile(option);
      case EMPTY -> new ArrayList<Chapter>();
      default -> new ArrayList<Chapter>();
    };
  }

  static List<Chapter> buildMockup() {
    List<Chapter> clist = new ArrayList<>();
    Chapter intro = new Chapter(0, "Mockup Review");
    intro.addQuestion(new Question(0, "Mock Review Data", "", 0));
    clist.add(intro);
    // Next add 3 chapters, each with 5 questions
    for (int c = 1; c <= 3; c++) {
      Chapter ch = new Chapter(c, "Title for Chapter " + c);
      ch.addQuestion(new Question(0, "Q0 is always the introductory text; no answers present",
          "Click 'Next' to start", 0));
      for (int q = 1; q <= 5; q++) {
        int sel = (int)(Math.random() * 6 + 1);
        if (sel > 3) { sel = 1; } // map "excess" options to single choice so it's the most common
        String inst = sel == 1 ? "(Select one)" : "(Choose up to " + sel + " answers)";
        Question qu = new Question(q, "Text for question #" + q, inst, sel);
        qu.addAnswer(new Answer("First answer", "First context, correct for multiple choice questions", sel > 1));
        qu.addAnswer(new Answer("Second answer", "Second context, always incorrect", false));
        qu.addAnswer(new Answer("Third answer", "Third context, correct for all questions", true));
        qu.addAnswer(new Answer("Fourth answer", "Fourth context, also incorrect", false));
        ch.addQuestion(qu);
      }
      clist.add(ch);
    }
    return clist;
  }

  /**
   * Load a file of key/value pairs (one pair per line) to assemble a quiz.
   * Chapter 0 is the quiz introduction and serves as the root of the
   * quiz/chapter/question tree. Within each chapter, Question 0 is the
   * chapter overview (or Quiz overview in the case of Chapter 0). Question 0
   * does not include any answers. Within each question, up to four answers
   * are allowed.
   *
   * The key has a compact shape, CQArr, where:
   * - C is a base62 (0-9A-Za-z) chapter number
   * - Q is a base62 question number
   * - A is the answer "number", one of A-D or "-" when not applicable
   * - rr is the two-character "role" of this key
   *
   * Currently supported roles:
   * - ti The title of the quiz or chapter
   * - in The introductory text for a chapter
   * - tx The text of the question or answer
   * - se The selection instructions (select one, select all that match, ...)
   * - ex The explanation for why an answer is correct/incorrect
   * - co The (optional) count of selectable answers (default is 1 if left out)
   *
   * @param filename Relative or absolute path to key/value text file
   * @return A completed list of chapters with questions and answers
   */
  static List<Chapter> buildFromFile(String filename) {
    List<String> contents = null;
    try {
      Path filepath = Path.of(filename);
      if (Files.isReadable(filepath)) {
        contents = Files.readAllLines(filepath);
      } else {
        System.err.println("File " + filename + " was not readable. Returning empty list.");
      }
    } catch (IOException ioe) {
      System.err.println("Failed to load quiz from file: " + filename + ". Returning empty list.");
      ioe.printStackTrace();
      contents = null;
    }
    return processContents(contents);
  }

  static List<Chapter> buildFromJar(String rezname) {
    List<String> contents = null;
    try {
      InputStream is = Quizzes.class.getClassLoader().getResourceAsStream(rezname);
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      contents = br.lines().toList();
      br.close();
    } catch (Exception e) {
      System.err.println("Failed to load quiz from Jar resource " + rezname + ". Returning empty list.");
      e.printStackTrace();
      contents = null;
    }
    return processContents(contents);
  }

  static List<Chapter> processContents(List<String> contents) {
    List<Chapter> chList = new ArrayList<>();
    if (contents != null && contents.size() > 0) {
      // Hooray! We successfully loaded the quiz data. Time to process the keys.
      for (String line : contents) {
        if (line.length() < 2 || line.startsWith("#")) { continue; }
        String[] kvp = line.split("\\s+", 2);
        if (kvp.length == 2) {
          processLine(kvp[0], kvp[1], chList);
        } else {
          System.err.println("Skipping malformed line: " + line);
        }
      }
    }
    return chList;
  }

  /**
   * Helper that places a line of quiz information into its appropriate
   * container based on the key at the beginning of the line.
   * @param key Formatted key indicating the location and role of the given value
   * @param val Contents of this entry; form is dependent on the role of this line
   * @param clist Existing chapter list; entries updated or added as needed
   */
  static void processLine(String key, String val, List<Chapter> clist) {
    // First up, decode the indices and separate the role
    int ci = debase62(key.charAt(0));
    int qi = debase62(key.charAt(1));
    char ac = key.charAt(2);
    int ai = switch (ac) {
      case 'A', 'B', 'C', 'D' -> (ac - 'A');
      default -> -1;
    };
    String role = key.substring(3);

    // Now figure out if we need to build any of the path for the key
    Chapter  ch = null;
    Question qu = null;
    Answer   an = null;
    if (ci >= 0 && ci < clist.size()) {
      ch = clist.get(ci);
      if (qi >= 0 && qi < ch.questionList.size()) {
        qu = ch.getQuestion(qi);
        if (ai >= 0 && ai < qu.answerCount) {
          an = qu.getAnswer(ai);
        } else if (ai > -1) {
          // Presumably valid new answer, verify the role is tc or tw
          if (role.equals("tc") || role.equals("tw")) {
            an = new Answer(val, role.equals("tc"));
            qu.addAnswer(an);
            return;
          } else {
            System.err.println("Invalid role for new answer: " + key);
          }
        } else if (ac != '-') {
          // If the answer was not explicitly meant to be ignored, let the user know
          System.err.println("Invalid answer index from key: " + key);
        }
      } else if (qi > -1) {
        // Presumably valid new question, verify the role is appropriate
        // Intro questions begin with "in", normal ones should begin with a tx key
        if (role.equals("tx") || role.equals("in")) {
          qu = new Question(qi, val);
          ch.addQuestion(qu);
          return;
        } else {
          System.err.println("Malformed new question: " + key);
        }
      } else {
        System.err.println("Invalid chapter index from key: " + key);
      }
    } else if (ci > -1){
      // Valid new chapter, verify this is also question 0 and role ti
      if (qi == 0 && (role.equals("ti") || role.equals("tx"))) {
        ch = new Chapter(ci, val);
        clist.add(ch);
        return;
      } else {
        System.err.println("Invalid chapter start: " + key);
      }
    } else {
      System.err.println("Invalid chapter index: " + ci);
    }

    // With a valid container hierarchy ready, figure out what to do with the value
    //  - t* entries should have been handled as part of the container finding process above
    //  - in The introductory text for a chapter
    //  - se The selection instructions (select one, select all that match, ...)
    //  - co The number of selections allowed (must be in [1..4])
    //  - ex The explanation for why an answer is correct/incorrect
    switch (role) {
      case "in":
        ch.introduction = val;
        break;
      case "co":
        qu.selectCount = safeNumber(val, 1, 4);
        break;
      case "se":
        qu.instructions = val;
        break;
      case "ex":
        an.explanation = val;
        break;
      default:
        System.err.println("Unrecognized role in key: " + key);
        break;
    }
  }

  static int debase62(char decodable) {
    if (decodable >= '0' && decodable <= '9') { return (decodable - '0'); }
    if (decodable >= 'A' && decodable <= 'Z') { return (decodable - 'A' + 10); }
    if (decodable >= 'a' && decodable <= 'z') { return (decodable - 'a' + 36); }
    return -1;
  }

  static int safeNumber(String s, int min, int max) {
    int num = min;
    try {
      num = Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      num = min;
    }
    if (num < min) return min;
    if (num > max) return max;
    return num;
  }
}
