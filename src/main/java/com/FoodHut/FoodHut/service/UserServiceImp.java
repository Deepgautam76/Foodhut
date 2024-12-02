package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.config.JwtProvider;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email=jwtProvider.getEmailFromJwtToken(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);

        if (user==null){
            throw new Exception("user not found");
        }
        return user;
    }
}
