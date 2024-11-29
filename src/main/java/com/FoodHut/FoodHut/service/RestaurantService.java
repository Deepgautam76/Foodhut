package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.dto.RestaurantDto;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.request.CreateRestaurantRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long RestaurantId,CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteReastaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getallRestaurant();

    public  List<Restaurant> searchRestaurant();

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;

    public Restaurant UpdateRestaurantStatus(Long id)throws Exception;

}
