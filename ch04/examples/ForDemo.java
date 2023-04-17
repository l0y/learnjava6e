package ch04.examples;

public class ForDemo {
  public static void main(String args[]) {
    System.out.println("Count up from 0 to 99:");
    for ( int i = 0; i < 100; i++ ) {
      System.out.println( i );
      int j = i;
      // do any other work needed
    }

    System.out.println("\nGenerate some coordinates:");
    for (int x = 0, y = 10; x < y; x++, y-- ) {
      System.out.println(x + ", " + y);
      // do other stuff with our new (x, y)...
    }

    System.out.println("\nCount from 0 to 9, but skip 5 using a continue statement:");
    for ( int i = 0; i < 10; i++ ) {
      if ( i == 5 )
        continue;
      System.out.println( i );
    }

  }
}
