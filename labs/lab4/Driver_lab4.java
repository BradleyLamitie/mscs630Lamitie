/**
 * file: Driver_lab4
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 4
 * due date: March 3, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab4 class
 *
 */

import java.util.Scanner;

/**
 * Driver_lab4
 *
 * This class uses the AES cipher to create 11 round keys from a 128-bit key.
 *
 */
public class Driver_lab4 {

  /**
   * main
   *
   * This function takes the given input from the user, creates 11 round keys
   * using the AES cipher, and prints each. The inputKey must be a 128-bit key
   * consisting of 16 hexadecimal pairs.
   *
   */
  public static void main(String args[]){
    Scanner scanner = new Scanner(System.in);
    String inputKey = scanner.nextLine();
    scanner.close();

    String[] roundKeysHex = AEScipher.aesRoundKeys(inputKey);

    for(int round = 1; round <= roundKeysHex.length; round ++){
      System.out.println(roundKeysHex[round - 1]);
    }
  }
}
