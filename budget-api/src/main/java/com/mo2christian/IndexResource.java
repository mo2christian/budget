package com.mo2christian;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class IndexResource {

    @GET
    public Response welcome(){
        return Response.temporaryRedirect(URI.create("/budget")).build();
    }

}
