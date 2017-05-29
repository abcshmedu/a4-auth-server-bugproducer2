package edu.hm.bugproducer.restAPI;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import javafx.util.Pair;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_OK;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_UNAUTHORIZED;

/**
 * AuthService class.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public class
AuthServiceImpl implements AuthService {
    /**
     * name of a legitUser.
     */
    private final String legitUser = "John Doe";
    /**
     * a legit password.
     */
    private final String getLegitPassword = "geheim";
    /**
     * map that contains a user and his password.
     */
    private static final Map<String, String> USERKEYMAP = new HashMap<>(); //user //

    /**
     * createToken method.
     * takes the user and this password and compare it with a legit user name and a legit user password,
     * if the USERKEYMAP contains the user, it will put his token in a set and checks if it is expired or not
     * @param user name of user
     * @param password unique string choose by user
     * @return status code with token or message
     */
    @Override
    public Pair<MediaServiceResult, String> createToken(String user, String password) {
        Pair<MediaServiceResult, String> result;
        if (user.equals(legitUser) && password.equals(getLegitPassword)) {
            if (USERKEYMAP.containsValue(user)) {
                Set<String> tokenSet = getKeysByValue(USERKEYMAP, user);
                String toCheck = tokenSet.iterator().next();
                if (TokenUtils.isNotExpired(toCheck)) {
                    result = new Pair<>(MSR_OK, toCheck);
                } else {
                    USERKEYMAP.remove(toCheck);
                    String token = TokenUtils.createToken(user, password);
                    USERKEYMAP.put(token, user);
                    result = new Pair<>(MSR_OK, token);
                }
            } else {
                String token = TokenUtils.createToken(user, password);
                USERKEYMAP.put(token, user);
                result = new Pair<>(MSR_OK, token);
            }

        } else {
            result = new Pair<>(MSR_UNAUTHORIZED, "Du_kommst_hier_nicht_rein!!");

        }

        return result;

    }

    /**
     * verifyToken method.
     * checks if the token we get is in our USERKEYMAP and if it is expired and creating a jwt
     * @param token unique string
     * @return status code and null if we cant verify the token or status code with a jwt if it is okay
     */
    @Override
    public Pair<MediaServiceResult, Jwt> verifyToken(String token) {
        Pair<MediaServiceResult, Jwt> result = new Pair<>(MSR_UNAUTHORIZED, null);
        if (USERKEYMAP.containsKey(token)) {
            if (TokenUtils.isNotExpired(token)) {

                Key key = MacProvider.generateKey();
                Jwt jwt;

                Map<String, Object> headerClaims = new HashMap();
                headerClaims.put("type", Header.JWT_TYPE);
                String compactJws = Jwts.builder()
                        .setSubject(USERKEYMAP.get(token))
                        .setHeader(headerClaims)
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact();

                try {

                    jwt = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
                    result = new Pair<>(MSR_OK, jwt);
                    return result;
                } catch (SignatureException e) {
                    return result;
                }
            } else {
                USERKEYMAP.remove(token);
                return result;
            }
        } else {
            return result;
        }
    }

    /**
     * getKeysByValue method.
     * gets the key from a map just by using the user
     * @param map that contains key and value
     * @param value by that we get the key
     * @param <T> type of key
     * @param <E> type of value
     * @return filtered keys to a set
     */
    private static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}

