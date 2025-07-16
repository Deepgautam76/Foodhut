package com.FoodHut.FoodHut.confingServices;

import com.FoodHut.FoodHut.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MyUserDetails class main working of the to implement "UserDetails"
 */

public class MyUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> roles=new ArrayList<>();
    public MyUserDetails(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
        if(user.getRole()!=null){
         this.roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
        }
    }

    /**
     * This method get role from the user and the set them
     * Into the security context holder
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Role granted auth ko call kya:"+roles.get(0));
        return roles;
    }

    /**
     * Returns the password used to authenticate the user.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username used to authenticate the user.
     * Cannot return null
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
