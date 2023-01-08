package ch07;

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
    public static final int STEP = 40;   // duration of an animation frame in milliseconds
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
}
