package com.example.demo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Project_Driver {

  public static void main(String[] args){

    //TODO: Add code here when creating the UI that takes in the user's image
    File jpg = new File("C:\\Users\\BradL\\Documents\\ImageOutput.jpg");
//    File jpg = new File("C:\\Users\\BradL\\Documents\\imageInput.png");
//    File jpg = new File("C:\\Users\\BradL\\Documents\\images.jpg");
//    File jpg = new File("C:\\Users\\BradL\\Documents\\testImage2.jpeg");

    BufferedImage image = null;
    try {
      image = ImageIO.read(jpg);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert image != null;
    int imageHeight = image.getHeight();
    int imageWidth = image.getWidth();

    //TODO: Add code here when creating the UI that takes in the user's key and possibly converts it to hexadecimal
    String keyHex = "0123456789ABCDEF0123456789ABCDEF";

    BufferedImage outputImage = ImageProcessor.encryptImage(image, imageWidth, imageHeight, keyHex);

    String home = System.getProperty("user.home");
    //TODO: Allow user to specify filename for output
    File file = new File(home + "\\Documents\\ImageOutput.jpg");
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("FILE ALREADY EXISTS");
    }
    try {
      ImageIO.write(outputImage, "jpg", file);
    } catch (IOException e) {
      e.printStackTrace();
    }

    File encryptedjpg = new File("C:\\Users\\BradL\\Documents\\ImageOutput.jpg");

    BufferedImage encryptedImage = null;
    try {
      encryptedImage = ImageIO.read(encryptedjpg);
    } catch (IOException e) {
      e.printStackTrace();
    }

//    ImageProcessor.decryptImage(encryptedImage, imageWidth, imageHeight, keyHex);
  }
}
