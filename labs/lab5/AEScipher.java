/**
 * file: AEScipher
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 5
 * due date: March 17, 2019
 * version: 1.2
 *
 * This file contains the declaration of the AEScipher class
 *
 */

import java.util.Arrays;
import java.util.Collections;

/**
 * AEScipher
 *
 * This class uses the AES cipher to create 11 round keys from a 128-bit key.
 *
 */
class AEScipher {

  // SBOX is the Rijndael substitution box used in the AES algorithm.
  private static char[] SBOX = {
          0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5,
          0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
          0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0,
          0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
          0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC,
          0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
          0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A,
          0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
          0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0,
          0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
          0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B,
          0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
          0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85,
          0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
          0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5,
          0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
          0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17,
          0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
          0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88,
          0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
          0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C,
          0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
          0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9,
          0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
          0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6,
          0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
          0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E,
          0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
          0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94,
          0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
          0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68,
          0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
  };

  // INVERSESBOX is the Rijndael substitution box used in the AES decryption
  // algorithm.
  private static char[] INVERSESBOX = {
          0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38,
          0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
          0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87,
          0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
          0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d,
          0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
          0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2,
          0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
          0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16,
          0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
          0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda,
          0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
          0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a,
          0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
          0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02,
          0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
          0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea,
          0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
          0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85,
          0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
          0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89,
          0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
          0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20,
          0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
          0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31,
          0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
          0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d,
          0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
          0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0,
          0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
          0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26,
          0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d};
  // RCON is the Rijndael key schedule used to expand the KeyHex into round keys
  private static char[] RCON = {
          0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40,
          0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
          0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a,
          0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
          0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25,
          0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
          0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08,
          0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
          0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6,
          0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
          0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61,
          0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
          0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01,
          0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
          0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e,
          0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
          0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4,
          0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
          0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8,
          0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
          0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d,
          0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
          0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91,
          0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
          0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d,
          0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
          0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c,
          0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
          0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa,
          0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
          0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66,
          0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
  };

  // mixMultiplier is the matrix that holds the hexadecimal pairs to be used in
  // mixing the columns
  private static char[][] MIXMULTIPLIER = {{0x02, 0x03, 0x01, 0x01},
                                           {0x01, 0x02, 0x03, 0x01},
                                           {0x01, 0x01, 0x02, 0x03},
                                           {0x03, 0x01, 0x01, 0x02}};

  private static char[][] INVERSEMIXMULTIPLIER = {{0x0e, 0x0b, 0x0d, 0x09},
                                                  {0x09, 0x0e, 0x0b, 0x0d},
                                                  {0x0d, 0x09, 0x0e, 0x0b},
                                                  {0x0b, 0x0d, 0x09, 0x0e}};
  /**
   * aesRoundKeys
   *
   * This function takes the key provided by the user to create 11 round keys.
   *
   * Parameters:
   *  KeyHex: The 128-bit key containing 16 hexadecimal pairs.
   *
   * Return Value:
   *  roundKeys: The 11 round keys created from the key.
   *
   */
  static String[] aesRoundKeys(String KeyHex) {

    int numOfKeys = 11;

    // Create array to hold the 11 round keys
    String[] roundKeys = new String[numOfKeys];

    // Create array to hold the 16 hexadecimal pairs
    char[] key = new char[16];

    // Break the key into different pairs and store them as characters.
    for (int counter = 0; counter < KeyHex.length() / 2; counter++) {
      key[counter] = (char) Integer.parseInt(
              KeyHex.substring(2 * counter, 2 * counter + 2),
              16);
    }

    // Create 2D array to hold all the computed rounds
    char[][] w = new char[4][44];

    // Copy the first round of values to the W array
    for (int col = 0; col <= 3; col++) {
      for (int row = 0; row <= 3; row++) {
        w[row][col] = key[4 * col + row];
      }
    }

    // j represents the number of cols
    int j = 4;

    while (j < 44) {
      if (j % 4 != 0) {
        // a) Compute w(j) = w(j-4) XOR w(j-1) if column is not a multiple of 4
        for (int col = 0; col <= 2; col++) {
          for (int row = 0; row <= 3; row++) {
            w[row][j] = (char) (w[row][j - 4] ^ w[row][j - 1]);
          }
          j++;
        }
      } else if (j % 4 == 0) {
        // b) Start new round
        // i) Store elements of the previous column in a temporary array
        char[] tempArray = new char[4];
        for (int row = 0; row <= 3; row++) {
          tempArray[row] = w[row][j - 1];
        }

        // ii) Left shift elements of temporary array
        char firstElement = tempArray[0];
        tempArray = new char[]{
                tempArray[1], tempArray[2], tempArray[3], firstElement};

        // iii) Transform elements of temporary array using substitution box
        for (int row = 0; row <= 3; row++) {
          tempArray[row] = aesSbox(tempArray[row]);
        }

        // iv) Retrieve the rconValue constant for the current round
        char rconValue = aesRcon(j / 4);

        // v) Perform XOR operation on the first element of the temporary array
        //    using the round constant
        tempArray[0] = (char) (tempArray[0] ^ rconValue);

        // vi) Set w(j) = w(j-4) XOR tempArray
        for (int row = 0; row <= 3; row++) {
          w[row][j] = (char) (w[row][j - 4] ^ tempArray[row]);
        }
        j++;
      }
    }

    // Construct the key from the w array, converting it back to hexadecimal
    // form and setting it to uppercase.
    StringBuilder builtKey = new StringBuilder();
    for (int col = 0; col <= 43; col++) {
      for (int row = 0; row <= 3; row++) {
        builtKey.append(String.format("%02X", (int)w[row][col]));
      }
    }

    // The number of characters in each key is 32, 16 pairs of characters
    for (int keyNumber = 0; keyNumber <= 10; keyNumber++){
      roundKeys[keyNumber] =
              builtKey.toString()
                      .toUpperCase()
                      .substring(keyNumber * 32, keyNumber * 32 + 32);
    }

    return roundKeys;
  }

