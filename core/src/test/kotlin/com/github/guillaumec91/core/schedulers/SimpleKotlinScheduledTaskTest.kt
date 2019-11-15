package com.github.guillaumec91.core.schedulers

import io.wcm.testing.mock.aem.junit5.AemContextExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import uk.org.lidalia.slf4jext.Level
import uk.org.lidalia.slf4jtest.TestLoggerFactory

@ExtendWith(AemContextExtension::class)
internal class SimpleKotlinScheduledTaskTest {

    private val fixture = SimpleKotlinScheduledTask()

    private val logger = TestLoggerFactory.getTestLogger(fixture.javaClass)

    @BeforeEach
    fun setup() {
        TestLoggerFactory.clear()
    }

    @Test
    fun run() {
        val config = mock(SimpleKotlinScheduledTask.Config::class.java)
        `when`<String>(config.myParameter).thenReturn("parameter value")

        fixture.activate(config)
        fixture.run()

        val events = logger.loggingEvents

        assertAll(
                { assertEquals(1, events.size) },
                { assertEquals(Level.INFO, events[0].level) },
                { assertEquals("SimpleScheduledTask is now running, myParameter='{${config?.myParameter}}'", events[0].message) }
        )
    }
}
