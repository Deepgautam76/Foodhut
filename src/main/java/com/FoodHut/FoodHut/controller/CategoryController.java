package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.FoodCategory;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.serviceInterfaces.CategoryService;
import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * API end-point creates foodCategory
     * */
    @PostMapping("/admin/category")
    public ResponseEntity<?> createCategory(
            @RequestBody FoodCategory category,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        FoodCategory createdCategory=categoryService.createCategory(category.getName(),user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * API end point find restaurant by category
     **/
    @GetMapping("/category/restaurant")
    public ResponseEntity<?> getRestaurantCategory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<FoodCategory> categories=categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }

}
