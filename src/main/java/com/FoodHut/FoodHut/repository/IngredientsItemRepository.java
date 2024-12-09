package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsItemRepository extends JpaRepository<IngredientsItem,Long> {
    List<IngredientsItem> findByRestaurantId(Long id);

}
