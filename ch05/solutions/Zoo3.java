package ch05.solutions;

import java.util.*;

public class Zoo3 {

  abstract class Animal {
    public abstract void speak();
    public abstract String getSpecies();
  }

  class Lion extends Animal {
    public void speak() {
      System.out.print("roar");
    }
    public String getSpecies() {
      return "lion";
    }
  }

  class Gibbon extends Animal {
    public void speak() {
      System.out.print("hoot");
    }
    public String getSpecies() {
      return "gibbon";
    }
  }

  class Seal extends Animal {
    public void speak() {
      System.out.print("bark");
    }
    public String getSpecies() {
      return "seal";
    }
  }

  public void listen() {
    System.out.println("Let's listen to our menagerie!");
    Animal[] menagerie = { new Lion(), new Gibbon(), new Seal() };
    for (Animal animal : menagerie) {
      System.out.print("The ");
      System.out.print(animal.getSpecies());
      System.out.print(" goes \"");
      animal.speak();
      System.out.println("\"");
    }
  }

  public static void main(String args[]) {
    Zoo3 myZoo = new Zoo3();
    myZoo.listen();
  }
}