  /**
   * aesSbox
   *
   * This function takes the character provided and returns the corresponding
   * substitution box value from the SBOX array.
   *
   * Parameters:
   *  inHex: an ASCII character that corresponds to a hexadecimal pair derived
   *         from the user provided key.
   *
   * Return Value:
   *  SBOX[convertedHexToInt]: The hexadecimal value retrieved from the
   *                           substitution box SBOX
   *
   */
  private static char aesSbox(char inHex){
    int convertedHexToInt = (int)inHex;
    return SBOX[convertedHexToInt];
  }

  /**
   * aesSbox
   *
   * This function takes the character provided and returns the corresponding
   * substitution box value from the SBOX array.
   *
   * Parameters:
   *  inHex: an ASCII character that corresponds to a hexadecimal pair derived
   *         from the user provided key.
   *
   * Return Value:
   *  SBOX[convertedHexToInt]: The hexadecimal value retrieved from the
   *                           substitution box SBOX
   *
   */
  private static char aesInverseSbox(char inHex){
    int convertedHexToInt = (int)inHex;
    return INVERSESBOX[convertedHexToInt];
  }

  /**
   * aesRcon
   *
   * This function takes the character provided and returns the corresponding
   * round constant value from the RCON array.
   *
   * Parameters:
   *  round: The round number. (A 128 bit Key has 11 rounds in total)
   *
   * Return Value:
   *  RCON[round]: The round constant that corresponds to the round number.
   *
   */
  private static char aesRcon(int round){
    return RCON[round];
  }

  /**
   * aesStateXOR
   *
   * This function takes each byte of the state and combines it with a byte of
   * the roundkey using bitwise XOR. This can be used for decryption as well,
   * because if a character is XORed by the same character twice, it remains the
   * same.
   *
   * Parameters:
   *  sHex: The 4x4 state matrix containing hexadecimal pairs
   *  keyHex: The 4x4 key matrix containing hexadecimal pairs
   *
   * Return Value:
   *  outStateHex: The resulting 4x4 subkey
   *
   */
  private static char[][] aesStateXOR(char[][] sHex, char[][] keyHex){
    char[][] outStateHex = new char[4][4];
    for(int col = 0; col < outStateHex.length; col++){
      for(int row = 0; row < outStateHex[col].length; row++){
        outStateHex[row][col] =
                (char) (sHex[row][col] ^ keyHex[row][col]);
      }
    }
    return outStateHex;
  }

