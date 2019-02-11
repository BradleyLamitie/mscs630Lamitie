/**
 * file: Driver_lab2b
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 2
 * due date: February 10, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab2b class
 */

import java.util.Arrays;
import java.util.Scanner;

/**
 * Driver_lab1
 *
 * This class uses the Extended Euclidean Algorithm to pass in two integers,
 * a and b, to find d, x, and y such that d = gcd(a,b) and d = ax + by.
 */
public class Driver_lab2b {

  /**
   * main
   *
   * This function takes the given input from the user, runs it through the
   * euclidAlgExt function and prints the greatest common divisor, x, and y.
   *
   */
  public static void main (String args[]){
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLong()){
      long a = scanner.nextLong();
      long b = scanner.nextLong();
      long[] resultSet = euclidAlgExt(a,b);
      System.out.println(resultSet[0] + " " + resultSet[1] + " "
              + resultSet[2]);
    }
  }

  /**
   * euclidAlgExt
   *
   * This function takes two numbers provided by the user and iteratively solves
   * for the greatest common divisor, x, and y to satisfy the equation
   * d = ax + by where d is the greatest common divisor between a and b.
   *
   * Parameters:
   *   a: The first number provided by the user.
   *   b: The second number provided by the user.
   *
   * Return value: An array containing the greatest common divisor between the
   * two provided numbers, as well as the x and y to satisfy the equation above.
   */
  private static long[] euclidAlgExt(long a, long b){
    long[] a_set = {a, 1, 0};
    long[] b_set = {b, 0, 1};
    long[] temp_set = new long[3];

    while(b_set[0] > 0) {
      long quotient = (long)Math.floor(a_set[0] / b_set[0]);
      for (int i=0; i<temp_set.length; i++) {
        temp_set[i] = a_set[i] - b_set[i] * quotient;
      }
      a_set = Arrays.copyOf(b_set, b_set.length);
      b_set = Arrays.copyOf(temp_set, temp_set.length);
    }

    return a_set;
  }

}