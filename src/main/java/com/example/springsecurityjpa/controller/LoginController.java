package com.example.springsecurityjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.springsecurityjpa.MyUserDetailService;
import com.example.springsecurityjpa.model.User;

@Controller
public class LoginController {
	@Autowired
	MyUserDetailService myUserDetailService;
	
	@RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
	public ModelAndView home() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("home");
	    return modelAndView;
	}
	
	public ModelAndView login() {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("login");
		return loginPage;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
	    ModelAndView signupPage = new ModelAndView();
	    User user = new User();
	    signupPage.addObject("user", user);
	    signupPage.setViewName("signup"); 
	    return signupPage;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createNewUser(User user, BindingResult bindingResult) {
	    System.out.println("SIGN UP HEREEE");
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
	    	System.out.println("has errors");
	    	page.setViewName("signup");
	    }else {
	    	myUserDetailService.saveUser(user);
            page.addObject("successMessage", "User has been registered successfully");
	        page.addObject("user", new User());
	        page.setViewName("login");
	    }
	   
	    return page;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView user() {
		ModelAndView userPage = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = myUserDetailService.findByUsername(auth.getName());	// auth default setting 조사
		userPage.addObject("currentUser", user);
		userPage.addObject("username", user.getUsername());
		userPage.addObject("name", user.getName());
		userPage.addObject("id", user.getId());
		userPage.setViewName("user");
		
		return userPage;
	}

	
	@RequestMapping(value = "/findUsername", method = RequestMethod.GET)
	public ModelAndView findUsername() {
		ModelAndView findUsernamePage = new ModelAndView();
		findUsernamePage.setViewName("findUsername");
		return findUsernamePage;
	}
	
	//TODO: ADD when user is not fouhd
	@RequestMapping(value = "/findUsernameByName", method = RequestMethod.GET)
	public ModelAndView findUserNameByName(@RequestParam(value = "name", required = true) String name){
		System.out.println("name = " + name);
		ModelAndView findUserNamePage = new ModelAndView();
		User user = myUserDetailService.findByName(name);
		
		findUserNamePage.addObject("name", user.getName());
		findUserNamePage.addObject("username", user.getUsername());
		findUserNamePage.setViewName("findUsername");
		 
		return findUserNamePage;
	}
	
	//TODO: ADD when username is not found
	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public ModelAndView findPassword() {
		ModelAndView findPasswordPage = new ModelAndView();
		findPasswordPage.setViewName("findPassword");
		return findPasswordPage;
	}
	
	@RequestMapping(value = "/findPasswordByUsername", method = RequestMethod.GET)
	public ModelAndView indPasswordByUsername(@RequestParam(value = "username", required = true) String username){
		System.out.println("username = " + username);
		ModelAndView findPasswordPage = new ModelAndView();
		User user = myUserDetailService.findByUsername(username);
		
		findPasswordPage.addObject("username", user.getUsername());
		findPasswordPage.addObject("password", user.getPassword());
		findPasswordPage.setViewName("findUsername");
		 
		return findPasswordPage;
	}
	
	

}
