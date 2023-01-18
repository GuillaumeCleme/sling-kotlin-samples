package me.guillaumecle.core.filters

import io.wcm.testing.mock.aem.junit5.AemContext
import io.wcm.testing.mock.aem.junit5.AemContextExtension
import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import uk.org.lidalia.slf4jext.Level
import uk.org.lidalia.slf4jtest.TestLoggerFactory
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException

@ExtendWith(AemContextExtension::class)
internal class KotlinLoggingFilterTest {

    private val fixture = KotlinLoggingFilter()

    private val logger = TestLoggerFactory.getTestLogger(fixture.javaClass)

    @BeforeEach
    fun setup() {
        TestLoggerFactory.clear()
    }

    @Test
    @Throws(IOException::class, ServletException::class)
    fun doFilter(context: AemContext) {
        val request = context.request()
        val response = context.response()

        val requestPathInfo = request.requestPathInfo as MockRequestPathInfo
        requestPathInfo.resourcePath = "/content/test"
        requestPathInfo.selectorString = "selectors"

        fixture.init(mock(FilterConfig::class.java))
        fixture.doFilter(request, response, mock(FilterChain::class.java))
        fixture.destroy()

        //Get logging event, should have only logged once
        val events = logger.loggingEvents
        assertEquals(1, events.size)

        //Check message and log level
        assertAll(
                { assertEquals(Level.INFO, events[0].level) },
                { assertEquals("Request for {" +
                        "${requestPathInfo.resourcePath}" +
                        "}, with selector {" +
                        "${requestPathInfo.selectorString}" +
                        "}"
                        , events[0].message) }
        )
    }
}
