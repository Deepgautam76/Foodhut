package com.FoodHut.FoodHut.dto.request;

import com.FoodHut.FoodHut.model.Address;
import com.FoodHut.FoodHut.dto.ContactInformation;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private String name;
    private String description;
    private String cuisineType;
    private AddressRequest address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}
