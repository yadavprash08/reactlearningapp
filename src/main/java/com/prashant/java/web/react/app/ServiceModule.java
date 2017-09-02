package com.prashant.java.web.react.app;

import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.prashant.java.web.react.resources.MyResource;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.inject.Scopes.*;

/**
 * Package: com.prashant.java.web.react.app
 */
@AllArgsConstructor
public class ServiceModule extends ServletModule {

    @NonNull
    private final String[] appArgs;

    @Provides
    @Named("ApplicationName")
    public String appName() {
        return "JettyGuiceApp";
    }

    @Provides
    @Named("server.httpPort")
    public int serverPort() {
        Integer serverPort =
            Optional.ofNullable(System.getProperty("httpPort")).map(Integer::parseInt).orElse(Integer.valueOf(8001));
        return serverPort.intValue();
    }

    @Override
    protected void configureServlets() {
        bind(DefaultServlet.class).in(SINGLETON);
        bind(GuiceContainer.class).in(SINGLETON);
        bind(MyResource.class).in(SINGLETON);

        final Map<String, String> initparams= new HashMap<>();
        initparams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        serve("/*").with(GuiceContainer.class, initparams);
    }

    @Provides
    @Singleton
    public Server server(@Named("server.httpPort") final int httpPort) {
        final Server server = new Server(httpPort);
        // Configure the server to inject all the controllers created with the Guice container.
        final ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class,
                          "/*",
                          EnumSet.of(javax.servlet.DispatcherType.REQUEST,
                                     javax.servlet.DispatcherType.ASYNC));

        context.addServlet(DefaultServlet.class, "/*");
        return server;
    }

}
