package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.model.IngredientsCategory;
import com.FoodHut.FoodHut.model.IngredientsItem;
import com.FoodHut.FoodHut.request.IngredientCategoryRequest;
import com.FoodHut.FoodHut.request.IngredientRequest;
import com.FoodHut.FoodHut.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //API end-point for stock update
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item=ingredientService.updateStock(id);
        return new ResponseEntity<>(item,HttpStatus.CREATED);
    }

    //API end-point for getRestaurantIngredient
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> items=ingredientService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items,HttpStatus.CREATED);
    }

    //API end-point for getRestaurantIngredientCategory
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<?> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsCategory> items=ingredientService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items,HttpStatus.CREATED);
    }


}
