package com.FoodHut.FoodHut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    /**
     * Welcome Home API end-point
    * */
    @RequestMapping("/")
    public ResponseEntity<String> homeController(){
        return new ResponseEntity<>("Welcome to food hut home page", HttpStatus.OK);
    }
}