  /**
   * aesNibbleSub
   *
   * This function takes each byte of the state and substitutes it using the
   * Rijndael Sbox. This allows the cipher to be non-linear.
   *
   * Parameters:
   *  inStateHex: The 4x4 state matrix containing hexadecimal pairs
   *
   * Return Value:
   *  outStateHex: The resulting 4x4 state matrix with substituted bytes
   *
   */
  static char[][] aesNibbleSub(char[][] inStateHex){
    char[][] outStateHex = new char[4][4];
    for(int col = 0; col < outStateHex.length; col++) {
      for (int row = 0; row < outStateHex[col].length; row++) {
        outStateHex[row][col] = aesSbox(inStateHex[row][col]);
      }
    }
    return outStateHex;
  }

  /**
   * aesInverseNibbleSub
   *
   * This function takes each byte of the state and substitutes it using the
   * Rijndael Sbox. This allows the cipher to be non-linear. This method is the
   * inverse of aesNibbleSub and is used in decryption of AES
   *
   * Parameters:
   *  inStateHex: The 4x4 state matrix containing hexadecimal pairs
   *
   * Return Value:
   *  outStateHex: The resulting 4x4 state matrix with substituted bytes
   *
   */
  static char[][] aesInverseNibbleSub(char[][] inStateHex){
    char[][] outStateHex = new char[4][4];
    for(int col = 0; col < outStateHex.length; col++) {
      for (int row = 0; row < outStateHex[col].length; row++) {
        outStateHex[row][col] = aesInverseSbox(inStateHex[row][col]);
      }
    }
    return outStateHex;
  }

  /**
   * aesShiftRow
   *
   * This function takes each row and shifts it by an amount. Row 0 is
   * not shifted, and each subsequent row n is shifted to the left by n.
   *
   * Parameters:
   *  inStateHex: The 4x4 state matrix containing hexadecimal pairs
   *
   * Return Value:
   *  outStateHex: The resulting 4x4 state matrix with shifted rows
   *
   */
  static char[][] aesShiftRow(char[][] inStateHex) {
    char[][] outStateHex = new char[4][4];
    int rowShift = 0;
    outStateHex[0] = inStateHex[0];
    for (int col = 0; col < outStateHex.length; col++) {
      for (int row = 1; row < outStateHex[col].length; row++) {
        int col_counter = row + rowShift;
        if (col_counter > 3) {
          col_counter -= 4;
        }
        outStateHex[row][col] = inStateHex[row][col_counter];
      }
      rowShift++;
    }
    return outStateHex;
  }

  /**
   * aesInverseShiftRow
   *
   * This function takes each row and shifts it by an amount right. Row 0 is
   * not shifted, and each subsequent row n is shifted to the right by n.
   *
   * Parameters:
   *  inStateHex: The 4x4 state matrix with shifted rows
   *
   * Return Value:
   *  outStateHex: The resulting 4x4 state matrix with rows shifted back
   *
   */
   static char[][] aesInverseShiftRow(char[][] inStateHex) {
    char[][] outStateHex = new char[4][4];
    int rowShift = 0;
    outStateHex[0] = inStateHex[0];
    for (int col = 0; col < outStateHex.length; col++) {
      for (int row = 1; row < outStateHex[col].length; row++) {
        int col_counter = row + rowShift;
        if (col_counter > 3) {
          col_counter -= 4;
        }
        outStateHex[row][col_counter] = inStateHex[row][col];
      }
      rowShift++;
    }
    return outStateHex;
  }


  /**
   * aesMixColumn
   *
   * This function performs the mix column operation of AES to transform the
   * state.
   *
   * Parameters:
   *   inStateHex: The 4x4 state matrix containing hexadecimal pairs
   *
   * Return Value:
   *   outStateHex: The resulting 4x4 state matrix with mixed columns
   *
   */
  static char[][] aesMixColumn(char[][] inStateHex){
    char[][] outStateHex = new char[4][4];
    for(int i = 0; i < inStateHex.length; i++){
      for(int j = 0; j < inStateHex[i].length; j++){
        char result;
        result = (char)(mixMult(inStateHex[0][i], MIXMULTIPLIER[j][0]) ^
                        mixMult(inStateHex[1][i], MIXMULTIPLIER[j][1]) ^
                        mixMult(inStateHex[2][i], MIXMULTIPLIER[j][2]) ^
                        mixMult(inStateHex[3][i], MIXMULTIPLIER[j][3]));
        outStateHex[j][i] = result;
      }
    }
    return outStateHex;
  }

