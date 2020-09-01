package com.example.springsecurityjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.springsecurityjpa.service.MyUserDetailService;
import com.example.springsecurityjpa.model.User;

@RestController
public class LoginController {
	
	@Autowired
	MyUserDetailService myUserDetailService;
	
	@RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
	public ModelAndView home() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("home");
	    return modelAndView;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView page = new ModelAndView();
		page.setViewName("login");
		return page;
	}
	
	@GetMapping("/signup")
	public ModelAndView signup() {
	    ModelAndView page = new ModelAndView();
	    User user = new User();
	    page.addObject("user", user);
	    page.setViewName("signup"); 
	    return page;
	}
	
	//TODO: ADD when user is already existing
	@PostMapping("/signup")
	public ModelAndView createNewUser(User user, BindingResult bindingResult) {
	    System.out.println(user.getUsername());
	    System.out.println(user.getPassword());
	    System.out.println(user.getName());
	    
	    ModelAndView page = new ModelAndView();
	    
	    if(myUserDetailService.exists(user.getUsername())) {
	        bindingResult
            .rejectValue("username", "error.username",
                    "There is already a user registered with the username provided");
	    }
	    
	    if(bindingResult.hasErrors()) {
	    	System.out.println("has errors - already existing");
	    	page.setViewName("signup");
	    }else {
	    	myUserDetailService.saveUser(user);
            page.addObject("successMessage", "User has been registered successfully");
	        page.addObject("user", new User());
	        page.setViewName("login");
	    }
	   
	    return page;
	}
	
	@GetMapping("/user")
	public ModelAndView user() {
		ModelAndView page = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = myUserDetailService.findByUsername(auth.getName());	// auth default setting 조사
		page.addObject("currentUser", user);
		page.addObject("username", user.getUsername());
		page.addObject("name", user.getName());
		page.addObject("id", user.getId());
		page.setViewName("user");
		
		return page;
	}
	
	@GetMapping("/findUsername")
	public ModelAndView findUsername() {
		ModelAndView page = new ModelAndView();
		page.addObject("user", new User());
		page.setViewName("findUsername");
		return page;
	}
	
	//TODO: ADD when user is not found
	@GetMapping("/findUsernameByName")
	public ModelAndView findUserNameByName(@RequestParam(value = "name", required = true) String name){
		ModelAndView page = new ModelAndView();
		User user = myUserDetailService.findByName(name);

		page.addObject("name", user.getName());
		page.addObject("username", user.getUsername());
		page.setViewName("findUsername");
		 
		return page;
	}
	
	//TODO: ADD when username is not found
	@GetMapping(value = "/findPassword")
	public ModelAndView findPassword() {
		ModelAndView page = new ModelAndView();
		page.addObject("user", new User());	
		page.setViewName("findPassword");
		return page;
	}
	
	@GetMapping(value = "/findPasswordByUsername")
	public ModelAndView indPasswordByUsername(@RequestParam(value = "username", required = true) String username){
		System.out.println("username = " + username);
		ModelAndView page = new ModelAndView();
		User user = myUserDetailService.findByUsername(username);
		
		page.addObject("username", user.getUsername());
		page.addObject("password", user.getPassword());
		page.setViewName("findPassword");
		 
		return page;
	}
	
}
