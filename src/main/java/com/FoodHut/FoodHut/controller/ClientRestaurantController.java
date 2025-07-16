package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.dto.RestaurantDto;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.serviceInterfaces.RestaurantService;
import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class ClientRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    /**
     * API for a Search restaurant by keyword
     * */
    @GetMapping("/search")
    public ResponseEntity<?> searchRestaurant(
            @RequestParam String keyword
    ) throws Exception {

        List<Restaurant>restaurants=restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }


    /**
     * API for fetch restaurants
     * */
    @GetMapping("/all")
    public ResponseEntity<?> getAllRestaurants(
    ) throws Exception {

        List<Restaurant> restaurants=restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }

    /**
     * API for find a restaurant By id
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> findRestaurantById(
            @PathVariable Long id
    ) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

    /**
     * API for add-favorite a restaurant
     * */
    @GetMapping("/{id}/add-favorites")
    public ResponseEntity<?> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant=restaurantService.addToFavorites(id,user);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
}