  /**
   * aesInverseMixColumn
   *
   * This function performs the mix column operation of AES to transform the
   * state. This is the inverse of the aesMixColumn method.
   *
   * Parameters:
   *   inStateHex: The 4x4 state matrix containing hexadecimal pairs
   *
   * Return Value:
   *   outStateHex: The resulting 4x4 state matrix with mixed columns
   *
   */
  static char[][] aesInverseMixColumn(char[][] inStateHex){
    char[][] outStateHex = new char[4][4];
    for(int i = 0; i < inStateHex.length; i++){
      for(int j = 0; j < inStateHex[i].length; j++){
        char result;
        result = (char)(mixMult(inStateHex[0][i], INVERSEMIXMULTIPLIER[j][0]) ^
                mixMult(inStateHex[1][i], INVERSEMIXMULTIPLIER[j][1]) ^
                mixMult(inStateHex[2][i], INVERSEMIXMULTIPLIER[j][2]) ^
                mixMult(inStateHex[3][i], INVERSEMIXMULTIPLIER[j][3]));
        outStateHex[j][i] = result;
      }
    }
    return outStateHex;
  }
  /**
   * mixMult
   *
   * This function performs the multiplication in the Galois Fields for
   * 1, 2, 3, 9, 11, 13, and 14
   *
   * Parameters:
   *   inHexPair: The pair of hexadecimal characters to be mixed.
   *   mixMultiplier: The value of the multiplier derived from the mix column
   *   matrix.
   *
   * Return Value:
   *   outHexPair: The pair of mixed hexadecimal values.
   *
   */
  private static char mixMult(char inHexPair, char mixMultiplier){
    char outHexPair = inHexPair;

    if(mixMultiplier == 0x02 || mixMultiplier == 0x03) {

      outHexPair = mixMult2(outHexPair);

      // If multiplier is 0x03 we can XOR the original pair with the new pair
      // calculated by using the multiplier 0x02
      if(mixMultiplier == 0x03){
        outHexPair = (char)(outHexPair ^ inHexPair);
      }
    }else if(mixMultiplier == 0x0e){
      outHexPair = (mixMult2((char)(mixMult2((char) (mixMult2(outHexPair) ^ inHexPair)) ^ inHexPair)));
    }else if(mixMultiplier == 0x0d){
      outHexPair = (char)((mixMult2(mixMult2((char) (mixMult2(outHexPair) ^ inHexPair)))) ^ inHexPair);
    }else if(mixMultiplier == 0x0b){
      outHexPair = (char)((mixMult2((char)(mixMult2(mixMult2(inHexPair)) ^ inHexPair))) ^ inHexPair);
    }else if(mixMultiplier == 0x09){
      outHexPair = (char)(mixMult2(mixMult2(mixMult2(inHexPair))) ^ inHexPair);
    }
    return outHexPair;
  }

  /**
   * mixMult2
   *
   * This function handles all multiplications by 2 in the Galois Fields.
   *
   * Parameters:
   *    inHexPair: The pair of hexadecimal characters to be mixed.
   *    mixMultiplier: The value of the multiplier derived from the mix column
   *    matrix.
   *
   * Return Value:
   *    outHexPair: The pair of mixed hexadecimal values.
   */
  static char mixMult2(char inHexPair){
    char outHexPair = inHexPair;

    // Check if the left most bit of the char is one
    boolean leftMostBitIsOne = ((inHexPair & 10000000) == 128);

    // Left shift the pair and remove the most significant bit.
    outHexPair = (char) (255 & (outHexPair << 1));

    // If the leftmost bit is one, we XOR the pair with 00011011.
    if (leftMostBitIsOne) {
      outHexPair = (char) (outHexPair ^ 0x1b);
    }

    return outHexPair;
  }

