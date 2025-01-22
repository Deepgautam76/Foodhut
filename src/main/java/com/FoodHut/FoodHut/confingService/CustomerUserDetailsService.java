package com.FoodHut.FoodHut.confingService;

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
        User user=userRepository.findByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found with email:"+username);
        }

//        USER_ROLE role=user.getRole();
//        List<GrantedAuthority> authorities=new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role.toString()));
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);

         return new UserPrincipal(user);
    }
}
