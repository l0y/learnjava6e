package ch05.solutions;

import java.util.*;

public class Zoo {

  abstract class Animal {
    public abstract void speak();
  }

  class Lion extends Animal {
    public void speak() {
      System.out.print("roar");
    }
  }

  class Gibbon extends Animal {
    public void speak() {
      System.out.print("hoot");
    }
  }

  public void listen() {
    Lion lion = new Lion();
    Gibbon gibbon = new Gibbon();
    System.out.println("Let's listen to some animals!");
    System.out.print("The lion goes \"");
    lion.speak();
    System.out.println("\"");
    System.out.print("The gibbon goes \"");
    gibbon.speak();
    System.out.println("\"");
  }

  public static void main(String args[]) {
    Zoo myZoo = new Zoo();
    myZoo.listen();
  }
}


