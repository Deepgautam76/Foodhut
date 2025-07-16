package com.FoodHut.FoodHut.jwtinfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

/**
 * When any user creates an account
 * Or log in an account,
 * This method call for generation of JWT token
 */
@Service
public class JwtUtilities {

    public static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    /**
     * JWT Token Generation Process
     * Take the input as the "Authentication" object
     */
    public String generateJwtToken(UserDetails userDetails){
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+100*60*60))
                .claim("authorities",roles)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
    * JwtToken Extraction logic
    */
    public Claims extractAllClaims(String JwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(JwtToken)
                .getBody();
    }
    public String  extractEmail(String jwtToken){
        return extractAllClaims(jwtToken).getSubject();
    }
    public boolean validateToken(String jwtToken,UserDetails dbUserDetails){
        String tokenEmail=extractEmail(jwtToken);
        return tokenEmail.equals(dbUserDetails.getUsername())&& isTokenNotExpired(jwtToken);
    }
    public boolean  isTokenNotExpired(String jwtToken){
        Date expiration=extractAllClaims(jwtToken).getExpiration();
        return expiration.after(new Date());
    }
}
