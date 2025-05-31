package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.config.JwtProvider;
import com.FoodHut.FoodHut.enums.USER_ROLE;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.dto.request.LoginRequest;
import com.FoodHut.FoodHut.dto.request.SignupRequest;
import com.FoodHut.FoodHut.dto.response.AuthResponse;
import com.FoodHut.FoodHut.confingService.CustomerUserDetailsService;
import com.FoodHut.FoodHut.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * Public API end-point
 * For Signup and SignIn
 * Anyone can hit "/auth" end-point
 * */

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private AuthService authService;

    /**
     * API end-point for registering the user(SignUp)
     * */
    @Tag(name="Create user api end point",description = "you can create user")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest user) throws Exception {

        /* Create user and card for user in a database*/
        User saveNewUser=authService.createUser(user);

        /* Set the Email and Password in securityContextHolder*/
        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /* Call the jwtProvider for JWT token generation */
        String jwt=jwtProvider.generateToken(authentication);

        /* Send a message to user in the form of AuthResponse */
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(saveNewUser.getRole());

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    /**
     * API end-point for login user
     **/
    @Tag(name="Login user api end-point",description = "you can login here")
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(
            @RequestBody LoginRequest request) throws Exception {
        String username=request.getEmail();
        String password=request.getPassword();

        /*
         * Here is the check the credential (Email=UserName, Password).
         * Pass credentials to authentication, provide fetch the user_details
         * And verify them
         */
        Authentication authentication=authenticate(username,password);

        /* Extract a role from authentication Object */
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        /* Here Call Generate the JWT Token */
        String jwt=jwtProvider.generateToken(authentication);

        /* Send the Message in the form of authResponse */
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    /**
     * This is a Custom "AuthenticationManager"
     * Check the email and password
     * To Stored Email and password in Database
     */
    private Authentication authenticate(String username, String password) throws Exception {
        /* Fetch user by the username from a database */
        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);
        if (userDetails==null){
            throw new Exception("invalid user email...");
        }
        /* Checking the password */
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("invalid password...");
        }

         /*
         * If everything is correct, then pass the "userDetails" to
         * the "authenticationManager" that verify the user credential
         * And return the "isAuthenticated" method if everything correct
         * Return true otherwise return false not authenticated
         */
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
