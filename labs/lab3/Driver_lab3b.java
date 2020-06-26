/**
 * file: Driver_lab3b
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 3b
 * due date: February 17, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab3b class
 *
 */

import java.util.Scanner;

/**
 * Driver_lab3b
 *
 * This class processes a plaintext, formatting it down to bytes expressed in
 * hexadecimal notation.
 *
 */
public class Driver_lab3b {

  /**
   * main
   *
   * This function takes the given input from the user and converts its
   * characters to hexadecimal form, listing them out vertically in 4 by 4
   * grids.
   *
   */
  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);
    char s = scanner.nextLine().charAt(0);
    String P = scanner.nextLine();

    // Calculate number of extra spaces left over by the 4 by 4 grid and fill
    // the spaces with the designated subtext.
    int numberOfEmptySpaces = 16 - P.length() % 16;
    StringBuilder Ps = new StringBuilder(P);
    for(int i = 0; i < numberOfEmptySpaces; i++){
      Ps.append(s);
    }

    // Print the 4 by 4 blocks row by row.
    for(int i = 0; i < Ps.length(); i += 16){
      int[][] temp = getHexMatP(s, Ps.substring(i, i + 16));
      for(int j = 0; j < 4; j++){
        StringBuilder Ps_row = new StringBuilder();
        for(int k = 0; k < 3; k++){
          Ps_row.append(Integer.toHexString(temp[j][k]).toUpperCase()).append(" ");
        }
        System.out.println(Ps_row + Integer.toHexString(temp[j][3]).toUpperCase());
      }
      System.out.println();
    }
  }

  /**
   * getHexMatP
   *
   * This function takes in no more than 16 characters that are a piece of the
   * plaintext the user provides and places each character in an array.
   * Each character is stored using it's ASCII value in a 4 by 4 grid.
   * If the string is less than 16 characters, we substitute a character for
   * the empty spaces in the grid.
   *
   * Parameters:
   *   s: The character that is used to substitute empty spaces in the 2D array.
   *   p: A string containing no more than 16 characters. This is a piece of the
   *   plaintext provided by the user.
   *
   * Return value: A two-dimensional array containing the 4 by 4 grid of ASCII
   * values.
   *
   */
  private static int[][] getHexMatP(char s, String p){
    int[][] P_matrix = new int[4][4];
    int counter = 0;
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++){
        P_matrix[j][i] = p.charAt(counter);
        counter++;
      }
    }
    return P_matrix;
  }
}
