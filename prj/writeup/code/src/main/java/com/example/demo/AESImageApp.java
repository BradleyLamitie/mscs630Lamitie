package com.example.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import controller.AESImageAppController;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"demo","controller"})
public class AESImageApp {

	public static void main(String[] args) {
		new File(AESImageAppController.uploadDirectory).mkdir();
		SpringApplication.run(AESImageApp.class, args);
	}
}