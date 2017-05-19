package edu.hm.bugproducer.restAPI;

import org.apache.commons.codec.binary.Base64;

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

    /**
     * Checks if a token expired (5 Minutes)
     *
     * @param token
     * @return
     */
    public static boolean isValidToken(String token) {
        long oldTime = Long.parseLong(token.substring(token.length() - TIME_OFFSET));
        long currTime = System.currentTimeMillis();

        return currTime < oldTime + TIME_TO_LIFE;
    }
}
