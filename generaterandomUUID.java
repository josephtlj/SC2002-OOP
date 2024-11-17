import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class generaterandomUUID {
    public  static void main(String[] args){
        for (int i=0; i<10; i++){
            // UUID variable = UUID.randomUUID();
            // System.out.println(variable);
            System.out.printf("Staff #%s\n", i);
            generateStuff();
        }
    }

    public static void generateStuff(){
        byte[] salt = generateSalt();
        String csvSalt = Base64.getEncoder().encodeToString(salt);
            String hashPasword = hashPassword("password", salt);
            System.out.println(hashPasword);
            System.out.println(csvSalt);
    }

    // SUPPORTING METHODS
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (Exception e) {
            throw new RuntimeException("Error generating salt!", e);
        }
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password!", e);
        }
    }
}
