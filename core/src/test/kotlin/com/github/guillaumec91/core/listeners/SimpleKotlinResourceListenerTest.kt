package com.github.guillaumec91.core.listeners

import org.apache.sling.api.SlingConstants
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.osgi.service.event.Event
import uk.org.lidalia.slf4jext.Level
import uk.org.lidalia.slf4jtest.TestLoggerFactory
import java.util.*

internal class SimpleKotlinResourceListenerTest {

    private val fixture = SimpleKotlinResourceListener()

    private val logger = TestLoggerFactory.getTestLogger(fixture.javaClass)

    @Test
    fun handleEvent() {
        val resourceEvent = Event("event/topic",
                Collections.singletonMap(SlingConstants.PROPERTY_PATH, "/content/test"))

        fixture.handleEvent(resourceEvent)

        val events = logger.loggingEvents
        assertEquals(1, events.size)

        assertAll(
                { assertEquals(Level.INFO, events[0].level) },
                { assertEquals("Resource event: {${resourceEvent.topic}} at: {${resourceEvent.getProperty(SlingConstants.PROPERTY_PATH)}}",
                        events[0].message) }
        )
    }
}
