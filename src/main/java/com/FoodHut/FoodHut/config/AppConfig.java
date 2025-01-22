package com.FoodHut.FoodHut.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {

    /**
    * User request for resource first Enters here for authorization
    * Here only those entering that has "JWT" token
    */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management-> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
                        .requestMatchers("/api/**")
                        .authenticated()
                        .anyRequest().permitAll()
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(crossConfigurationSource()));
        return http.build();
    }


    /**
     * This only for allow the same system frontend request
     */
    private CorsConfigurationSource crossConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg=new CorsConfiguration();

                cfg.setAllowedOrigins(
                        List.of(
                                "Http://localhost:3000"
                        ));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(List.of("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
