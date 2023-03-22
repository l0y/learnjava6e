package ch11.examples;

import java.util.Random;
import java.util.stream.Stream;
import java.util.function.Supplier;

public class WeekDayGenerator implements Supplier<String> {
  private static String[] days = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
  private Random randSrc = new Random();

  public String get() {
    return days[randSrc.nextInt(days.length)];
  }

  public static void main(String args[]) {
    Stream.generate(new WeekDayGenerator())
          .limit(5)
          .forEach(System.out::println);
  }
}

