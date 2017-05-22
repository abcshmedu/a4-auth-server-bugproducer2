package edu.hm.bugproducer.restAPI;


import io.jsonwebtoken.Jwt;
import javafx.util.Pair;

public interface AuthService {

    Pair<MediaServiceResult, String> createToken(String user, String password);
    Pair<MediaServiceResult, Jwt> verifyToken(String token);

}
