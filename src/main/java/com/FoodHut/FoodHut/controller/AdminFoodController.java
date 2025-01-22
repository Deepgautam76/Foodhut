package com.FoodHut.FoodHut.controller;
import com.FoodHut.FoodHut.model.Food;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.CreateFoodRequest;
import com.FoodHut.FoodHut.response.MessageResponse;
import com.FoodHut.FoodHut.serviceInterfaces.FoodService;
import com.FoodHut.FoodHut.serviceInterfaces.RestaurantService;
import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    /**
    * API end-point for creation food
    */
    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody CreateFoodRequest req,
                                        @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food=foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    /**
    * API end-point for delete food
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id,
                                        @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("food deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    /**
    * API end-point for updating food availability
    * */
    @PutMapping("/{id}/availability")
    public ResponseEntity<?> updateFoodAvailability(@PathVariable Long id,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailibilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
