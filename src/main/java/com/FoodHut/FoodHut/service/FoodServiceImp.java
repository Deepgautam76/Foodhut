package com.FoodHut.FoodHut.service;
import com.FoodHut.FoodHut.model.Food;
import com.FoodHut.FoodHut.model.FoodCategory;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.repository.FoodRepository;
import com.FoodHut.FoodHut.dto.request.CreateFoodRequest;
import com.FoodHut.FoodHut.serviceInterfaces.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;


    /**
     Here is implementation of createFood method
     And also saved food in a restaurant
     **/
    @Override
    public Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant) {
        Food food=new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImage(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        Food savedFood= foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    /**
       Delete food by foodId method implementation
     */
    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarain,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) {

        List<Food> foods=foodRepository.findByRestaurantId(restaurantId);

        if(isVegitarain){
            foods=filterByVegetarian(foods,isVegitarain);
        }
        if(isNonveg){
            foods=filterByNonveg(foods,isNonveg);
        }
        if(isSeasonal){
            foods=filterBySeasonal(foods, isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.isEmpty()){
            foods=filterByCategory(foods,foodCategory);
        }

        return foods;
    }

    /**
     * Food filter by Category
     */
    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food ->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    /**
     * Food filter by Seasonal
     */
    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food ->
                food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    /**
     * Food filter by Non-veg
     */
    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food ->
                !food.isVegetarian()).collect(Collectors.toList());
    }

    /**
     * Food filter by Vegetarian
     */
    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarain) {
        return foods.stream().filter(food ->
                food.isVegetarian()==isVegitarain).collect(Collectors.toList());
    }

    /**
     * Search the food
     * by passing the keyword
     * */
    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    /**
     * Search food by foodId
     * */
    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood=foodRepository.findById(foodId);

        if(optionalFood.isEmpty()){
            throw  new Exception("food not exist...");
        }
        return optionalFood.get();
    }

    /**
     * Update stock in stock or out of stock
     * */
    @Override
    public Food updateAvailibilityStatus(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
