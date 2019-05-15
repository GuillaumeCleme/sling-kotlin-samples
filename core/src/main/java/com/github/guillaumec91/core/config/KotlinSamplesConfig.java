package com.github.guillaumec91.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

public class KotlinSamplesConfig{

    @ObjectClassDefinition(name="Simple Kotlin Servlet - Configuration",
            description = "Java-based configuration for Kotlin Servlet")
    public @interface SimpleKotlinServletConfig {

        @AttributeDefinition(name = "A parameter",
                description = "Can be configured in /system/console/configMgr")
        String myParameter() default "";
    }

    @ObjectClassDefinition(name="Simple Kotlin Scheduled Task - Configuration",
            description = "Can be configured in /system/console/configMgr")
    public static @interface SimpleKotlinScheduledTaskConfig {

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "*/30 * * * * ?";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "A parameter",
                description = "Can be configured in /system/console/configMgr")
        String myParameter() default "";
    }

}
