package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<FoodCategory,Long> {
}
