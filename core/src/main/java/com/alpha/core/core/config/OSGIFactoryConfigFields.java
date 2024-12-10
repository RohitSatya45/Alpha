package com.alpha.core.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="OSGI Factory config", description = "Config factory folder osgi")
public @interface OSGIFactoryConfigFields {
    @AttributeDefinition(
            name="Config ID",
            description="Enter Config ID",
            type= AttributeType.INTEGER
    )
    int configID() default 5;
    @AttributeDefinition(
            name="Config Name",
            description = "Enter Config Name",
            type=AttributeType.STRING)
    public String configName() default "Config #";

    @AttributeDefinition(
            name="Config URL",
            description = "Enter Config URL",
            type=AttributeType.STRING
    )public String configURL() default "Config #";
}
