/**
 * file: ImageProcessor.java
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: Project
 * due date: May 12, 2018
 * version: 1.0
 *
 * This file contains the declaration of the ImageProcessor class
 */
package application;

import java.awt.image.BufferedImage;

/**
 * ImageProcessor
 * 
 * This class holds all the methods used to breakdown images into stateHexes,
 * encrypt them, and rebuild them back into images. 
 */
public class ImageProcessor {

	/**
   * imageToHexPixels
   * 
   * This function converts an image into pixels represented by 6 
   * hexadecimal characters
   * 
   * Parameters:
   * 	image: 				The image that the user passed in to the application
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 
   * Return value: An array holding all pixel's RGB values stored in 
   * 							 hexadecimal format.
   */
  private static String[] imageToHexPixels(BufferedImage image, int imageWidth,
  																				 int imageHeight) {
  	
  	// Retrieve RGB data of each pixel
    int[] rgbData = image.getRGB(0, 0, imageWidth, imageHeight, null,
    														 0, imageWidth);
    
    String[] hexPixels = new String[imageHeight * imageWidth];

    // For each pixel, convert the rgb data to hexadecimal form
    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
      	
      	// Get the integer value of the pixel
      	int pixelValue = rgbData[(i * imageWidth) + j];
      	
      	//Retrieve the red value
        int red = (pixelValue >> 16) & 0xFF;
        String redHex = Integer.toHexString(red);
        // If the integer is less than 15 we append a 0 to the beginning of 
        // hex in order to maintain a proper RGB length.
        if(red <= 15){
          redHex = "0" + redHex;
        }

        //Retrieve the green value
        int green = (pixelValue >> 8) & 0xFF;
        String greenHex = Integer.toHexString(green);
        if(green <= 15){
          greenHex = "0" + greenHex;
        }

        //Retrieve the blue value
        int blue = (pixelValue) & 0xFF;
        String blueHex = Integer.toHexString(blue);
        if(blue <= 15){
          blueHex = "0" + blueHex;
        }

        // Construct the hexadecimal pixel value and store it
        hexPixels[(imageWidth * i) + j] = redHex + greenHex + blueHex;
      }
    }

    return hexPixels;
  }

  /**
   * hexPixelsToStateHexes
   * 
   * This function converts an image into pixels represented by 6 
   * hexadecimal characters
   * 
   * Parameters:
   * 	hexPixels: 	An array holding all pixel's RGB values stored in 
   * 							hexadecimal format.		
   * 
   * Return value: An array holding stateHexes
   */
  private static String[] hexPixelsToStateHexes(String[] hexPixels) {
  	
  	// Combine all pixel hex values into a long string
    StringBuilder hexImage = new StringBuilder();
    for (String hexPixel : hexPixels) {
      hexImage.append(hexPixel);
    }
    
    // Calculate how many stateHexes can be made from the pixels 
    int numOfStateHex = (int) Math.ceil(hexImage.length() / 32.0);

    // If the pixels can't be broken down evenly in stateHexes we append 0s
    // to the end of the stateHex for a full 32 characters
    if (hexImage.length() % 32 != 0) {
      int bufferSpaces = 32 - (hexImage.length() % 32);
      for (int i = 0; i < bufferSpaces; i++) {
        hexImage.append("0");
      }
    }

    // Break hexPixels into 128 bit stateHexes
    String[] stateHexes = new String[numOfStateHex];
    for (int i = 0; i < numOfStateHex; i++) {
      stateHexes[i] = hexImage.substring(i * 32, (i * 32) + 32);
    }
    return stateHexes;
  }

  /**
   * encryptStateHexes
   * 
   * This function uses AES to encrypt a stateHex
   * 
   * Parameters:
   * 	stateHexes: An array made up of some of the pixel's RGB values stored in 
   * 							hexadecimal format.	
   *  keyHex:			A 128 bit hexadecimal key used to encrypt the stateHexes	
   * 
   * Return value: An array holding all the encrypted stateHexes
   */
  private static String[] encryptStateHexes(String[] stateHexes,
  																			    String keyHex){
  	// Encrypt each stateHex and store it
    String[] encryptedStateHexes = new String[stateHexes.length];
    for(int i = 0; i < stateHexes.length; i++){
      encryptedStateHexes[i] = AEScipher.AES(stateHexes[i], keyHex);
    }
    return encryptedStateHexes;
  }

  /**
   * decryptStateHexes
   * 
   * This function uses AES to decrypt an encrypted stateHex
   * 
   * Parameters:
   * 	stateHexes: An array made up of some of the pixel's RGB values stored in 
   * 							hexadecimal format.	
   *  keyHex:			A 128 bit hexadecimal key used to decrypt the stateHexes	
   * 
   * Return value: An array holding all the decrypted stateHexes
   */
  private static String[] decryptStateHexes(String[] encryptedStateHexes,
  																					String keyHex){
  	
		// Decrypt each stateHex and store it	
    String[] decryptedStateHexes = new String[encryptedStateHexes.length];
    for(int i = 0; i < encryptedStateHexes.length; i++){
      decryptedStateHexes[i] = AEScipher.AESDecrypt(encryptedStateHexes[i],
      																							keyHex);
    }
    return decryptedStateHexes;
  }

  /**
   * stateHexesToHexPixels
   * 
   * This function breaks the stateHexes back down to pixels stored in 
   * hexadecimal form.
   * 
   * Parameters:
   * 	stateHexes: An array made up of some of the pixel's RGB values stored in 
   * 							hexadecimal format.	
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 
   * Return value: An array holding all the pixels stored in hexadecimal form
   */
  static String[] stateHexesToHexPixels(String[] stateHexes, int imageWidth,
  																			int imageHeight) {
  	
  	// Create a string by combining all statehexes together
    StringBuilder hexString = new StringBuilder();
    String[] hexPixels = new String[imageHeight * imageWidth];

    for (String stateHex : stateHexes) {
      hexString.append(stateHex);
    }

    // Break the hexString into pixels
    for(int i = 0; i < imageHeight * imageWidth; i++){
      hexPixels[i] = hexString.substring(i*6,(i*6)+6);
    }

    return hexPixels;
  }

  /**
   * hexPixelsToImage
   * 
   * This function combines the hexPixels into an image
   * 
   * Parameters:
   * 	hexPixels: 		An array holding each pixel stored in hexadecimal format
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 
   * Return value: The resulting image
   */
  static BufferedImage hexPixelsToImage(String[] hexPixels, int imageWidth,
  																			int imageHeight){
  	
  	// Create the image
    BufferedImage image = new BufferedImage(imageWidth, imageHeight,
    																				BufferedImage.TYPE_INT_RGB);
    
    // Break down each pixel and set the corresponding image's pixel to the 
    // derived value
    for(int i = 0; i < imageHeight; i++){
      for(int j = 0; j < imageWidth; j++){
      	
        int pixelValue = 0;
        
        // Set the red value
        String redHex = hexPixels[(imageWidth * i) + j].substring(0,2);
        int red = Integer.parseInt(redHex, 16);
        pixelValue = pixelValue | (red << 16);

        // Set the green value
        String greenHex = hexPixels[(imageWidth * i) + j].substring(2,4);
        int green = Integer.parseInt(greenHex, 16);
        pixelValue = pixelValue | (green << 8);

        // Set the blue value
        String blueHex = hexPixels[(imageWidth * i) + j].substring(4,6);
        int blue = Integer.parseInt(blueHex, 16);
        pixelValue = pixelValue | blue;
        
        image.setRGB(j,i,pixelValue);
      }
    }
    System.out.println("FINISHED BUILDING IMAGE");
    return image;
  }

  /**
   * imageToStateHexes
   * 
   * This function breaks the image down into stateHexes
   * 
   * Parameters:
   * 	image: 				The image that was passed in by the user
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 
   * Return value: The stateHexes that are derived from the image
   */
  public static String[] imageToStateHexes(BufferedImage image, 
  																					int imageWidth, int imageHeight){
  	
  	// Break the image down into pixels in hexadecimal form
    String[] hexPixels = imageToHexPixels(image, imageWidth, imageHeight);
    
    // Break the pixels into stateHexes
    return hexPixelsToStateHexes(hexPixels);
  }

  /**
   * stateHexesToImage
   * 
   * This function builds the stateHexes into an image
   * 
   * Parameters:
   * 	stateHexes: 	The array of stateHexes to be formed into the image
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 
   * Return value: An image derived from the stateHexes
   */
  private static BufferedImage stateHexesToImage(String[] stateHexes,
  																							 int imageWidth,
  																							 int imageHeight){
    
  	// Convert the stateHexes to pixels
  	String[] encryptedHexPixels = stateHexesToHexPixels(stateHexes, imageWidth,
  																											imageHeight);
    
  	// Convert the pixels to an image
    return hexPixelsToImage(encryptedHexPixels, imageWidth, imageHeight);
  }

  /**
   * encryptImage
   * 
   * This function encrypts an image and returns the encrypted image.
   * 
   * Parameters:
   * 	image: 				The image to be encrypted
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 	keyHex:				The 128-bit hexadecimal key used to encrypt the image
   * 
   * Return value: An encrypted image derived from the user-given image
   */
  public static BufferedImage encryptImage(BufferedImage image, int imageWidth,
  																				 int imageHeight, String keyHex){
  	
  	// Break the image into stateHexes 
    String[] stateHexes = imageToStateHexes(image, imageWidth, imageHeight);
    System.out.println("FINISHED READING");

    // Encrypt the stateHexes
    String[] encryptedStateHexes = encryptStateHexes(stateHexes, keyHex);
    System.out.println("FINISHED ENCRYPTING");
    
    // Convert the encrypted stateHexes to an image
    return stateHexesToImage(encryptedStateHexes, imageWidth, imageHeight);
  }

  /**
   * decryptImage
   * 
   * This function decrypts an image and returns the decrypted image.
   * 
   * Parameters:
   * 	image: 				The image to be decrypted
   *  imageWidth: 	The width of the image measured in pixels
   * 	imageHeight: 	The height of the image measured in pixels
   * 	keyHex:				The 128-bit hexadecimal key used to decrypt the image
   * 
   * Return value: An encrypted image derived from the user-given image
   */
  public static BufferedImage decryptImage(BufferedImage image, int imageWidth,
  																				 int imageHeight, String keyHex){
  	
  	// Break the image into stateHexes 
  	String[] stateHexes = imageToStateHexes(image, imageWidth, imageHeight);
    System.out.println("FINISHED READING");
    
    // Decrypt the stateHexes
    String[] decryptedStateHexes = decryptStateHexes(stateHexes, keyHex);
    System.out.println("FINISHED DECRYPTING");
    
    // Convert the decrypted stateHexes to an image
    return stateHexesToImage(decryptedStateHexes, imageWidth, imageHeight);
  }
  
  /**
   * stringToHex
   * 
   * This function takes the 16-character ASCII key and converts it to 
   * a 32 character hexadecimal key, both of which are 128 bits.
   * 
   * Parameters:
   * 	key: The user provided 16 ASCII character (128-bit) key used to process 
   *       the image
   * 
   * Return value: The 32 hexadecimal character key used to process the image
   */
  public static String stringToHex(String key){
  	
  	// Break the characters into an array
	  char characters[] = key.toCharArray();
	  StringBuilder hexKey = new StringBuilder();
	  
	  // Convert each character to hexadecimal form
    for (int i = 0; i < characters.length; i++) {
      hexKey.append(Integer.toHexString((int) characters[i]));
    }
    System.out.println("HEXKEY IS:" + hexKey.toString());
    return hexKey.toString();
  }
}
