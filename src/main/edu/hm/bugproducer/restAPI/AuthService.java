package edu.hm.bugproducer.restAPI;



import edu.hm.bugproducer.Status.StatusMgnt;
import javafx.util.Pair;

/**
 * AuthService interface.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public interface AuthService {
    /**
     * createToken method.
     * takes the user and this password and compare it with a legit user name and a legit user password,
     * if the userKeyMap contains the user, it will put his token in a set and checks if it is expired or not
     * @param user name of user
     * @param password unique string choose by user
     * @return status code with token or message
     */
    Pair<StatusMgnt, String> createToken(String user, String password);

    /**
     * verifyToken method.
     * checks if the token we get is in our userKeyMap and if it is expired and creating a jwt
     * @param token unique string
     * @return status code and null if we cant verify the token or status code with a jwt if it is okay
     */
    Pair<StatusMgnt, String> verifyToken(String token);
}