  /**
   * AES
   *
   * This function takes the plaintext message and key provided by the user and
   * encrypts the message.
   *
   * Parameters:
   *   pTextHex: The plaintext represented in hexadecimal form stored in a
   *   string.
   *   keyTextHex: The key represented in hexadecimal form stored in a string.
   *
   * Return Value:
   *   stringBuilder.toString(): The cipher text presented in hexadecimal form.
   *
   */
  static String AES(String pTextHex, String keyTextHex){
    String[] roundKeysHex = aesRoundKeys(keyTextHex);
    char[][] keyHex = new char[4][4];
    char[][] sHex = new char[4][4];
    int numberOfRounds = 11;

    // Fill in state matrix
    for (int col = 0; col < sHex.length; col++) {
      for (int row = 0; row < sHex.length; row++) {
        String tempPair = pTextHex.substring((2 * (4 * col + row)),
                                            (2 * (4 * col + row) + 2));
        sHex[row][col] = (char) Integer.parseInt(tempPair, 16);
      }
    }

    for(int i = 0; i < numberOfRounds; i++) {

      // Place the round key in a 2D array of chars
      for (int col = 0; col < keyHex.length; col++) {
        for (int row = 0; row < keyHex.length; row++) {
          String tempPair = roundKeysHex[i].substring((2 * (4 * col + row)),
                                                     (2 * (4 * col + row) + 2));
          keyHex[row][col] = (char) Integer.parseInt(tempPair, 16);
        }
      }

      // Depending on the current round, perform operations on the state matrix.
      if(i == 0) {
        sHex = aesStateXOR(sHex, keyHex);
      }else if(i == numberOfRounds - 1){
        sHex = aesNibbleSub(sHex);
        sHex = aesShiftRow(sHex);
        sHex = aesStateXOR(sHex, keyHex);
      } else {
        sHex = aesNibbleSub(sHex);
        sHex = aesShiftRow(sHex);
        sHex = aesMixColumn(sHex);
        sHex = aesStateXOR(sHex, keyHex);
      }
    }

    // Build the final cipher text being outputted.
    StringBuilder stringBuilder = new StringBuilder();
    for (int col = 0; col < keyHex.length; col++) {
      for (int row = 0; row < keyHex.length; row++) {
        stringBuilder.append(String.format("%02X", (int)(sHex[row][col])));
      }
    }

    return stringBuilder.toString();
  }

  /**
   * AESDecrypt
   *
   * This function takes the cipherText message and key provided by the user and
   * decrypts the message.
   *
   * Parameters:
   *   cTextHex: The cipherText represented in hexadecimal form stored in a
   *   string.
   *   keyTextHex: The key represented in hexadecimal form stored in a string.
   *
   * Return Value:
   *   stringBuilder.toString(): The plainText presented in hexadecimal form.
   *
   */
  static String AESDecrypt(String cTextHex, String keyTextHex){
    String[] roundKeysHex = aesRoundKeys(keyTextHex);
    Collections.reverse(Arrays.asList(roundKeysHex));
    char[][] keyHex = new char[4][4];
    char[][] sHex = new char[4][4];
    int numberOfRounds = 11;

    // Fill in state matrix
    for (int col = 0; col < sHex.length; col++) {
      for (int row = 0; row < sHex.length; row++) {
        String tempPair = cTextHex.substring((2 * (4 * col + row)),
                (2 * (4 * col + row) + 2));
        sHex[row][col] = (char) Integer.parseInt(tempPair, 16);
      }
    }

    for(int i = 0; i < numberOfRounds; i++) {

      // Place the round key in a 2D array of chars
      for (int col = 0; col < keyHex.length; col++) {
        for (int row = 0; row < keyHex.length; row++) {
          String tempPair = roundKeysHex[i].substring((2 * (4 * col + row)),
                  (2 * (4 * col + row) + 2));
          keyHex[row][col] = (char) Integer.parseInt(tempPair, 16);
        }
      }

      if(i == 0){
        sHex = aesStateXOR(sHex, keyHex);
        sHex = aesInverseShiftRow(sHex);
        sHex = aesInverseNibbleSub(sHex);
      }else if(i == numberOfRounds - 1){
        sHex = aesStateXOR(sHex, keyHex);
      }else{
        sHex = aesStateXOR(sHex, keyHex);
        sHex = aesInverseMixColumn(sHex);
        sHex = aesInverseShiftRow(sHex);
        sHex = aesInverseNibbleSub(sHex);
      }

    }
    // Build the final cipher text being outputted.
    StringBuilder stringBuilder = new StringBuilder();
    for (int col = 0; col < keyHex.length; col++) {
      for (int row = 0; row < keyHex.length; row++) {
        stringBuilder.append(String.format("%02X", (int)(sHex[row][col])));
      }
    }

    return stringBuilder.toString();
  }
}
