package com.mal.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
public abstract class AbstractRestfulServiceBase {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractRestfulServiceBase() {}

    protected Logger getLogger() {
        return logger;
    }
}
