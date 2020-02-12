package com.mo2christian.budget.filter;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@ApiKey
@Priority(Priorities.USER)
public class ApiKeyFilter implements ContainerRequestFilter {

    private final String API_KEY = "api-key";

    @Inject
    @ConfigProperty(name = "app.key")
    private String appKey;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String key = containerRequestContext.getHeaders().getFirst(API_KEY);
        if (key == null || !key.equals(appKey))
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }
}
