package com.FoodHut.FoodHut.config;

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

    private final SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth) throws Exception{
        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthrities(authorities);

        //Here is the build JWT token
        String jwt= Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
        return jwt;
    }
    public String getEmailFromJwtToken(String jwt) throws Exception{
        jwt=jwt.substring(7);
        Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getPayload();

        String email=String.valueOf(claims.get("email"));
        return email;
    }
    private String populateAuthrities(Collection<? extends GrantedAuthority> authorities) throws Exception {
        Set<String> auths=new HashSet<>();
        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return null;
    }
}
