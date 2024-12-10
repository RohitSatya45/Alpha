package com.alpha.core.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="OSGI Folder config", description = "Config folder osgi")
public @interface OSGIConfigFields {
    @AttributeDefinition(
            name="Service ID",
            description="Enter Service ID",
            type= AttributeType.INTEGER
    )
    int serviceID() default 5;
    @AttributeDefinition(
            name="Service Name",
            description = "Enter Service Name",
            type=AttributeType.STRING)
    public String serviceName() default "AEM Geeks Service";

    @AttributeDefinition(
            name="Service URL",
            description = "Enter service URL",
            type=AttributeType.STRING
    )public String serviceURL() default "Localhost";

}
