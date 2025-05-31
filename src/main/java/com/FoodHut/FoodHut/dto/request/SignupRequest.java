package com.FoodHut.FoodHut.dto.request;

import com.FoodHut.FoodHut.enums.USER_ROLE;
import lombok.Data;

@Data
public class SignupRequest {
    private String fullName;
    private String email;
    private String password;
    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

}