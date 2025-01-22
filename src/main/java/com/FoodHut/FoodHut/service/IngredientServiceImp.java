package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.IngredientsCategory;
import com.FoodHut.FoodHut.model.IngredientsItem;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.repository.IngredientCategoryRepository;
import com.FoodHut.FoodHut.repository.IngredientsItemRepository;
import com.FoodHut.FoodHut.serviceInterfaces.IngredientService;
import com.FoodHut.FoodHut.serviceInterfaces.RestaurantService;
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

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category=findIngredientCategoryById(categoryId);

        IngredientsItem item=new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredient=ingredientsItemRepository.save(item);
        category.getIngredientsItem().add(ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> optionalIngredientsItem=ingredientsItemRepository.findById(id);

        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("ingredient not found");
        }

        IngredientsItem ingredientsItem=optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientsItemRepository.save(ingredientsItem);
    }
}
