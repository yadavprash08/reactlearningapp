package com.prashant.java.web.react.app;

import com.google.common.base.Throwables;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;
import com.prashant.java.web.react.resources.MyResource;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;

import javax.ws.rs.ApplicationPath;
import java.util.concurrent.CountDownLatch;

/**
 * Author: @yprasha Created on: 6/2/17
 */
@Slf4j
@ApplicationPath("/")
public class Application {

    private static final String DEFAULT_SERVER_PORTS = "8001";

    private final Server server;
    private final Injector injector;

    public Application(final String[] args) throws Exception {
        this.injector = Guice.createInjector(new ServiceModule(args));
        server = this.injector.getInstance(Server.class);
        server.start();
        registerShutdownHook();
    }

    private void registerShutdownHook() {
        Thread hookThread = new Thread(this::requestShutdown);
        Runtime.getRuntime().addShutdownHook(hookThread);
    }

    private void requestShutdown() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Throwables.throwIfUnchecked(e);
        } finally {
            server.destroy();
        }
    }

    public static void main(String[] args) throws Exception {
        log.info("Starting the application");
        final String package_name = "com.prashant.java.web.react";
        Application app = new Application(args);
    }
}
