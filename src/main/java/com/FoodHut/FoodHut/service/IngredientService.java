package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.IngredientsItem;
import com.FoodHut.FoodHut.model.IngredientsCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientService {

    IngredientsCategory createIngredientCategory(String name,Long restaurantId)throws Exception;

    IngredientsCategory findIngredientCategoryById(Long id)throws Exception;

    List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)throws Exception;

    List<IngredientsCategory> findRestaurantIngredients(Long restaurantId);

    IngredientsItem updateStock(Long id)throws Exception;


}