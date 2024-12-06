package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<FoodCategory,Long> {

    List<FoodCategory> findByRestaurantId(Long id);
}
