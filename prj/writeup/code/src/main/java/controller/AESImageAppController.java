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

import com.example.demo.ImageProcessor;

@Controller
public class AESImageAppController {
  public static String uploadDirectory = System.getProperty("user.dir")+"/AESImage";
  @RequestMapping("/")
  public String UploadPage(Model model) {
	  return "AESImageLanding";
  }
  
  @RequestMapping("/uploadData")
  public String upload(Model model,@RequestParam("image") MultipartFile file, @RequestParam("key") String keyHex, @RequestParam("AESType") String aesType) {	  
	  Path fileNameandPath = Paths.get(uploadDirectory, file.getOriginalFilename());
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
	  assert image != null;
	  int imageHeight = image.getHeight();
      int imageWidth = image.getWidth();

      BufferedImage outputImage = null;
      if(aesType.equals("Encrypt")) {
          outputImage = ImageProcessor.encryptImage(image, imageWidth, imageHeight, keyHex);
      } else if(aesType.equals("Decrypt")) {
    	  outputImage = ImageProcessor.decryptImage(image, imageWidth, imageHeight, keyHex);
      }

      File outputFile = new File(uploadDirectory + "\\Output.jpg");
      try {
        outputFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("FILE ALREADY EXISTS");
      }
      try {
        ImageIO.write(outputImage, "jpg", outputFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
	  model.addAttribute("msg", "Successfully uploaded files ");
	  return "AESImageDownload";
  }
}
