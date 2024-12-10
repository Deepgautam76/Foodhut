package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.IngredientsCategory;
import com.FoodHut.FoodHut.request.IngredientCategoryRequest;
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

        IngredientsCategory item=ingredientService.createIngredientCategory(req.getName(),req.getRestaurentId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


}
