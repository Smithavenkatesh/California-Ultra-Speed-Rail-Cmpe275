package com.javainuse.controllers;

import com.javainuse.model.LoginForm;
import com.javainuse.model.User;


import com.javainuse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;






import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

   

    @RequestMapping("/login")
    public String login(LoginForm loginForm) {
        return "/login";
    }

    
    @RequestMapping(value="/loginProcess", method = RequestMethod.POST)
	public ModelAndView userLogin(ModelAndView model,@Valid LoginForm loginForm, BindingResult bindingResult,RedirectAttributes redirectAttrs, HttpServletRequest request,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "password", required = true) String password) {
	
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
    	if (bindingResult.hasErrors()) {
            
    		model.addObject("nullmsg", "Please fill the form correctly with all the values.");
			model.setViewName("login");
       }
       
    	 
		User user = userService.findByEmail(email);
		if (user == null) {

			model.addObject("errormsg", "Invalid username");
			model.setViewName("login");
			
		} 
		
		
		else if (!(encoder.matches(password, user.getPassword())) && user.getEnabled()) {

			
			model.addObject("errormsg", "Email or Password is incorrect");
			model.setViewName("login");
			
	
		} 
		else if (user!= null && !user.getEnabled()) {
				

			model.addObject("accountverificationmsg", "Access Denied: Account not verfied yet");
			model.setViewName("/login");
				
		}
			else if ((encoder.matches(password, user.getPassword())) && user.getEnabled()) {
				model.addObject("successmsg", "login is successful.");
				model.setViewName("/welcome");
				
			
  	      	}
			else {
		model.addObject("otherwisemsg", "login error,. Please try again later.");
		model.setViewName("/login");}
		
		return model;
}		
		
		
    }

		

		
