/**
 * file: Driver_lab5
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 5
 * due date: March 17, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab5 class
 *
 */
import java.util.Scanner;

/**
 * Driver_lab5
 *
 * This class uses the AES cipher to allow a user to encrypt a given plaintext
 * with a key also provided by the user.
 *
 */
public class Driver_lab5 {

  /**
   * main
   *
   * This function takes the given key and plaintext provided by the user and
   * passes them through the AES Cipher to yield a ciphertext. The key and
   * plaintext must both be 128 bits and contain 16 hexadecimal pairs.
   *
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String key = scanner.nextLine();
    String state = scanner.nextLine();
    String cTextHex = AEScipher.AESDecrypt(state, key);
    System.out.print(cTextHex);
  }
}
