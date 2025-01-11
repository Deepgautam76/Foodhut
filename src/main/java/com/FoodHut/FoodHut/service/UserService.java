package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    List<User> findAllUsers();
}
