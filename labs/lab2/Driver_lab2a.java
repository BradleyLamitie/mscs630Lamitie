/**
 * file: Driver_lab2a
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 2a
 * due date: February 10, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab2a class
 */

import java.util.Scanner;

/**
 * Driver_lab2a
 *
 * This class finds the greatest common divisor of two numbers supplied
 * by the user.
 */
public class Driver_lab2a {

  /**
   * main
   *
   * This function takes the given input from the user, runs it through the
   * euclidAlg function and prints the greatest common divisor.
   *
   */
  public static void main (String args[]){
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLong()){
      long a = scanner.nextLong();
      long b = scanner.nextLong();
      System.out.println(euclidAlg(a,b));
    }
  }

  /**
   * euclidAlg
   *
   * This function takes two numbers provided by the user and iteratively solves
   * for the greatest common divisor.
   *
   * Parameters:
   *   a: The first number provided by the user.
   *   b: The second number provided by the user.
   *
   * Return value: The greatest common divisor between the two provided numbers.
   */
  static long euclidAlg(long a, long b){
    while(a != 0){
      long temp = b;
      b = a;
      a = temp%a;
    }
    return b;
  }
}
