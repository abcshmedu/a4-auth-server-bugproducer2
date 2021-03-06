package edu.hm.bugproducer.restAPI;

import edu.hm.bugproducer.Status.StatusMgnt;
import io.jsonwebtoken.*;

import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.hm.bugproducer.Status.MediaServiceResult.MSR_OK;
import static edu.hm.bugproducer.Status.MediaServiceResult.MSR_UNAUTHORIZED;

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
     * name of legit user.
     */
    private final String legitUser = "John Doe";
    /**
     * a legit password.
     */
    private final String getLegitpassword = "geheim";
    /**
     * map with user and his password.
     */
    private static final Map<String, String> USERKEYMAP = new HashMap<>(); //user //

    @Override
    public Pair<StatusMgnt, String> createToken(String user, String password) {
        Pair<StatusMgnt, String> result;
        if (user.equals(legitUser) && password.equals(getLegitpassword)) {
            if (USERKEYMAP.containsValue(user)) {
                Set<String> tokenSet = getKeysByValue(USERKEYMAP, user);
                String toCheck = tokenSet.iterator().next();
                if (TokenUtils.isNotExpired(toCheck)) {
                    result = new Pair<>(new StatusMgnt(MSR_OK,"ok"), toCheck);
                } else {
                    USERKEYMAP.remove(toCheck);
                    String token = TokenUtils.createToken(user, password);
                    USERKEYMAP.put(token, user);
                    result = new Pair<>(new StatusMgnt(MSR_OK,"ok"), token);
                }
            } else {
                String token = TokenUtils.createToken(user, password);
                USERKEYMAP.put(token, user);
                result = new Pair<>(new StatusMgnt(MSR_OK,"ok"),token);
            }

        } else {
            result = new Pair<>(new StatusMgnt(MSR_UNAUTHORIZED,"Wrong Login credentials"),"");

        }

        return result;

    }

    @Override
    public Pair<StatusMgnt, String> verifyToken(String token) {
        Pair<StatusMgnt, String> result = new Pair<>(new StatusMgnt(MSR_UNAUTHORIZED,"unauthorized user"), null);
        if (USERKEYMAP.containsKey(token)) {
            if (TokenUtils.isNotExpired(token)) {

                Map<String, Object> headerClaims = new HashMap();
                headerClaims.put("type", Header.JWT_TYPE);
                String compactJws = null;
                try {
                    compactJws = Jwts.builder()
                            .setSubject(USERKEYMAP.get(token))
                            .setHeader(headerClaims)
                            .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                            .compact();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {


                    result = new Pair<>(new StatusMgnt(MSR_OK,"ok"), compactJws);
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
     * getKeyByValue method.
     * filters from the map the keys by using the value
     * @param map map that contains key and value
     * @param value value you want the key of
     * @param <T> type of key
     * @param <E> type of value
     * @return filters the keys into a set
     */
    private static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}

