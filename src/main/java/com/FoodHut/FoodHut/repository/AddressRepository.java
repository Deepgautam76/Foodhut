package com.FoodHut.FoodHut.repository;

import com.FoodHut.FoodHut.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}