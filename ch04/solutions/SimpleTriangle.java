package ch04.solutions;

public class SimpleTriangle {
  public static void main(String args[]) {
    // Create our "triangular" two-dimensional array
    int[][] triangle = new int[5][];

    // Use nested loops to fill it
    for (int i = 0; i < triangle.length; i++) {
        triangle[i] = new int [i + 1];
        for (int j = 0; j < i + 1; j++)
            triangle[i][j] = i + j;
    }

    // And finally, use nested loops to display it
    for (int i = 0; i < triangle.length; i++) {
      for (int j = 0; j < triangle[i].length; j++) {
        System.out.println(triangle[i][j]);
      }
    }
  }
}
