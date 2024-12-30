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


/**
 * Public API end-point
 * For Signup and SignIn
 * Anyone can hit "/auth" end-point
 * */

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



    /**
     * API end-point for registering the user(SignUp)
     * */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        /**
         * If Email already Exists in DB
         * */
        User isEmailExist=userRepository.findByEmail(user.getEmail());
        if (isEmailExist!=null){
            throw new Exception("This email already used, use other email");
        }

        /**
         * Create new user
         * */
        User createUser=new User();
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        /**
         * Save The User in DB
         * */
        User saveNewUser=userRepository.save(createUser);

        /**
         * Create Cart for New User
         * */
        Cart cart=new Cart();
        cart.setCustomer(saveNewUser);
        cartRepository.save(cart);

        /**
        * Set the Email and Password in securityContextHolder
        */
        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        /**
        * Call the jwtProvider for JWT token generation
        **/
        String jwt=jwtProvider.generateToken(authentication);

        /**
        * Send Message to User for Register success
        * In the form of AuthResponse Model
        * */
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(saveNewUser.getRole());

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    /**
     * API end-point for login user
     **/
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(
            @RequestBody LoginRequest loginRequest) throws Exception {

        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        /**
         * Here is the check the authentication
         * */
        Authentication authentication=authenticate(username,password);

        /**
         * Extract the role
         * */
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        /**
        * Here Call Generate the JWT Token
        */
        String jwt=jwtProvider.generateToken(authentication);

        /**
        * Send the Message for success login
        * In the form of authResponse
        **/
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) throws Exception {

        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);

        /**
         * Checking the email
         * */
        if (userDetails==null){
            throw new Exception("invalid username...");
        }
        /**
         * Checking the password
         * */
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("invalid password...");
        }

        /**
         * If everything is correct so that User authenticated
         * */
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
