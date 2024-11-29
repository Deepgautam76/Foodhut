package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
