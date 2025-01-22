package com.FoodHut.FoodHut.service;

import com.FoodHut.FoodHut.model.Cart;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.CartRepository;
import com.FoodHut.FoodHut.repository.UserRepository;
import com.FoodHut.FoodHut.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("This email already used, use other email");
        }

        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveNewUser = userRepository.save(createUser);

        Cart cart = new Cart();
        cart.setCustomer(saveNewUser);
        cartRepository.save(cart);
        return saveNewUser;
    }
}

