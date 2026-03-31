// Path: /skyblock-api/src/main/java/net/sujal/api/service/Service.java
package net.sujal.api.service;

/**
 * Base interface for all modular services in the Skyblock ecosystem.
 */
public interface Service {
    
    /**
     * Called when the service is registered and should initialize.
     */
    void onEnable();

    /**
     * Called when the server is shutting down. Clean up tasks here.
     */
    void onDisable();
}
