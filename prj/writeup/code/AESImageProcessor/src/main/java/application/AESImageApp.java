/**
 * file: AESImageApp.java
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: Project
 * due date: May 12, 2018
 * version: 1.0
 *
 * This file contains the declaration of the AESImageApp class
 */
package application;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import controller.AESImageAppController;

/**
 * AESImageApp
 * 
 * This class is the Configuration of the AESImage Application.
 * It is used to create the directory, and to start the Spring 
 * Application on a Tomcat server. You can access the application
 * at localhost:8080
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({"demo","controller"})
public class AESImageApp {
	
	/**
	 * main
	 * 
	 * This function creates the directory specified in the AESImageAppController
	 * class and runs the application.
	 */
	public static void main(String[] args) {
		new File(AESImageAppController.uploadDirectory).mkdir();
		SpringApplication.run(AESImageApp.class, args);
	}
}