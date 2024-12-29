package com.FoodHut.FoodHut.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

//Input takes the jwt token then validate them
public class JwtTokenValidator extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt=request.getHeader(JwtConstant.JWT_HEADER);

        /*
        * JWT Token look like -> "Bearer token"
        * Extract token remove the Bearer part from String
        */
        if (jwt!=null && jwt.startsWith("Bearer ")){
            jwt=jwt.substring(7);
            try {
                /*
                * Your key-string to convert SecretKey
                * For checking JWT token is valid or not and also check expiration time
                * Claims have the all Information about User(Email,Roles)
                */
                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims= Jwts.parser()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(jwt)
                                .getBody();
                /*
                * Extract the email and authorities(roles)
                * Extracted roles ROLE_CUSTOMER, ROLE_ADMIN set in GrantedAuthority List
                * */
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                /*
                * Creating an authentication object for manual authentication
                * Set then spring security context
                * */
                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                /*
                * Console spring security context
                */
                System.out.println("log of security context: "+SecurityContextHolder.getContext().getAuthentication());
            }catch (Exception e){
                throw new BadCredentialsException("invalid token ...");
            }
        }
        filterChain.doFilter(request,response);

    }
}
