package com.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/user")
    public String getUser() {
        return "Welcome User!";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome Admin!";
    }

    @GetMapping("/all")
    public String getAll() {
        return "Welcome Whomsoever!";
    }
}
