package com.innovator.journal.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health_check {
    @GetMapping("/ok")
    public String healthCheck(){
        return "ok";
    }
}
