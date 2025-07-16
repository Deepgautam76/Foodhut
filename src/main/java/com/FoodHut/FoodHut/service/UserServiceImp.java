package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.jwtinfo.JwtUtilities;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.UserRepository;
import com.FoodHut.FoodHut.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtUtilities.extractEmail(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found by this "+email));
    }

    /**
     * find all users that present in database
     */
    @Override
    public List<User> findAllUsers(){
        return  userRepository.findAll();
    }

}
