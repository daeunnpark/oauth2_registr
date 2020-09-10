package com.example.springsecurityjpa.controller;

import com.example.springsecurityjpa.model.Oauth2Client;
import com.example.springsecurityjpa.model.User;
import com.example.springsecurityjpa.service.CustomOauth2ClientDetailsService;
import com.example.springsecurityjpa.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/app")
public class clientController {

    @Autowired
    CustomUserDetailsService userService;

    @Autowired
    CustomOauth2ClientDetailsService clientService;

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView page = new ModelAndView();
        page.setViewName("register");
        page.addObject("client", new Oauth2Client());
        return page;
    }

    @PostMapping("/register")
    public ModelAndView register(Oauth2Client client, Authentication auth) throws NoSuchAlgorithmException {
        User user = userService.findByUsername(auth.getName());
        System.out.println("name" + client.getName());
        System.out.println("registered uri " + client.getRegisteredRedirectUri());
        client.setUser(user);
        clientService.save(client);
        return new ModelAndView("redirect:/app/view/" + client.getName());
    }

    @GetMapping("/view/all")
    public ModelAndView viewApps(Authentication auth) {
        //User user = userService.findByUsername(auth.getName());
        ModelAndView page = new ModelAndView();
        page.addObject("clients", clientService.findByUsername(auth.getName()));
        page.setViewName("viewAll");
        return page;
    }

    @GetMapping("/view/{name}")
    public ModelAndView viewApp(Authentication auth, @PathVariable String name) {
       // User user = userService.findByUsername(auth.getName());
        ModelAndView page = new ModelAndView();
        Oauth2Client client = clientService.findByUsernameAndName(auth.getName(), name);
        page.addObject("client", clientService.findByUsernameAndName(auth.getName(), name));
        page.setViewName("view");
        return page;
    }

    @PostMapping("/delete/{name}")
    public ModelAndView deleteApp(Authentication auth, @PathVariable String name) {
        User user = userService.findByUsername(auth.getName());
        clientService.delete(clientService.findByUsernameAndName(auth.getName(), name));
        return new ModelAndView("redirect:/app/view/all");
    }

}
