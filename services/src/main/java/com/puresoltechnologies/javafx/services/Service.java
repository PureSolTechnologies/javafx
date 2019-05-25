package com.puresoltechnologies.javafx.services;

import java.util.Set;

/**
 *
 * @author Rick-Rainer Ludwig
 */
public interface Service {

    Set<Class<? extends Service>> getDependencies();

    /**
     * This method is called once after the Service was discovered during startup
     * with SPI to initialize itself.
     */
    void construct();

    /**
     * This method is called to start the service. During runtime, services might
     * get stopped and started if needed.
     */
    void start();

    /**
     * This method is called to stop the service. During runtime, services might get
     * stopped and started if needed.
     */
    void stop();

    /**
     * This method is called just before application shutdown for graceful shutdown
     * of the service.
     */
    void destroy();

}
