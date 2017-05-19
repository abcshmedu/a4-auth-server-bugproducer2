package edu.hm.bugproducer.restAPI;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_OK;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_UNAUTHORIZED;

public class
AuthServiceImpl implements AuthService {

    private String legitUser = "Joe Doe";
    private String getLegitpassword = "geheim";
    private Map<String, String> userKeyMap = new HashMap<>(); //user //

    @Override
    public Pair createToken(String user, String password) {
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
            return result;
        } else {
            return new Pair(MSR_UNAUTHORIZED, "Du kommst hier nicht rein!!");

        }

    }

    @Override
    public MediaServiceResult verifyToken(String token) {
        if (userKeyMap.containsValue(token)) {
            if (TokenUtils.isNotExpired(token)) {
                return MSR_OK;
            } else {
                userKeyMap.remove(token);
                return MSR_UNAUTHORIZED;
            }
        } else {
            return MSR_UNAUTHORIZED;
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

