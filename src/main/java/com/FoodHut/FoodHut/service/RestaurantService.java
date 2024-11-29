package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.CreateRestaurantRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long RestaurantId,CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteReastaurant(Long restaurantId) throws Exception;
}
