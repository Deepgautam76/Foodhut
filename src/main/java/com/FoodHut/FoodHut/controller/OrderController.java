package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.Order;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.dto.request.OrderRequest;
import com.FoodHut.FoodHut.serviceInterfaces.OrderService;
import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * API end-point for create an order
     * */
    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest req,
                                         @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        Order order=orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * API end-point for get Orders
     * */
    @GetMapping("/order/user")
    public ResponseEntity<?> getOrderHistory(
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        List<Order> order=orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
