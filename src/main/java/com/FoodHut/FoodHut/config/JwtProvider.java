package com.FoodHut.FoodHut.config;

import com.FoodHut.FoodHut.config.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    public static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthrities(authorities);

        //Here is the build JWT token
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
    }

    //Extract email form JWT Token
    public String getEmailFromJwtToken(String jwt) {
       try{
           jwt=jwt.substring(7);
           Claims claims=Jwts.parser()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(jwt)
                   .getPayload();
           return String.valueOf(claims.get("email"));

       }catch (Exception e){
           throw new IllegalArgumentException("Invalid JWT token !"+e);
       }
    }

    private String populateAuthrities(Collection<? extends GrantedAuthority> authorities)  {
        Set<String> auths=new HashSet<>();
        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return auths.toString();
    }
}
//
//
//package com.FoodHut.FoodHut.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class JwtProvider {
//
//    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
//
//    // Generate JWT Token
//    public String generateToken(Authentication auth) {
//        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//        String roles = populateAuthorities(authorities);
//
//        // Build the JWT token
//        return Jwts.builder()
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + 86400000)) // 24 hours expiration
//                .claim("email", auth.getName())
//                .claim("authorities", roles)
//                .signWith(key)
//                .compact();
//    }
//
//    // Extract email from JWT Token
//    public String getEmailFromJwtToken(String jwt) {
//        if (jwt.startsWith("Bearer ")) {
//            jwt = jwt.substring(7); // Remove 'Bearer ' prefix
//        }
//        Claims claims = Jwts.parser()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(jwt)
//                .getBody(); // Extract claims
//        return String.valueOf(claims.get("email"));
//    }
//
//    // Populate authorities into a string
//    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        Set<String> auths = new HashSet<>();
//        for (GrantedAuthority authority : authorities) {
//            auths.add(authority.getAuthority());
//        }
//        return String.join(",", auths); // Return roles as a comma-separated string
//    }
//}
//
