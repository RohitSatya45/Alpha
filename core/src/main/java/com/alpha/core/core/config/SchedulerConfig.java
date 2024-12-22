package com.alpha.core.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="scheduler", description = "passing value to scheduler")
public @interface SchedulerConfig {
    @AttributeDefinition(
            name="Scheduler Name",
            description="Enter Scheduler Name",
            type= AttributeType.STRING
    )
    String schedulerName() default "Custom Sling Scheduler Configuration";
    @AttributeDefinition(
            name="Cron Expression",
            description = "Cron Expression Used by Sceduler",
            type=AttributeType.STRING)
    public String cronExpression() default "0 0/1 * 1/1 * ? *";
}
