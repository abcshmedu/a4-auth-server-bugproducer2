package edu.hm.bugproducer.restAPI;

import io.jsonwebtoken.Jwt;
import javafx.util.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * AuthRessource class.
 *
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
@Path("/auth")
public class AuthRessource {

    private AuthServiceImpl authservice = new AuthServiceImpl();


    /**
     * login method.
     *
     * uses a user and a password and create with that a token by using the HTTP verb POST.
     * @param user of shareIt
     * @param password selected by user
     * @return a status code and the value of the entity
     */
    @POST
    @Path("/login/")
   //@Consumes(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String user, @FormParam("password") String password) {

        Pair<MediaServiceResult, String> result = authservice.createToken(user, password);
        return Response
                .status(result.getKey().getCode())
                .entity(result.getValue())
                .build();

    }

    /**
     * getVerify method.
     *
     * checks if the token is verified by using the HTTP verb GET.
     * @param token a string that not hold any user information
     * @return a status code and the value of the entity
     */
    @GET
    @Path ("/verify/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVerify(@PathParam("token") String token) {
        Pair<MediaServiceResult, Jwt> result = authservice.verifyToken(token);
        return Response
                .status(result.getKey().getCode())
                .entity(result.getValue())
                .build();

    }




}
