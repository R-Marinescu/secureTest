package com.example.springsecurity.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String sendHome(Principal principal){

        return "index";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("authenticated")
    public String authenticated(){

        return "authenticated";
    }

    @GetMapping("/logout")
    String logout() {
        return "logout";
    }

    @RequestMapping(value = "/adminView", method = { RequestMethod.GET, RequestMethod.POST })
    String loginAdmin(){

        return "adminView";
    }

    @RequestMapping(value = "/profile", method = { RequestMethod.GET, RequestMethod.POST })
    public String loginUser(){

        return "profile";
    }

    @GetMapping("/customer")
    public String customerPage(){

        return "customer";
    }


}
