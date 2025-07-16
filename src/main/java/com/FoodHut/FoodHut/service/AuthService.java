package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.Cart;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.CartRepository;
import com.FoodHut.FoodHut.repository.UserRepository;
import com.FoodHut.FoodHut.dto.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest request) throws Exception {
        Optional<User> isEmailExist = userRepository.findByEmail(request.getEmail());
        if(isEmailExist.isPresent()){
            throw  new Exception("Email already exist enter other email");
        }

        User createUser = new User();
        createUser.setEmail(request.getEmail());
        createUser.setFullName(request.getFullName());
        createUser.setRole(request.getRole());
        createUser.setPassword(passwordEncoder.encode(request.getPassword()));
        User saveNewUser = userRepository.save(createUser);

        Cart cart = new Cart();
        cart.setCustomer(saveNewUser);
        cartRepository.save(cart);
        return saveNewUser;
    }

    public boolean isExist(String email) {
        return userRepository.existsByEmail(email);
    }
}

