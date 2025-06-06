package com.FoodHut.FoodHut.dto.response;

import com.FoodHut.FoodHut.enums.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
