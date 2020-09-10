package com.example.springsecurityjpa.controller;

import com.example.springsecurityjpa.model.User;
import com.example.springsecurityjpa.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class loginController {

    @Autowired
    CustomUserDetailsService userService;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView page = new ModelAndView();
        page.setViewName("home");
        return page;
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
        page.addObject("user", new User());
        page.setViewName("signup");
        return page;
    }

    @PostMapping("/signup")
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        System.out.println("signup heree");
        System.out.println(user.toString());
        if (userService.exists(user.getUsername())) {
            bindingResult
                    .rejectValue("username", "error.username",
                            "There is already a user registered with the username provided");
        }

        if (bindingResult.hasErrors()) {
			return signup();
		}
		userService.saveUser(user);
		return login();
    }

    @GetMapping("/user")
    public ModelAndView user(Authentication auth) {
        User user = userService.findByUsername(auth.getName());

		ModelAndView page = new ModelAndView();
        page.addObject("currentUser", user);
        page.addObject("username", user.getUsername());
        page.addObject("name", user.getName());
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

    @GetMapping("/findUsernameByName")
    public ModelAndView findUserNameByName(@RequestParam(value = "name", required = true) String name) {
        User user = userService.findByName(name);

		ModelAndView page = new ModelAndView();
        page.addObject("name", user.getName());
        page.addObject("username", user.getUsername());
        page.setViewName("findUsername");

        return page;
    }

    @GetMapping(value = "/findPassword")
    public ModelAndView findPassword() {
        ModelAndView page = new ModelAndView();
        page.addObject("user", new User());
        page.setViewName("findPassword");
        return page;
    }

    @GetMapping(value = "/findPasswordByUsername")
    public ModelAndView indPasswordByUsername(@RequestParam(value = "username", required = true) String username) {
        User user = userService.findByUsername(username);

		ModelAndView page = new ModelAndView();
        page.addObject("username", user.getUsername());
        page.addObject("password", user.getPassword());
        page.setViewName("findPassword");

        return page;
    }

}
