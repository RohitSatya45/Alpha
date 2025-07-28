package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.CityModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class, adapters = CityModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CityModelImpl implements CityModel {

    @Inject
    private String city;

    @Override
    public String getCity() {
        return city;
    }
}
