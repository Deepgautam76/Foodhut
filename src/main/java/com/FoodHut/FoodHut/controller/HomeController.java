package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    /**
     * Welcome Home API end-point
    * */
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> homeController(){
        return new ResponseEntity<>("Welcome to food hut home page", HttpStatus.OK);
    }

    /**
     * To get the all users in a database
     */
    @GetMapping("/users")
    public ResponseEntity<?> allDatabaseUsers(){
        return new  ResponseEntity< >(userService.findAllUsers(),HttpStatus.OK);
    }

}
