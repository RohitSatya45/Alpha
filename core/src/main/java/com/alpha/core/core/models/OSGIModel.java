package com.alpha.core.core.models;

import com.alpha.core.core.services.OSGIFactoryConfig;

import java.util.List;

public interface OSGIModel {
    public String getServiceName();

    public int getServiceId();

    public boolean getLiveData();

    public String[] getCountries();

    public String getRunModes();

    public String getServicename();

    public int getServiceid();

    public  String getServiceurl();


    public List<OSGIFactoryConfig> getAllOSGiConfigs();
}
