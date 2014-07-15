package com.mal.resources;

//import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ApiApplication extends ResourceConfig {
    public ApiApplication() {
        packages("com.mal");
//        register(MultiPartFeature.class);
    }
}
