package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.NestedMultifield;
import com.alpha.core.core.models.OSGIModel;
import com.alpha.core.core.services.OSGIConfig;
import com.alpha.core.core.services.OSGIFactoryConfig;
import com.alpha.core.core.services.OSGIFields;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;

@Model(adaptables =SlingHttpServletRequest.class, adapters = OSGIModel.class)

public class OSGIModelImpl implements OSGIModel {
    @OSGiService
    OSGIFields osgiFields;
    @OSGiService
    OSGIConfig osgiConfig;
    @OSGiService
    OSGIFactoryConfig osgiFactoryConfig;
    @Override
    public String getServiceName() {
        return osgiFields.getServiceName();
    }

    @Override
    public int getServiceId() {
        return osgiFields.getServiceId();
    }

    @Override
    public boolean getLiveData() {
        return osgiFields.getLiveData();
    }

    @Override
    public String[] getCountries() {
        return osgiFields.getCountries();
    }

    @Override
    public String getRunModes() {
        return osgiFields.getRunModes();
    }

    @Override
    public String getServicename() {
        return osgiConfig.getServiceName();
    }

    @Override
    public int getServiceid() {
        return osgiConfig.getServiceId();
    }

    @Override
    public String getServiceurl() {
        return osgiConfig.getServiceUrl();
    }

    @Override
    public List<OSGIFactoryConfig> getAllOSGiConfigs() {
        return osgiFactoryConfig.getConfigList();
    }
}
