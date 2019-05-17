package com.github.guillaumec91.core.listeners

import org.apache.sling.api.SlingConstants
import org.osgi.framework.Constants
import org.osgi.service.component.annotations.Component
import org.osgi.service.event.Event
import org.osgi.service.event.EventConstants
import org.osgi.service.event.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Kotlin listener for changes in the resource tree
 */
@Component(service = [EventHandler::class],
        immediate = true,
        property = [
             "${Constants.SERVICE_DESCRIPTION}=Kotlin listener for changes in the resource tree",
             "${EventConstants.EVENT_TOPIC}=org/apache/sling/api/resource/Resource/*"
        ])
class SimpleKotlinResourceListener : EventHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun handleEvent(event: Event) {
        log.info("Resource event: {${event.topic}} at: {${event.getProperty(SlingConstants.PROPERTY_PATH)}}")
    }
}