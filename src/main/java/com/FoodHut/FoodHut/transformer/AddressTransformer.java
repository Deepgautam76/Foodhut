package com.FoodHut.FoodHut.transformer;

import com.FoodHut.FoodHut.dto.request.AddressRequest;
import com.FoodHut.FoodHut.model.Address;

public class AddressTransformer {
    public static Address AddressRequestToAddress(AddressRequest addressRequest){
        return Address.builder()
                .city(addressRequest.getCity())
                .streetAddress(addressRequest.getStreetAddress())
                .country(addressRequest.getCountry())
                .postalCode(addressRequest.getPostalCode())
                .stateProvider(addressRequest.getStateProvider())
                .build();
    }
    public static AddressRequest AddressToAddressRequest(Address address){
        return AddressRequest.builder()
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .stateProvider(address.getStateProvider())
                .streetAddress(address.getStreetAddress())
                .build();
    }


}
