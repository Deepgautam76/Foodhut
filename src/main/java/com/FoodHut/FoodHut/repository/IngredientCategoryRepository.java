package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientsCategory,Long> {
    List<IngredientsCategory> findByRestaurantId(Long id);
}
