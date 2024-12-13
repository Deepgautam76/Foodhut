package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByCustomerId(Long userId);
}
