package edu.hm.bugproducer.restAPI;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_OK;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_UNAUTHORIZED;

public class
AuthServiceImpl implements AuthService {

    private final String legitUser = "John Doe";
    private final String getLegitpassword = "geheim";
    private final static Map<String, String> userKeyMap = new HashMap<>(); //user //

    @Override
    public Pair<MediaServiceResult, String> createToken(String user, String password) {
        Pair<MediaServiceResult, String> result;
        if (user.equals(legitUser) && password.equals(getLegitpassword)) {
            if (userKeyMap.containsValue(user)) {
                Set<String> tokenSet = getKeysByValue(userKeyMap, user);
                String toCheck = tokenSet.iterator().next();
                if (TokenUtils.isNotExpired(toCheck)) {
                    result = new Pair<>(MSR_OK, toCheck);
                } else {
                    userKeyMap.remove(toCheck);
                    String token = TokenUtils.createToken(user, password);
                    userKeyMap.put(token, user);
                    result = new Pair<>(MSR_OK, token);
                }
            } else {
                String token = TokenUtils.createToken(user, password);
                userKeyMap.put(token, user);
                result = new Pair<>(MSR_OK, token);
            }

        } else {
            result = new Pair<>(MSR_UNAUTHORIZED, "Du_kommst_hier_nicht_rein!!");

        }

        return result;

    }

    @Override
    public Pair<MediaServiceResult, String> verifyToken(String token) {
        Pair<MediaServiceResult, String> result = new Pair<>(MSR_UNAUTHORIZED, null);
        if (userKeyMap.containsKey(token)) {
            if (TokenUtils.isNotExpired(token)) {




                Map<String, Object> headerClaims = new HashMap();
                headerClaims.put("type", Header.JWT_TYPE);
                String compactJws = null;
                try {
                    compactJws = Jwts.builder()
                            .setSubject(userKeyMap.get(token))
                            .setHeader(headerClaims)
                            .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                            .compact();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {


                    result = new Pair<>(MSR_OK, compactJws);
                    return result;
                } catch (SignatureException e) {
                    return result;
                }
            } else {
                userKeyMap.remove(token);
                return result;
            }
        } else {
            return result;
        }
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}

