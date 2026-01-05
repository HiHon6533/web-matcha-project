
package test;

import org.mindrot.jbcrypt.BCrypt;

public class GenPass {
    public static void main(String[] args){
        String rawPassword = "user01";
        System.out.println("Password hased: " + BCrypt.hashpw(rawPassword, BCrypt.gensalt()));
    }
}
