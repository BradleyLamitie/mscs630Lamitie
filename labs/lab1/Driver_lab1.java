/**
 * file: Driver_lab1
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 1
 * due date: February 2, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab1 class
 */

import java.util.Scanner;

/**
 * Driver_lab1
 *
 * This class converts lines of text supplied by the user, encrypts them
 * using a cipher, and prints them out.
 */
public class Driver_lab1 {

  /**
   * main
   *
   * This function takes the given input from the user, runs it through the
   * str2int function and prints the resulting ciphered text.
   *
   */
  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);
    while(scanner.hasNextLine()) {
      for(int converted_number : str2int(scanner.nextLine())) {
        System.out.print(converted_number + " ");
      }
      System.out.println("");
    }
    scanner.close();
  }

  /**
   * str2int
   *
   * This function encrypts the values of characters in a line of text to
   * digits. The function takes the numeric value of letters such that:
   * (A = 0 ... Z = 25, ' ' = 26) and displays them in order.
   *
   * Parameters:
   *   plainText: The line of text to be encrypted using the cipher.
   *
   * Return value: the numeric values of the characters passed into it,
   * according to the cipher.
   */
  private static int[] str2int(String plainText){
    final int SPACE_ASCII_VALUE = 32;
    final int SPACE_CIPHER_VALUE = 26;
    final int A_ASCII_VALUE = 97;
    final String DELIMITER_PATTERN = "";
    int[] ints = new int[plainText.length()];
    Scanner scanner = new Scanner(plainText);
    // The useDelimiter method uses a pattern to break up a string so the
    //  scanner can parse through it.
    scanner.useDelimiter(DELIMITER_PATTERN);
    for(int i = 0; scanner.hasNext(); i++){
      char letter = scanner.next().toLowerCase().charAt(0);
      if(letter == SPACE_ASCII_VALUE){
        ints[i] = SPACE_CIPHER_VALUE;
      }else {
        ints[i] = letter - A_ASCII_VALUE;
      }
    }
    return ints;
  }
}
