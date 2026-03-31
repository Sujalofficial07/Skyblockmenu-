// Path: /skyblock-core/src/main/java/net/sujal/core/service/SkyblockServiceManager.java
package net.sujal.core.service;

import net.sujal.api.service.Service;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lightweight Dependency Injection & Service Registry.
 */
public class SkyblockServiceManager {
    
    private final Map<Class<? extends Service>, Service> services = new LinkedHashMap<>();

    public <T extends Service> void registerService(Class<T> clazz, T service) {
        services.put(clazz, service);
        service.onEnable();
    }

    public <T extends Service> T getService(Class<T> clazz) {
        Service service = services.get(clazz);
        if (service == null) {
            throw new IllegalArgumentException("Service not registered: " + clazz.getName());
        }
        return clazz.cast(service);
    }

    public void disableAll() {
        // Iterate in reverse order to disable dependent services correctly
        Service[] serviceArray = services.values().toArray(new Service[0]);
        for (int i = serviceArray.length - 1; i >= 0; i--) {
            serviceArray[i].onDisable();
        }
        services.clear();
    }
}
