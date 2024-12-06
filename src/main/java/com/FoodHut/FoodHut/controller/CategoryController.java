package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.FoodCategory;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.service.CategoryService;
import com.FoodHut.FoodHut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody FoodCategory category,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        FoodCategory createdCategory=categoryService.createCategory(category.getName(),user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


}
