package spaceblaster.game;

public class Game {
  public static void main(String args[]) {
    SpaceShip ship = new SpaceShip();
    Planetoid planet = new Planetoid();

    ship.fireOn(planet);
  }
}
