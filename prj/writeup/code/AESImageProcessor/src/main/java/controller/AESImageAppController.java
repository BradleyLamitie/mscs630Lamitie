/**
 * file: AESImageAppController.java
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: Project
 * due date: May 12, 2018
 * version: 1.0
 *
 * This file contains the declaration of the AESImageAppController class
 */
package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import application.ImageProcessor;

/**
 * AESImageAppController
 * 
 * This class maps requests passed from the HTML pages and Thymeleaf to
 * perform operations and redirect to the other pages. This application 
 * uses MVC.
 */
@Controller
public class AESImageAppController {
	
	// This is where we store the image passed from the user.
  public static String uploadDirectory = "C:"+"/AESImage";
  
  /**
   * uploadPage
   * 
   * This function redirects to AESImageLanding.html when the user visits 
   * localhost:8080
   * 
   * Parameters:
   * 	model: The parameter used to carry data
   * 
   * Return value: The page (view) that will be served to the user in response 
   * to the mapping
   * 
   */
  @RequestMapping("/")
  public String uploadPage(Model model) {
	  // Load HTML page view
	  return "AESImageLandingView";
  }
  
  /**
   * upload
   * 
   * This function saves the user provided image to a directory, 
   * encrypts/decrypts it and saves/passes the image to the download page
   * 
   * Parameters:
   * 	model:   The parameter used to carry data
   * 	file:    The image passed in from the user
   *  key:     The key to be used in encryption/decryption. The key is in ASCII
   *  					 format
   *  aesType: The user's choice. Value will be either Encrypt or Decrypt
   * Return value: The page (view) that will be served to the user in response 
   * to the mapping
   * 
   */
  @RequestMapping("/uploadData")
  public String upload(Model model,
  										 @RequestParam("image") MultipartFile file,
  										 @RequestParam("key") String key,
  										 @RequestParam("AESType") String aesType){
  	long t0 = System.currentTimeMillis();
  	// Convert the ASCII characters to a valid hexadecimal key
	  String keyHex = ImageProcessor.stringToHex(key);
	  
	  // Get the filename and path for the image passed in from user
	  Path fileNameandPath = Paths.get(uploadDirectory, 
	  																 file.getOriginalFilename());
	  
	  // Generate the file and write the image onto it
	  File imageFile = fileNameandPath.toFile();
	  BufferedImage image = null;
	  try {
	  	Files.write(fileNameandPath, file.getBytes());
	    image = ImageIO.read(imageFile);
	    Files.delete(fileNameandPath);
	  } catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("COULDNT READ IMAGE AT = " + fileNameandPath);
	  }
	  
	  // Ensure the image isn't blank
	  assert image != null;
	  
	  // Gather image height and width
	  int imageHeight = image.getHeight();
    int imageWidth = image.getWidth();

    // Generate output image based on the user's AEStype selection 
    BufferedImage outputImage = null;
    if(aesType.equals("Encrypt")) {
      outputImage = ImageProcessor.encryptImage(image, imageWidth,
        																					imageHeight, keyHex);
    } else if(aesType.equals("Decrypt")) {
  	  outputImage = ImageProcessor.decryptImage(image, imageWidth,
  	  																					imageHeight, keyHex);
    }
    
    // Generate the output file and store in static directory
    // NOTE: We store it in static directory so the user can view the image
    //			 after encryption/decryption.
    String outputFilePath = 
    		uploadDirectory + "/output.png";
    File outputFile = new File(outputFilePath);
    System.out.println(outputFilePath);
    try {
      outputFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("FILE ALREADY EXISTS");
    }
    
    
    // Write the image to the outputfile
    // Have to use png to avoid lossy nature of jpg
    try {
      ImageIO.write(outputImage, "png", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Pass the image path as a variable to HTML page
	  model.addAttribute("image", "output.png");
	  model.addAttribute("imagePath", outputFilePath);
	  
	  System.out.println("Time to encrypt:" + (System.currentTimeMillis() - t0)/1000.0);
	  // Load HTML page view
	  return "AESImageDownloadView";
  }
}
