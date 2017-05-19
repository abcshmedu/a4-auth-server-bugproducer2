package edu.hm.bugproducer.restAPI;

import org.apache.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TokenUtils {

    /**
     * Creates a random TokenUtils
     *
     * @param userName     username
     * @param password     password
     * @param creationDate date when he does the login
     * @return random token
     */
    public static String createToken(String userName, String password, String creationDate) {
        // last 19 numbers
        String currTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String keySource = userName + password + creationDate;

        // generate TokenUtils
        byte[] tokenByte;
        new Base64(true);
        tokenByte = Base64.encodeBase64(keySource.getBytes());

        return new String(tokenByte) + currTime;
    }
}
