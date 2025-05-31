package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.dto.RestaurantDto;
import com.FoodHut.FoodHut.model.Address;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.AddressRepository;
import com.FoodHut.FoodHut.repository.RestaurantRepository;
import com.FoodHut.FoodHut.repository.UserRepository;
import com.FoodHut.FoodHut.dto.request.CreateRestaurantRequest;
import com.FoodHut.FoodHut.serviceInterfaces.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * A restaurant creation method implementation
     * */
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

    /**
     * Update restaurant method implementation
     * */
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

    /***
     * Restaurant delete method implementation
     */
    @Override
    public void deleteReastaurant(Long restaurantId) throws Exception {

        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    /**
     * All restaurants fetch method
     */
    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    /**
     * Search restaurant by keyword, method implementation
     */
    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    /**
     * Find a restaurant By id
     */
    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("restaurant not found with id of "+id);
        }
        return opt.get();
    }
    /**
     * Find a restaurant by its userid
     */
    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("restaurant not found with owner id of "+userId);
        }
        return restaurant;
    }

    /**
     * Added a favourite restaurant
     * If that already favourite then remove them
     */
    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant=findRestaurantById(restaurantId);

        RestaurantDto dto=new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImage(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setRestaurantId(restaurantId);

        boolean isfavorited=false;
        List<RestaurantDto> favorites=user.getFavorites();
        for(RestaurantDto favorite: favorites){
            if(favorite.getRestaurantId().equals(restaurantId)){
                isfavorited=true;
                break;
            }
        }
        /*
          If the restaurant is already favorite, remove it otherwise, add it to favorites
         */
        if(isfavorited){
            favorites.removeIf(favorite->favorite.getRestaurantId().equals(restaurantId));
        }else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    /**
     * Method for updating restaurant open and close status.
     */
    @Override
    public Restaurant UpdateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
