package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    /**
     * This is the custom query implementation
     * This is called (JPQL)-Java Persistence Query Language
     */
    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query, '%')) " +
            "OR lower(r.cuisineType) LIKE lower(concat('%',:query, '%'))")
    List<Restaurant> findBySearchQuery(String query);

    /**
     * This is also a Custom query implementation
     * But this auto implemented by Spring
     */
    Restaurant findByOwnerId(Long userId);

}
