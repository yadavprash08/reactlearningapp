package com.prashant.java.web.react.resources;

import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Resource controller to handle all the requests coming from outside.
 * Author: @yprasha
 * Created on: 6/2/17
 */
@Path("/resources")
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class MyResource {


    @GET
    @Path("applications")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getIt() {
        final List<String> applications = new ArrayList<>();
        applications.addAll(Arrays.asList("Facebook", "Google", "ICICI Bank", "Lakes Apt"));
        return applications;
    }
}
