package com.FoodHut.FoodHut.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
* When anyone creates an account
* Or log in an account, This process happened
*/
@Service
public class JwtProvider {

    public static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    /**
     * JWT Token Generation Process
     */
    public String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthrities(authorities);
        /**
         * Here is Build the JWT Token and set expiration time
         */
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
    }

    /**
    * Extract email form JWT Token
    */
    public String getEmailFromJwtToken(String jwt) {
       try{
           if (jwt != null && jwt.startsWith("Bearer "))
           {
                jwt = jwt.substring(7);
           } else
           {
                throw new IllegalArgumentException("JWT token is malformed, missing Bearer prefix.");
           }
           Claims claims=Jwts.parser()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(jwt)
                   .getBody();

           String email=claims.get("email",String.class);
           if (email == null)
           {
               throw new IllegalArgumentException("JWT does not contain an email claim.");
           }
           return email;

       } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("JWT token has expired.", e);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT signature.", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + e.getMessage(), e);
        }
    }

    private String populateAuthrities(Collection<? extends GrantedAuthority> authorities)  {
        Set<String> auths=new HashSet<>();
        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(", ",auths);
    }
}
