package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.jwtinfo.JwtUtilities;
import com.FoodHut.FoodHut.enums.USER_ROLE;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.dto.request.LoginRequest;
import com.FoodHut.FoodHut.dto.request.SignupRequest;
import com.FoodHut.FoodHut.dto.response.AuthResponse;
import com.FoodHut.FoodHut.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


/**
 * Public API end-point
 * For Signup and SignIn
 * Anyone can hit "/auth" end-point
 * */

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    /**
     * API end-point for registering the user(SignUp)
     * */
    @Tag(name="Create user api end point",description = "Create user")
    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler(@RequestBody SignupRequest request) throws Exception {

        if(request==null || request.getEmail().isEmpty()){
            throw new Exception("Not enter blank user email or password");
        }

        if(authService.isExist(request.getEmail())){
            throw new Exception("User already exist");
        }
        User saveNewUser=authService.createUser(request);

        return new ResponseEntity<>("Register success",HttpStatus.CREATED);
    }

    /**
     * API end-point for login user
     **/
    @Tag(name="Login user api end-point",description = "you can login here")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest request) throws Exception {

        if(request.getEmail().isBlank() || request.getPassword().isBlank()) {
            return new ResponseEntity<>("User email or password cannot be blank", HttpStatus.BAD_REQUEST);
        }

        // Authenticate the user before SingUp(Login)
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        String JwtToken=jwtUtilities.generateJwtToken(userDetails);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        /* Send the Message in the form of authResponse */
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(JwtToken);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
}
