package ch04.examples;

import java.util.ArrayList;

public class EnhancedForDemo {
  public static void main(String args[]) {
    int [] arrayOfInts = new int [] { 1, 2, 3, 4 };
    int total = 0;

    for( int i  : arrayOfInts ) {
      System.out.println( i );
      total = total + i;
    }
    System.out.println("Total: " + total);

    // ArrayList is a popular collection class
    // Chapter 7 discusses collections in more detail
    ArrayList<String> list = new ArrayList<String>();
    list.add("foo");
    list.add("bar");

    for( String s : list )
      System.out.println( s );
  }
}
