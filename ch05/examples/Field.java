package ch05.examples;

import java.awt.*;
import javax.swing.*;

/**
 * A reduced playing field for our game. This class sets up the apples
 * similar to our real game (in the ch05/examples/game folder) but leaves out
 * other details. It is meant for use with the various PrintDetails classes.
 */
public class Field extends JComponent {

  public static final int APPLE_SIZE_IN_PIXELS = 30;

  Color fieldColor = Color.LIGHT_GRAY;

  Apple a1 = new Apple();
  Apple a2 = new Apple();

  public void setupApples() {
    // For now, just play with our apple attributes directly
    a1.diameter = 3.0f;
    a1.mass = 5.0f;
    a1.x = 20;
    a1.y = 40;
    a2.diameter = 8.0f;
    a2.mass = 10.0f;
    a2.x = 70;
    a2.y = 200;
  }

  protected void paintComponent(Graphics g) {
    g.setColor(fieldColor);
    g.fillRect(0, 0, getWidth(), getHeight());
    a1.draw(g);
    a2.draw(g);
  }

  public void detectCollisions() {
    if (a1.isTouching(a2)) {
      System.out.println("Collision detected!");
    } else {
      System.out.println("Apples are not touching.");
    }
  }
}
