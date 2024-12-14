package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.Order;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.OrderRequest;
import com.FoodHut.FoodHut.service.OrderService;
import com.FoodHut.FoodHut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;



}
