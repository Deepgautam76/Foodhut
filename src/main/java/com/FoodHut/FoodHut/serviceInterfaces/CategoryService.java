package com.FoodHut.FoodHut.serviceInterfaces;

import com.FoodHut.FoodHut.model.FoodCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryService {

    FoodCategory createCategory(String name,Long userId) throws Exception;

    List<FoodCategory> findCategoryByRestaurantId(Long id) throws Exception;

    FoodCategory findCategoryById(Long id) throws Exception;

}
