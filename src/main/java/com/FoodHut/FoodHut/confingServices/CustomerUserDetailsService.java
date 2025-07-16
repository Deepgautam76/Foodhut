package com.FoodHut.FoodHut.confingServices;

import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implement the user detailed service
 * For fetching the User By Email
 * And Return the userDetailed Object
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found by this email "+username));
        System.out.println("Yha tak ayi request:"+user);
         return new MyUserDetails(user);
    }
}
