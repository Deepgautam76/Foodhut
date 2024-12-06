package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.FoodCategory;
import com.FoodHut.FoodHut.model.Restaurant;
import com.FoodHut.FoodHut.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantService restaurantService;


    //food-category method implementation
    @Override
    public FoodCategory createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(userId);
        FoodCategory category=new FoodCategory();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    //Find category by Restaurant
    @Override
    public List<FoodCategory> findCategoryByRestaurantId(Long id) throws Exception {
        return categoryRepository.findByRestaurantId(id);
    }

    //Find the category By id
    @Override
    public FoodCategory findCategoryById(Long id) throws Exception {
        Optional<FoodCategory> optionalFoodCategory=categoryRepository.findById(id);

        if(optionalFoodCategory.isEmpty()){
            throw new Exception("category not found");
        }
        return optionalFoodCategory.get();
    }
}
