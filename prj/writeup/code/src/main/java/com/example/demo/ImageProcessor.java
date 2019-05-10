package com.example.demo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

  private static String[] imageToHexPixels(BufferedImage image, int imageWidth, int imageHeight) {

    int[] rgbData = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
    String[] hexPixels = new String[imageHeight * imageWidth];

    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        int red = (rgbData[(i * imageWidth) + j] >> 16) & 0xFF;
        String redHex = Integer.toHexString(red);
        if(red <= 15){
          redHex = "0" + redHex;
        }

        int green = (rgbData[(i * imageWidth) + j] >> 8) & 0xFF;
        String greenHex = Integer.toHexString(green);
        if(green <= 15){
          greenHex = "0" + greenHex;
        }

        int blue = (rgbData[(i * imageWidth) + j]) & 0xFF;
        String blueHex = Integer.toHexString(blue);
        if(blue <= 15){
          blueHex = "0" + blueHex;
        }

        hexPixels[(imageWidth * i) + j] = redHex + greenHex + blueHex;
      }
    }

    return hexPixels;
  }

  private static String[] hexPixelsToStateHexes(String[] hexPixels) {
    StringBuilder hexImage = new StringBuilder();
    for (String hexPixel : hexPixels) {
      hexImage.append(hexPixel);
    }
    int numOfStateHex = (int) Math.ceil(hexImage.length() / 32.0);

    if (hexImage.length() % 32 != 0) {
      int bufferSpaces = 32 - (hexImage.length() % 32);
      for (int i = 0; i < bufferSpaces; i++) {
        hexImage.append("0");
      }
    }

    String[] stateHexes = new String[numOfStateHex];
    for (int i = 0; i < numOfStateHex; i++) {
      stateHexes[i] = hexImage.substring(i * 32, (i * 32) + 32);
    }
    return stateHexes;
  }


  private static String[] encryptStateHexes(String[] stateHexes, String keyHex){
    String[] encryptedStateHexes = new String[stateHexes.length];
    for(int i = 0; i < stateHexes.length; i++){
      encryptedStateHexes[i] = AEScipher.AES(stateHexes[i], keyHex);
      System.out.println("ENCRYPTED: " + encryptedStateHexes[i]);
    }
    return encryptedStateHexes;
  }

  private static String[] decryptStateHexes(String[] stateHexes, String keyHex){
    String[] decryptedStateHexes = new String[stateHexes.length];
    for(int i = 0; i < stateHexes.length; i++){
      decryptedStateHexes[i] = AEScipher.AESDecrypt(stateHexes[i], keyHex);
    }
    return decryptedStateHexes;
  }

  static String[] stateHexesToHexPixels(String[] stateHexes, int imageWidth, int imageHeight) {
    StringBuilder hexString = new StringBuilder();
    String[] hexPixels = new String[imageHeight * imageWidth];

    for (String stateHex : stateHexes) {
      hexString.append(stateHex);
    }

    for(int i = 0; i < imageHeight * imageWidth; i++){
      hexPixels[i] = hexString.substring(i*6,(i*6)+6);
    }

    return hexPixels;
  }

  static BufferedImage hexPixelsToImage(String[] hexPixels, int imageWidth, int imageHeight){
    BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    for(int i = 0; i < imageHeight; i++){
      for(int j = 0; j < imageWidth; j++){
        int p = 0;
        String redHex = hexPixels[(imageWidth * i) + j].substring(0,2);
        int red = Integer.parseInt(redHex, 16);
        p = p | (red << 16);

        String greenHex = hexPixels[(imageWidth * i) + j].substring(2,4);
        int green = Integer.parseInt(greenHex, 16);
        p = p | (green << 8);

        String blueHex = hexPixels[(imageWidth * i) + j].substring(4,6);
        int blue = Integer.parseInt(blueHex, 16);
        p = p | blue;

        System.out.println("Pixel =" + p + " rgb = " + redHex + greenHex+  blueHex);
        image.setRGB(j,i,p);
      }
    }
    System.out.println("FINISHED BUILDING ENCRYPTED IMAGE");
    return image;
  }

  private static String[] imageToStateHex(BufferedImage image, int imageWidth, int imageHeight){
    String[] hexPixels = imageToHexPixels(image, imageWidth, imageHeight);
    for(String hexPixel: hexPixels){
      System.out.println("Hex pixels" + hexPixel);
    }
    String[] stateHex = hexPixelsToStateHexes(hexPixels);

    return stateHex;
  }

  private static BufferedImage stateHexesToImage(String[] encryptedStateHexes, int imageWidth, int imageHeight){
    String[] encryptedHexPixels = stateHexesToHexPixels(encryptedStateHexes, imageWidth, imageHeight);
    for(String hexPixels: encryptedHexPixels){
      System.out.println("ENCRYPTED Hex pixels" + hexPixels);
    }
    return hexPixelsToImage(encryptedHexPixels, imageWidth, imageHeight);
  }

  public static BufferedImage encryptImage(BufferedImage image, int imageWidth, int imageHeight, String keyHex){
    String[] stateHexes = imageToStateHex(image, imageWidth, imageHeight);
    System.out.println("FINISHED READING");

    for (String stateHex: stateHexes) {
      System.out.println("TEST1 stateHex plainText=" + stateHex);
    }
    String[] encryptedStateHexes = encryptStateHexes(stateHexes, keyHex);
    System.out.println("FINISHED ENCRYPTING");

    for (String stateHex: encryptedStateHexes) {
      System.out.println("TEST2 encryption stateHex=" + stateHex);
    }

    // Test:
//    String[] decryptedStateHexes = decryptStateHexes(encryptedStateHexes, keyHex);
//    System.out.println("FINISHED DECRYPTING");

    //Fine and checked until this point
    return stateHexesToImage(encryptedStateHexes, imageWidth, imageHeight);
  }

  public static BufferedImage decryptImage(BufferedImage image, int imageWidth, int imageHeight, String keyHex){

    String[] stateHexes = imageToStateHex(image, imageWidth, imageHeight);
    System.out.println("FINISHED READING");

    for (String stateHex: stateHexes) {
      System.out.println("TEST3 encrypted stateHex= " + stateHex);
    }

    String[] decryptedStateHexes = decryptStateHexes(stateHexes, keyHex);
    System.out.println("FINISHED DECRYPTING");

    return stateHexesToImage(decryptedStateHexes, imageWidth, imageHeight);
  }
}
