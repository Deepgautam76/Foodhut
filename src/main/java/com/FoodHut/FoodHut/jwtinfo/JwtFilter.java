package com.FoodHut.FoodHut.jwtinfo;


import com.FoodHut.FoodHut.confingServices.CustomerUserDetailsService;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

/**
 * Input takes the jwt token then validate them
 * and create the Authentication Object set the username and roles
 * "Authentication" object set to the "securityContext" holder
 * Check the "authoriseRequest" role and url
 * */
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private CustomerUserDetailsService userDetailsService;
    private final SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
           String authHeader=request.getHeader(JwtConstant.JWT_HEADER);
           String jwtToken=null;
           String userEmail=null;

            //Step1: - Extract token and email
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                userEmail = jwtUtilities.extractEmail(jwtToken);

                //Step2: -Validate and authenticate
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                    if (userDetails != null && jwtUtilities.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userEmail, null, userDetails.getAuthorities());

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        filterChain.doFilter(request,response);
    }
}
