package edu.hm.bugproducer.restAPI;

import javafx.util.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/auth")
public class AuthRessource {

    private AuthServiceImpl authservice = new AuthServiceImpl();



    @POST
    @Path("/login/")
   //@Consumes(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String user,@FormParam("password") String password) {

        Pair<MediaServiceResult, String> result = authservice.createToken(user,password);
        return Response
                .status(result.getKey().getCode())
                .entity(result.getValue())
                .build();

    }

    @GET
    @Path ("/verify/{token}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getVerify(@PathParam("token") String token) {
        MediaServiceResult result = authservice.verifyToken(token);
        return Response
                .status(result.getCode())
                .build();

    }




}
