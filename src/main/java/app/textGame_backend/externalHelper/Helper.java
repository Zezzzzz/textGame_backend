package app.textGame_backend.externalHelper;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Helper {
    /**
     * Hashes a given string with SHA512 hashing function
     * @param toHash the string to be hashed
     * @return a string containing the 128 hexadecimal digits of the hashed string
     */
    public static String hashStringWithSHA512(String toHash) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(toHash.getBytes("utf8"));
            result = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
