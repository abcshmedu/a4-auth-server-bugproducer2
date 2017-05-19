package edu.hm.bugproducer.restAPI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthRessource {

    @POST
    @Path("/login/")
   //@Consumes(MediaType.APPLICATION_JSON)
    public Response login() {
      return null;
    }

    @GET
    @Path ("/verify/{token}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getVerify(@PathParam("token") String token) {
        return null;
    }




}
