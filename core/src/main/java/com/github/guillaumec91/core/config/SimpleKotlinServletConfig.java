package com.github.guillaumec91.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Simple Kotlin Servlet - Configuration",
        description = "Java-based configuration for Kotlin Servlet")
public @interface SimpleKotlinServletConfig {

    @AttributeDefinition(name = "A parameter",
            description = "Can be configured in /system/console/configMgr")
    String myParameter() default "";
}

