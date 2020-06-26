/**
 * file: Driver_lab3a
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: lab 3a
 * due date: February 17, 2019
 * version: 1.0
 *
 * This file contains the declaration of the Driver_lab3a class
 *
 */

import java.util.Scanner;

/**
 * Driver_lab3a
 *
 * This class uses the algorithm of cofactor expansion along the first row in
 * order to find the determinant of an n by n matrix.
 * NOTE: If the determinant of the matrix and modulo m are relatively prime,
 * the matrix is invertible.
 *
 */
public class Driver_lab3a {

  /**
   * main
   *
   * This function takes the given input from the user and finds the determinant
   * based on the user-provided modulo m, number of rows n, and values for
   * filling the matrix.
   *
   */
  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);

    // Load in modulo m
    int m = scanner.nextInt();

    // Load in number of rows and columns in the matrix
    int n = scanner.nextInt();
    int[][] A = new int[n][n];
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        A[i][j] = scanner.nextInt();
      }
    }
    System.out.println(cofModDet(m, A));
    scanner.close();
  }

  /**
   * cofModDet
   *
   * This function takes the modulo m and matrix A provided by the user
   * and recursively solves for the determinant using 3 cases:
   *
   * Case 1: For n = 1, det(A) is simply a, since A = [a].
   *
   * Case 2: For n = 2, det(A)= a11a22 − a12a21, where A = [a11 a12][a21 a22]
   *
   * Case 3: For n ≥ 3,
   * det(A)= a11det(A11)−a12det(A12)+a13det(A13)− · · · + (−1)n+1a1ndet(A1n),
   * where Aij is the (n − 1) × (n − 1) submatrix that results when the i-th row
   * and the j-th column are removed from the original matrix A.
   *
   * Parameters:
   *   m: The modulo provided by the user
   *   A: an n by n matrix, whose values have been provided by the user.
   *
   * Return value: The determinant of the n by n matrix and modulo m.
   *
   */
  static int cofModDet(int m, int[][] A){
    int result = 0;

    // if operation = 1 we add, -1 we subtract
    int operation = 1;
    // Case 1:
    if(A.length == 1){
      result = A[0][0];

      // Case 2:
    }else if (A.length == 2){
      int tempResult = Math.floorMod(A[0][0], m) * Math.floorMod(A[1][1], m) -
                        Math.floorMod(A[0][1], m) * Math.floorMod(A[1][0], m);
      result = tempResult;

      // Case 3:
    }else{
      for(int i = 0; i < A.length; i++){
        int[][] B = new int[A.length - 1][A.length - 1];
        for(int j = 1; j < A.length; j++){
          for(int k = 0; k < A.length; k++){
            if(k > i){
              B[j - 1][k - 1] = A[j][k];
            }else if(i > k){
              B[j - 1][k] = A[j][k];
            }
          }
        }

        int num;
        if (A[0][i] < 0) {
          num = A[0][i] % m + m;
        }else{
          num = A[0][i] % m;
        }
        long tempResult = operation * ((num * cofModDet(m, B)) % m);
        result += tempResult;
        operation *= -1;
      }
    }

    if (result < 0){
      result = result % m + m;
    }else{
      result %= m;
    }

    return result;
  }
}
