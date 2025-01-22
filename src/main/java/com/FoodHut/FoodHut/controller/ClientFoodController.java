package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.Food;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.serviceInterfaces.FoodService;
import com.FoodHut.FoodHut.serviceInterfaces.RestaurantService;
import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class ClientFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * API end point for search the foods
     * */
    @GetMapping("/search")
    public ResponseEntity<?> searchFood(
            @RequestParam String name,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        List<Food> foods=foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    /**
     * API endpoint For a get restaurant food
     * */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getRestaurantFood(
            @RequestParam boolean vagetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false) String food_category,
            @PathVariable Long restaurentId,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        List<Food> foods=foodService.getRestaurantsFood(restaurentId,vagetarian,seasonal,nonveg,food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}
