package edu.hm.bugproducer.restAPI;


import javafx.util.Pair;

public interface AuthService {

    Pair createToken(String user, String password);
    MediaServiceResult verifyToken(String token);

}
