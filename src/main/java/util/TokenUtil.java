package util;

import java.util.UUID;
import java.util.Random;

public class TokenUtil {
    
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
    public static String tokenPass() {
        Random rd = new Random();
        int token = rd.nextInt(900000) + 100000;
        
        return String.valueOf(token);
    }
}