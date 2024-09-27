package com.ddk.asmsof306.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPageController {
    private final AuthenticationManager authenticationManager;
    @GetMapping({"/","/home","/index"})
    String adminPage(){

        return "admin/index.html";
    }
    @GetMapping({"/login"})
    String adminLoginPage(){

        return "auth-login";
    }


}
