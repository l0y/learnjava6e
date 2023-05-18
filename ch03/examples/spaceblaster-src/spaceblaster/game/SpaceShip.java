package spaceblaster.game;

public class SpaceShip {
  private String laserSound = "pew";

  public void fireOn(Planetoid target) {
    System.out.print("Firing:");
    for (int i = 0; i < 3; i++) {
      System.out.print(" " + laserSound);
    }
    System.out.println("!\n");
    target.destroy();
  }
}
