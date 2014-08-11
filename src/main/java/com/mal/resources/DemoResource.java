package com.mal.resources;

import com.mal.orm.User;
import com.mal.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource
 */
@Named
@Path("demo")
public class DemoResource extends AbstractRestfulServiceBase{

    @Inject
    private UserService userService;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String demo() {
	return "Got it";
    }

    @GET
    @Path("/{user_id}")
    public User getIt(
    	@PathParam("user_id")Long userId) {

    	return userService.getUser(userId);
    }

    @POST
    public User postIt() {

    	return userService.post();
    }

    @PUT
    @Path("/{user_id}")
    public User updateIt(
    	@PathParam("user_id") Long userId) {

    	return userService.update(userId);
    }

    @DELETE
    @Path("/{user_id}")
    public String deleteIt(
    	@PathParam("user_id") Long userId) {

    	return userService.delete(userId);
    }
}
