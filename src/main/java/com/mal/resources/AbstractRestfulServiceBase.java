package com.mal.resources;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
public abstract class AbstractRestfulServiceBase {

    public AbstractRestfulServiceBase() {}
}
