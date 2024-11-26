package com.FoodHut.FoodHut.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @RequestMapping("/")
    public String health(){
        return "Every thing is good.";
    }
}
