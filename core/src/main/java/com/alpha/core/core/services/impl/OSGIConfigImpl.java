package com.alpha.core.core.services.impl;

import com.alpha.core.core.config.OSGIConfigFields;
import com.alpha.core.core.services.OSGIConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service= OSGIConfig.class,immediate=true)
@Designate(ocd= OSGIConfigFields.class)
public class OSGIConfigImpl implements OSGIConfig{
    private int serviceId;
    private String serviceName;
    private String serviceURL;
    @Activate
    public void activate(OSGIConfigFields osgiConfigFields){
        serviceId= osgiConfigFields.serviceID();
        serviceName=osgiConfigFields.serviceName();
        serviceURL=osgiConfigFields.serviceURL();
    }

    @Override
    public int getServiceId() {
        return serviceId;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String getServiceUrl() {
        return serviceURL;
    }
}

