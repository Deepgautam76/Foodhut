package com.FoodHut.FoodHut.controller;

import com.FoodHut.FoodHut.config.JwtProvider;
import com.FoodHut.FoodHut.model.Cart;
import com.FoodHut.FoodHut.model.USER_ROLE;
import com.FoodHut.FoodHut.model.User;
import com.FoodHut.FoodHut.repository.CartRepository;
import com.FoodHut.FoodHut.repository.UserRepository;
import com.FoodHut.FoodHut.request.LoginRequest;
import com.FoodHut.FoodHut.response.AuthResponse;
import com.FoodHut.FoodHut.service.CustomerUserDetailsService;
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

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final CartRepository cartRepository;

    @Autowired
    public AuthController(JwtProvider jwtProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          CustomerUserDetailsService customerUserDetailsService,
                          CartRepository cartRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerUserDetailsService = customerUserDetailsService;
        this.cartRepository = cartRepository;
    }



    //Controller for registering the user(SignUp)
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        //If email is already exists
        User isEmailExist=userRepository.findByEmail(user.getEmail());
        if (isEmailExist!=null){
            throw new Exception("This email already used in an other account");
        }

        //Create new user
        User createUser=new User();
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        //Save the user in DB
        User saveNewUser=userRepository.save(createUser);

        //Create Cart for user
        Cart cart=new Cart();
        cart.setCustomer(saveNewUser);
        cartRepository.save(cart);

        //Verify credential and Generate JWT token
        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        //Set the message register success
        authResponse.setMessage("Register success");
        authResponse.setRole(saveNewUser.getRole());

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    //Controller for login user
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) throws Exception {

        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        //Here is the check the authentication
        Authentication authentication=authenticate(username,password);

        //Extract the role
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        //Here is generated the Token
        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        //Set the message register success
        authResponse.setMessage("Register success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) throws Exception {

        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);

        //Checking the email
        if (userDetails==null){
            throw new Exception("invalid username...");
        }
        //Checking the password
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("invalid password...");
        }

        //If everything is correct so that User authenticated
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
//
//
//package com.FoodHut.FoodHut.controller;
//
//import com.FoodHut.FoodHut.config.JwtProvider;
//import com.FoodHut.FoodHut.model.Cart;
//import com.FoodHut.FoodHut.model.USER_ROLE;
//import com.FoodHut.FoodHut.model.User;
//import com.FoodHut.FoodHut.repository.CartRepository;
//import com.FoodHut.FoodHut.repository.UserRepository;
//import com.FoodHut.FoodHut.request.LoginRequest;
//import com.FoodHut.FoodHut.response.AuthResponse;
//import com.FoodHut.FoodHut.service.CustomerUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final JwtProvider jwtProvider;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final CustomerUserDetailsService customerUserDetailsService;
//    private final CartRepository cartRepository;
//
//    @Autowired
//    public AuthController(JwtProvider jwtProvider,
//                          UserRepository userRepository,
//                          PasswordEncoder passwordEncoder,
//                          CustomerUserDetailsService customerUserDetailsService,
//                          CartRepository cartRepository) {
//        this.jwtProvider = jwtProvider;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.customerUserDetailsService = customerUserDetailsService;
//        this.cartRepository = cartRepository;
//    }
//
//    // Register a new user
//    @PostMapping("/signup")
//    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
//        // Check if email already exists
//        if (userRepository.findByEmail(user.getEmail()) != null) {
//            throw new Exception("This email is already used in another account.");
//        }
//
//        // Create new user and encode password
//        User createUser = new User();
//        createUser.setEmail(user.getEmail());
//        createUser.setFullName(user.getFullName());
//        createUser.setRole(user.getRole());
//        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        // Save the new user
//        User savedUser = userRepository.save(createUser);
//
//        // Create a cart for the user
//        Cart cart = new Cart();
//        cart.setCustomer(savedUser);
//        cartRepository.save(cart);
//
//        // Authenticate the user
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Generate JWT token
//        String jwt = jwtProvider.generateToken(authentication);
//
//        // Build response
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setJwt(jwt);
//        authResponse.setMessage("Registration successful");
//        authResponse.setRole(savedUser.getRole());
//
//        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
//    }
//
//    // Login a user and return a JWT token
//    @PostMapping("/signin")
//    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) throws Exception {
//        // Authenticate user
//        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//
//        // Extract role from authentication
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
//
//        // Generate JWT token
//        String jwt = jwtProvider.generateToken(authentication);
//
//        // Build response
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setJwt(jwt);
//        authResponse.setMessage("Login successful");
//        authResponse.setRole(USER_ROLE.valueOf(role));
//
//        return new ResponseEntity<>(authResponse, HttpStatus.OK);
//    }
//
//    // Authenticate user credentials
//    private Authentication authenticate(String username, String password) throws Exception {
//        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
//
//        // Check if user exists
//        if (userDetails == null) {
//            throw new Exception("Invalid username.");
//        }
//
//        // Check password
//        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new Exception("Invalid password.");
//        }
//
//        // Return authenticated token
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//}
//
