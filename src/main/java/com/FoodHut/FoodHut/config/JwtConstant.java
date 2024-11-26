package com.FoodHut.FoodHut.config;

import java.security.SecureRandom;

public class JwtConstant {

//    public static final String SECRET_KEY ="seeiofahoffnlkajnoicrpqjworihorjSANIHOSIOFAJSOIJLKSDOINGLEFKSNGOIASJPO";


//    //Generate the random 256-bit secrete key
    public static final String SECRET_KEY = generateSecretKey();

    private static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(key);
        return new String(key);
    }
    public static final String JWT_HEADER="Authorization";

}
