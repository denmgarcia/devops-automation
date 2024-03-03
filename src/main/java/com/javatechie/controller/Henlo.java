package com.javatechie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class Henlo {

    @GetMapping("message")
    public String message(){
        return "welcome to javatechie";
    }


}
