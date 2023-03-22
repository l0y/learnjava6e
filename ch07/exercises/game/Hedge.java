package ch07.exercises.game;

import java.awt.*;

/**
 * Another obstacle for our game. Hedges are simple boxes.
 * We'll reuse the tree size constants for our hedges.
 */
public class Hedge implements GamePiece {
    int x, y;

    // Drawing helpers
    private Color hedgeColor = Color.GREEN.darker();
    private int hedgeWidth = Field.TREE_WIDTH_IN_PIXELS;
    private int hedgeHeight = (int)(hedgeWidth / 2);

    // Boundary helpers
    private Rectangle boundingBox;

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        boundingBox = new Rectangle(x, y, hedgeWidth, hedgeHeight);
    }

    @Override
    public int getPositionX() {
        return x;
    }

    @Override
    public int getPositionY() {
        return y;
    }

    @Override
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(hedgeColor);
        g.fillRect(x, y, hedgeWidth, hedgeHeight);
    }

    @Override
    public boolean isTouching(GamePiece otherPiece) {
	// We don't really have any collisions to detect yet, so just return "no".
	// As we fill out all of the game pieces, we'll come back to this method
	// and provide a more useful response.
	return false;
    }
}
