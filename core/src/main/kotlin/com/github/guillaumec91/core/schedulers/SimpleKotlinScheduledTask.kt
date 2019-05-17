package com.github.guillaumec91.core.schedulers

import com.github.guillaumec91.core.config.KotlinSamplesConfig
import org.osgi.service.component.annotations.Activate
import org.osgi.service.component.annotations.Component
import org.osgi.service.metatype.annotations.Designate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Sample Kotlin based scheduled task using a Java
 * annotation class for OSGI configurations
 */
//Designate to Java class due to annotation constraints
@Designate(ocd = KotlinSamplesConfig.SimpleKotlinScheduledTaskConfig::class)
@Component(service = [Runnable::class])
class SimpleKotlinScheduledTask : Runnable{

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private var config: KotlinSamplesConfig.SimpleKotlinScheduledTaskConfig? = null

    override fun run() {
        log.info("SimpleScheduledTask is now running, myParameter='{${config?.myParameter}}'")
    }

    @Activate
    fun activate(config: KotlinSamplesConfig.SimpleKotlinScheduledTaskConfig) {
        this.config = config
    }
}