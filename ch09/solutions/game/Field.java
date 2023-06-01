package ch09.solutions.game;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/**
 * The playing field for our game. Now we can setup some constants for
 * other game classes to use and create member variables for our player and some trees.
 */
public class Field extends JComponent {
  public static final float GRAVITY = 9.8f;  // feet per second per second
  public static final int STEP = 30;   // duration of an animation frame in milliseconds
  public static final int APPLE_SIZE_IN_PIXELS = 30;
  public static final int TREE_WIDTH_IN_PIXELS = 60;
  public static final int TREE_HEIGHT_IN_PIXELS = 2 * TREE_WIDTH_IN_PIXELS;
  public static final int PHYSICIST_SIZE_IN_PIXELS = 75;
  public static final int MAX_TREES = 12;
  public static final int MAX_HEDGES = 8;

  Color fieldColor = Color.LIGHT_GRAY;
  Random random = new Random();

  Physicist physicist;
  List<Tree> trees = new ArrayList<>();
  List<Hedge> hedges = new ArrayList<>();
  List<Apple> apples = new ArrayList<>();
  boolean animating = false;
  Thread animationThread;

  protected void paintComponent(Graphics g) {
    g.setColor(fieldColor);
    g.fillRect(0,0, getWidth(), getHeight());
    for (Tree t : trees) {
        t.draw(g);
    }
    for (Hedge h : hedges) {
        h.draw(g);
    }
    physicist.draw(g);
    for (Apple a : apples) {
      a.draw(g);
    }
  }

  public void setPlayer(Physicist p) {
    physicist = p;
  }

  public void addTree(int x, int y) {
    Tree tree = new Tree();
    tree.setPosition(x,y);
    trees.add(tree);
  }

  public void addHedge(int x, int y) {
    Hedge hedge = new Hedge();
    hedge.setPosition(x,y);
    hedges.add(hedge);
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
      }
    }
  }

  void cullFallenApples() {
    Iterator<Apple> iterator = apples.iterator();
    while (iterator.hasNext()) {
      Apple a = iterator.next();
      if (a.getPositionY() > 600) {
        System.out.println("Culling apple");
        iterator.remove();
      }
    }
    if (apples.size() <= 0) {
      animating = false;
    }
  }

  void startAnimation() {
    // Platform threads
    animationThread = new Thread(new Animator());
    animationThread.start();

    // Virtual threads
    // Remember you need Java 19 or higher to use virtual threads.
    // In addition to the correct version of Java, make sure your
    // IDE has Preview Feature support enabled. We've left this line
    // commented out so that this class still compiles with older
    // versions. When you're ready, comment out the platform thread
    // lines above and uncomment this line:
    //animationThread = Thread.startVirtualThread(new Animator());
  }

  class Animator implements Runnable {
    public void run() {
      // "animating" is a global variable that allows us
      // to stop animating and conserve resources
      // if there are no active apples to move
      while (animating) {
        System.out.println("Stepping " + apples.size() +
            " apples");
        for (Apple a : apples) {
          a.step();
        }
        // Reach back to our outer class instance to repaint
        Field.this.repaint();
        // And get rid of any apples on the ground
        cullFallenApples();
        try {
          Thread.sleep(STEP);
        } catch (InterruptedException ie) {
          System.err.println("Animation interrupted");
          animating = false;
        }
      }
    }
  }

}
