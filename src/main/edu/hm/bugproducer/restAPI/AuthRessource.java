package edu.hm.bugproducer.restAPI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthRessource {

    @POST
    @Path("/login/")
   //@Consumes(MediaType.APPLICATION_JSON)
    public Response login() {
      return null;
    }


}
