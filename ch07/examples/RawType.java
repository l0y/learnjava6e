package ch07.examples;

import java.util.*;

public class RawType {
  public static void main(String args[]) {
    List list = new ArrayList();
    list.add("foo");

    String[] sa = {"hi", "there"};
    List<String> ls = Arrays.asList(sa);
  }
}
