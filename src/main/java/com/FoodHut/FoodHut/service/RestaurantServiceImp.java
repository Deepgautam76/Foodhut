package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.dto.RestaurantDto;
import com.FoodHut.FoodHut.model.Address;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.AddressRepository;
import com.FoodHut.FoodHut.repository.RestaurantRepository;
import com.FoodHut.FoodHut.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;


    //Here is the Creation of Restaurant
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address=addressRepository.save(req.getAddress());

        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    //Here is the update restaurant
    @Override
    public Restaurant updateRestaurant(Long RestaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant=findRestaurantById(RestaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getAddress()!=null){
            restaurant.setAddress(updatedRestaurant.getAddress());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getContactInformation()!=null){
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteReastaurant(Long restaurantId) throws Exception {

    }

    @Override
    public List<Restaurant> getallRestaurant() {
        return null;
    }

    @Override
    public List<Restaurant> searchRestaurant() {
        return null;
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        return null;
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        return null;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        return null;
    }

    @Override
    public Restaurant UpdateRestaurantStatus(Long id) throws Exception {
        return null;
    }
}
