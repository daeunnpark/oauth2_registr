package com.example.springsecurityjpa.controller;

import com.example.springsecurityjpa.model.Oauth2Client;
import com.example.springsecurityjpa.model.User;
import com.example.springsecurityjpa.repository.Oauth2ClientRepository;
import com.example.springsecurityjpa.service.CustomOauth2ClientDetailsService;
import com.example.springsecurityjpa.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/app")
public class appController {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    CustomOauth2ClientDetailsService customOauth2ClientDetailsService;

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView page = new ModelAndView();
        page.setViewName("register");
        page.addObject("client", new Oauth2Client());
        return page;
    }

    @PostMapping("/register")
    public ModelAndView register(Oauth2Client client, Authentication auth) throws NoSuchAlgorithmException {

        User user = myUserDetailService.findByUsername(auth.getName());
        client.setUser(user);
        String name = client.getName();

        customOauth2ClientDetailsService.save(client);
        return new ModelAndView("redirect:/app/view/"+name);
    }

    @GetMapping("/view/all")
    public ModelAndView viewApps(Authentication auth) {
        User user = myUserDetailService.findByUsername(auth.getName());
        System.out.println("auth name = " + auth.getName());
        System.out.println("user id = " + user.getId());
        System.out.println(customOauth2ClientDetailsService.findByUserId(user.getId()));
        ModelAndView page = new ModelAndView();
        page.addObject("clients", customOauth2ClientDetailsService.findByUserId(user.getId()));
        page.setViewName("viewAll");
        return page;
    }

    @GetMapping("/view/{name}")
    public ModelAndView viewApp(Authentication auth, @PathVariable String name) {
        User user = myUserDetailService.findByUsername(auth.getName());
        ModelAndView page = new ModelAndView();
        page.addObject("client", customOauth2ClientDetailsService.findByUserIdAndName(user.getId(), name));
        page.setViewName("view");
        return page;
    }

    @PostMapping("/delete/{name}")
    public ModelAndView deleteApp(Authentication auth, @PathVariable String name) {
        User user = myUserDetailService.findByUsername(auth.getName());
        customOauth2ClientDetailsService.delete(customOauth2ClientDetailsService.findByUserIdAndName(user.getId(), name));
        return new ModelAndView("redirect:/app/view/all");
    }


}
