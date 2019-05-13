/**
 * file: MyErrorController.java
 * author: Bradley Lamitie
 * course: MSCS 630
 * assignment: Project
 * due date: May 12, 2018
 * version: 1.0
 *
 * This file contains the declaration of the MyErrorController class
 */
package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MyErrorController
 * 
 * This class maps errors to the errorView page using a controller based on MVC
 */
@Controller
public class MyErrorController implements ErrorController  {
 
/**
 * handleError
 * 
 * This function redirects to errorView.html when the user runs into an error
 * The only ways the user can get here is by entering a file that isnt jpg
 * or png, a file larger then 1MB or internal errors with the system.
 * 
 * Return value: The page (view) that will be served to the user in response 
 * to the mapping
 * 
 */
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    return "ErrorView";
  }
 
  /**
	 * getErrorPath
	 * 
	 * This function returns the path of the error page.
	 * 
	 * Return value: The error path
	 * 
	 */
  @Override
  public String getErrorPath() {
      return "/error";
  }
}