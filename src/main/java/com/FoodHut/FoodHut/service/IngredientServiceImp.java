package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.IngredientsCategory;
import com.FoodHut.FoodHut.model.IngredientsItem;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.repository.IngredientCategoryRepository;
import com.FoodHut.FoodHut.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientService {

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;


    @Override
    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientsCategory category=new IngredientsCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientsCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> opt=ingredientCategoryRepository.findById(id);

        if(opt.isEmpty()){
            throw  new Exception("ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {

        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        return null;
    }

    @Override
    public List<IngredientsCategory> findRestaurantIngredients(Long restaurantId) {
        return null;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        return null;
    }
}
