package ch11.examples;

import java.util.function.DoubleUnaryOperator;

public class Adjuster {
  public static double adjust(double val, DoubleUnaryOperator adjustment) {
    return adjustment.applyAsDouble(val);
  }

  public static void main(String args[]) {
    double sample = 70.2;
    System.out.println("Initial reading: " + sample);
    System.out.print("Adding 3: ");
    System.out.println(adjust(sample, s -> s + 3));
    System.out.print("Reducing by 10%: ");
    System.out.println(adjust(sample, s -> s * 0.9));
  }
}
