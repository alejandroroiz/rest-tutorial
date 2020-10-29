package com.base22.rest.tutorial.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Hello Base22. This is a message from Spring!\n";
    }
}
