package com.FoodHut.FoodHut.serviceInterfaces;

import com.FoodHut.FoodHut.model.Food;
import com.FoodHut.FoodHut.model.FoodCategory;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.request.CreateFoodRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodService {
    Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    List<Food> getRestaurantsFood(Long restaurantId,
                                  boolean isVegitarain,
                                  boolean isNonveg,
                                  boolean isSeasonal,
                                  String foodCategory
    );

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId) throws Exception;

    Food updateAvailibilityStatus(Long foodId) throws  Exception;

}
