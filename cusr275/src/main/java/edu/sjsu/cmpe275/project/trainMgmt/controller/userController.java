package edu.sjsu.cmpe275.project.trainMgmt.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import edu.sjsu.cmpe275.project.trainMgmt.model.user;
import edu.sjsu.cmpe275.project.trainMgmt.service.userService;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import java.util.List;

@Transactional
@Service
@RestController


public class userController {
	
 @Autowired
 userService userService;
 
	
	
	
	 @PostMapping("/register")
	    @ResponseBody
	    public ModelAndView createUser(@Valid
	    			@RequestParam(value= "username", required = true) String username,
	    			@RequestParam(value= "email", required = true) String email,
	    			@RequestParam(value= "password", required = true) String password)
	    				 			
	    {
		 user createuser = new user(username,email,password);
		 userService.saveUser(createuser);
		 ModelAndView modelAndView;
		 ModelMap map = new ModelMap();
		 String successMessage = "Successfully Registered";
		 map.addAttribute("Message", successMessage);
		 
		 modelAndView = new ModelAndView(new MappingJackson2JsonView(),map);
		 return modelAndView;
		
	    }

	 
	 @GetMapping("/login")
	    public ResponseEntity getUserLoginDetails(@Valid
	    		@RequestParam(value= "email", required = false)String email,
	    		@RequestParam(value= "password", required = false)String password) {
	        List<user> AllUsers = userService.findAll();
	        
	       
	       
	        for(int i=0;i<AllUsers.size();i++)
	        {
	        	System.out.println(AllUsers.get(i).getEmail());
	        	if(AllUsers.get(i).getEmail().equals(email) && AllUsers.get(i).getPassword().equals(password))
	        	{
	        		
	        		user currentUser = AllUsers.get(i);
	       		 
	        		 
	        	 return ResponseEntity.ok().body(currentUser);
	        	}
	        	
	        }
	        
	        return ResponseEntity.badRequest().build();
	        
	    }
	 
	 @GetMapping("/getUserId")
	 public ModelAndView getUserLoginId(@Valid
	    		@RequestParam(value= "email", required = false)String email)
	 {
		 
		 List<user> AllUsers = userService.findAll();
		 ModelAndView modelAndView;
		 ModelMap map = new ModelMap();
		
		 
		 
		 for(int i=0;i<AllUsers.size();i++)
		 {
			
			 if(AllUsers.get(i).getEmail().equals(email))
			 {
				 //System.out.println(AllUsers.get(i).getId());
				 int finduser = AllUsers.get(i).getId();
				 
				 
				 map.addAttribute("Id", finduser);
				 modelAndView = new ModelAndView(new MappingJackson2JsonView(),map);
				 return modelAndView;
				 //return ResponseEntity.ok().body(finduser);
				 
			 }
			 
			 			
		 }
		 map.addAttribute("Message", "400 Bad Request");
		 modelAndView = new ModelAndView(new MappingJackson2JsonView(),map);
		 return modelAndView;
		 //return ResponseEntity.badRequest().build();
	 }
	 
	  
	 
}





