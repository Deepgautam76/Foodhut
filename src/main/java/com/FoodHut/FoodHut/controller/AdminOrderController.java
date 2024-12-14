package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.Order;
import com.FoodHut.FoodHut.model.User;
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

    //API end-point for get Orders history
    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<?> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        List<Order> order=orderService.getRestaurantOrder(id,order_status);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    //API end-point for updateOrder status by admin
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);

        Order order=orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>( order,HttpStatus.OK);
    }





}
