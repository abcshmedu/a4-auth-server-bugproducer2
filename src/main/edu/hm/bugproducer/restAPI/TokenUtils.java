package edu.hm.bugproducer.restAPI;

import org.apache.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TokenUtils {

    private static final int TIME_OFFSET = 13;
    private static final int TIME_TO_LIFE = 300000;

    /**
     * Creates a random TokenUtils
     *
     * @param userName username
     * @param password password
     * @return random token
     */
    public static String createToken(String userName, String password) {
        // last 19 numbers
        long currTime = System.currentTimeMillis();
        String keySource = userName + password + currTime;

        // generate TokenUtils
        byte[] tokenByte;
        new Base64(true);
        tokenByte = Base64.encodeBase64(keySource.getBytes());

        return new String(tokenByte) + currTime;
    }

    public static boolean isValidToken(String token) {
        boolean isValid = false;
        long oldTime = Long.parseLong(token.substring(token.length() - TIME_OFFSET));
        long currTimed = System.currentTimeMillis();

        if (oldTime + TIME_TO_LIFE > currTimed) {
            System.err.println("Abgelaufen");
        } else {
            System.err.println("Still valid");
        }

        return true;
    }
}
