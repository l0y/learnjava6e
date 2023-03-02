package ch12.exercises.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

/**
 * The playing field for our game. Now we can setup some constants for
 * other game classes to use and create member variables for our player and some trees.
 */
public class Field extends JComponent implements ActionListener {

  public static final float GRAVITY = 9.8f; // feet per second per second
  public static final int STEP = 40; // duration of an animation frame in milliseconds
  public static final int APPLE_SIZE_IN_PIXELS = 30;
  public static final int TREE_WIDTH_IN_PIXELS = 60;
  public static final int TREE_HEIGHT_IN_PIXELS = 2 * TREE_WIDTH_IN_PIXELS;
  public static final int PHYSICIST_SIZE_IN_PIXELS = 75;

  public static final int MAX_PHYSICISTS = 5;
  public static final int MAX_APPLES_PER_PHYSICIST = 3;
  public static final int MAX_TREES = 12;

  Color fieldColor = Color.LIGHT_GRAY;

  // Keep track of our own score and also make room for multiple players
  int myScore = 0;
  String[] scores = new String[3];
  JLabel[] scoreLabels = new JLabel[3];

  List<Physicist> physicists = Collections.synchronizedList(new ArrayList<>());
  List<Apple> apples = Collections.synchronizedList(new ArrayList<>());
  List<Tree> trees = Collections.synchronizedList(new ArrayList<>());
  List<Hedge> hedges = Collections.synchronizedList(new ArrayList<>());

  boolean animating = false;
  Thread animationThread;
  Timer animationTimer;

  protected void paintComponent(Graphics g) {
    g.setColor(fieldColor);
    g.fillRect(0, 0, getWidth(), getHeight());
    for (Tree t : trees) {
      t.draw(g);
    }
    for (Hedge h : hedges) {
      h.draw(g);
    }
    for (Physicist p : physicists) {
      p.draw(g);
    }
    for (Apple a : apples) {
      a.draw(g);
    }
  }

  public void actionPerformed(ActionEvent event) {
    if (animating && event.getActionCommand().equals("repaint")) {
      //System.out.println("Timer stepping " + apples.size() + " apples");
      for (Apple a : apples) {
        a.step();
        detectCollisions(a);
      }
      repaint();
      cullFallenApples();
    }
  }

  /**
   * Toss an apple from the given physicist using that physicist's aim and force.
   * Make sure the field is in the animating state.
   *
   * @param physicist the player whose apple should be tossed
   */
  public void startTossFromPlayer(Physicist physicist) {
    if (!animating) {
      System.out.println("Starting animation!");
      animating = true;
      startAnimation();
    }
    if (animating) {
      // Check to make sure we have an apple to toss
      if (physicist.aimingApple != null) {
        Apple apple = physicist.takeApple();
        apple.toss(physicist.aimingAngle, physicist.aimingForce);
        apples.add(apple);
        Timer appleLoader = new Timer(800, physicist);
        appleLoader.setActionCommand("New Apple");
        appleLoader.setRepeats(false);
        appleLoader.start();
      }
    }
  }

  // Removes any apples that have fallen below the screen.
  // For this exercise, you'll need to expand this method to
  // also remove apples and obstacles that have collided.
  void cullFallenApples() {
    Iterator<Apple> iterator = apples.iterator();
    while (iterator.hasNext()) {
      Apple a = iterator.next();
      if (a.getPositionY() > 600) {
        System.out.println("Culling apple");
        iterator.remove();
      }
      // TODO: Check the apple for any collisions. If a collision
      // is detected, remove the apple and the obstacle it hit.
    }
    if (apples.size() <= 0) {
      animating = false;
    }
  }

  void detectCollisions(Apple apple) {
    // TODO: check for collisions with trees, hedges, or other apples
  }

  void hitPhysicist(Physicist physicist) {
    // do any scoring or notifications here
    physicists.remove(physicist);
  }

  void hitTree(Tree tree) {
    // do any scoring or notifications here
    myScore += 10;
    trees.remove(tree);
    setScore(1, String.valueOf(myScore));
  }

  void hitHedge(Hedge hedge) {
    // do any scoring or notifications here
    myScore += 12;
    hedges.remove(hedge);
    setScore(1, String.valueOf(myScore));
  }

  public void resetScore(int playerNumber) {
    myScore = 0;
    setScore(playerNumber, "0");
  }

  public String getScore(int playerNumber) {
    return scores[playerNumber];
  }

  public void setScore(int playerNumber, String score) {
    scores[playerNumber] = score;
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          String newScore = " Player " + playerNumber + ": " + score;
          scoreLabels[playerNumber].setText(newScore);
        }
      }
    );
  }

  void startAnimation() {
    if (animationTimer == null) {
      animationTimer = new Timer(STEP, this);
      animationTimer.setActionCommand("repaint");
      animationTimer.setRepeats(true);
      animationTimer.start();
    } else if (!animationTimer.isRunning()) {
      animationTimer.restart();
    }
  }
}
