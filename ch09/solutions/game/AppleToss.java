package ch09.solutions.game;

import javax.swing.*;

/**
 * Our apple tossing game. This class extends JFrame to create our
 * main application window. We'll be filling this out along the way, 
 * but for now we can setup a field with some trees and our player.
 */
public class AppleToss extends JFrame {

  Field field = new Field();
  Physicist player1 = new Physicist();

  public AppleToss() {
    // Create our frame
    super("Apple Toss Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800,600);
    setResizable(false);

    // Build the field with our player and some trees
    setupFieldForOnePlayer();

  }

  /**
   * A helper method to populate a one player field with target trees.
   * Expanded to include setting up hedges as part of Advanced Exercise 1.
   */
  private void setupFieldForOnePlayer() {
    // Place our (new) physicist in the lower left corner and connect them to the field
    player1.setPosition(100,500);
    field.setPlayer(player1);
    player1.setField(field);
        
    // Make a few trees for target practice
    for (int row = 1; row <= 2; row++) {
      for (int col = 1; col <=3; col++) {
        field.addTree(col * 100, row * 100);
      }
    }

    // And add one row of hedges similar to the trees
    for (int h = 1; h <= 4; h++) {
      field.addHedge(h * 80 + 380, 240);
    }

    add(field);
  }

  /**
   * Helper to (manually) toss an apple with a moderate force at a 45-degree angle
   */
  private void tossDemo() {
    try {
      player1.setAimingAngle(45.0f);
      player1.setAimingForce(25.0f);
      field.repaint();
      Thread.sleep(1000);
      field.startTossFromPlayer(player1);
    } catch (InterruptedException ie) {
      System.err.println("Interrupted during initial pause before tossing an apple.");
    }
  }

  public static void main(String args[]) {
    AppleToss game = new AppleToss();
    game.setVisible(true);
    game.tossDemo();
  }
}
