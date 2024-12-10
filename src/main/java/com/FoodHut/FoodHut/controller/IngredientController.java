package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.IngredientsCategory;
import com.FoodHut.FoodHut.model.IngredientsItem;
import com.FoodHut.FoodHut.request.IngredientCategoryRequest;
import com.FoodHut.FoodHut.request.IngredientRequest;
import com.FoodHut.FoodHut.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    //API end-point for creation of IngredientCategory
    @PostMapping("/category")
    public ResponseEntity<?> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
            ) throws Exception {

        IngredientsCategory item=ingredientService.createIngredientCategory(req.getName(),req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    //API end-point for creation of Ingredient
    @PostMapping()
    public ResponseEntity<?> createIngredient(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientsItem item=ingredientService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item,HttpStatus.CREATED);
    }



}
