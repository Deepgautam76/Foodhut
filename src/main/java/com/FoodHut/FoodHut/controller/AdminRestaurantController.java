package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.CreateRestaurantRequest;
import com.FoodHut.FoodHut.response.MessageResponse;
import com.FoodHut.FoodHut.service.RestaurantService;
import com.FoodHut.FoodHut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    //API for create a restaurant
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.createRestaurant(req,user);
        return  new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    //API for update a restaurant
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.updateRestaurant(id,req);
        return  new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    //API for delete a restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        restaurantService.deleteReastaurant(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("restaurant deleted successfully");
        return  new ResponseEntity<>(res,HttpStatus.OK);
    }

    //API for update restaurant status opening and closing by id
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.UpdateRestaurantStatus(id);
        return  new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    //API for find a restaurant by UserId
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

}
