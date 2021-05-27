package org.acme.getting.started;

import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String form(@Context HttpHeaders httpHeaders) {

        System.out.println("Receving some payload....");

        Set<String> headerKeys = httpHeaders.getRequestHeaders().keySet();
        for(String header:headerKeys){
            System.out.println(header+":"+httpHeaders.getRequestHeader(header).get(0));
        }

        return "{\"hello\n:\nworld\n}";
    }

}