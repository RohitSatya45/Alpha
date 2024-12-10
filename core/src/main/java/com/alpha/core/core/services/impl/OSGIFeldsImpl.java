package com.alpha.core.core.services.impl;

import com.alpha.core.core.services.OSGIFields;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;

@Component(service= OSGIFields.class,immediate = true)
@Designate(ocd= OSGIFeldsImpl.ServiceConfig.class)
public class OSGIFeldsImpl implements OSGIFields{

    @ObjectClassDefinition(name="OSGI Configuration",description ="Testing OSGI Conffig Fields" )
    public @interface ServiceConfig{
        @AttributeDefinition(
                name="Service Name",
                description = "Enter Service Name",
                type= AttributeType.STRING
        )
        String getServiceName() default "testservice";
        @AttributeDefinition(
                name="Service Id",
                description = "Enter Service Id",
                type= AttributeType.INTEGER
        )
        int getServiceId() default 5;
        @AttributeDefinition(
                name="Live Data",
                description = "Is Data live",
                type= AttributeType.BOOLEAN
        )
        boolean getLiveData() default false;
        @AttributeDefinition(
                name="Countries",
                description = "Enter Countrires",
                type=AttributeType.STRING)
        String[] getCountries() default {"in","uk"};
        @AttributeDefinition(
                name="Run Modes",
                description = "Select Run Modes",
                options={
                        @Option(label="Author",value="author"),
                        @Option(label="Publish",value="publish"),
                        @Option(label="Both",value="both")
                },
                type = AttributeType.STRING)
        String getRunModes() default"author";
    }

    private String serviceName;
    private int serviceId;
    private boolean isLive;
    private String[] countries;
    private String runModes;
    @Activate
    public void activate(ServiceConfig serviceConfig){
        serviceName=serviceConfig.getServiceName();
        serviceId=serviceConfig.getServiceId();
        isLive= serviceConfig.getLiveData();
        countries= serviceConfig.getCountries();
        runModes=serviceConfig.getRunModes();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public int getServiceId() {
        return serviceId;
    }

    @Override
    public boolean getLiveData() {
        return isLive;
    }

    @Override
    public String[] getCountries() {
        return countries;
    }

    @Override
    public String getRunModes() {
        return runModes;
    }
}
