package com.FoodHut.FoodHut.dto.request;

import com.FoodHut.FoodHut.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;

}
