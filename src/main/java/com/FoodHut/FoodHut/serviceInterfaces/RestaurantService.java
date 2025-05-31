package com.FoodHut.FoodHut.serviceInterfaces;

import com.FoodHut.FoodHut.dto.RestaurantDto;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.dto.request.CreateRestaurantRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    Restaurant updateRestaurant(Long RestaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    void deleteReastaurant(Long restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(Long id) throws Exception;

    Restaurant getRestaurantByUserId(Long userId) throws Exception;

    RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;

    Restaurant UpdateRestaurantStatus(Long id)throws Exception;

}
