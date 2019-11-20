package com.github.guillaumec91.core.schedulers

import com.github.guillaumec91.core.schedulers.SimpleKotlinScheduledTask.Config
import org.osgi.service.component.annotations.Activate
import org.osgi.service.component.annotations.Component
import org.osgi.service.metatype.annotations.AttributeDefinition
import org.osgi.service.metatype.annotations.Designate
import org.osgi.service.metatype.annotations.ObjectClassDefinition
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Sample Kotlin based scheduled task using a Java
 * annotation class for OSGI configurations
 */
@Designate(ocd = Config::class)
@Component(service = [Runnable::class])
class SimpleKotlinScheduledTask : Runnable{

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private var config: Config? = null

    @ObjectClassDefinition(name = "Simple Kotlin Scheduled Task - Configuration",
            description = "Can be configured in /system/console/configMgr")
    annotation class  Config (
            @get:AttributeDefinition(name = "Cron Expression", description = "Cron-job expression")
            val scheduler_expression : String = "*/30 * * * * ?",

            @get:AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
            val scheduler_concurrent : Boolean = false,

            @get:AttributeDefinition(name = "A parameter", description = "Can be configured in /system/console/configMgr")
            val myParameter : String = "hello"
    )

    override fun run() {
        log.info("SimpleScheduledTask is now running, myParameter='{${config?.myParameter}}'")
    }

    @Activate
    fun activate(config: Config) {
        this.config = config
    }
}