package com.FoodHut.FoodHut.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private String streetAddress;
    private String  city;
    private String stateProvider;
    private int postalCode;
    private String country;
}
