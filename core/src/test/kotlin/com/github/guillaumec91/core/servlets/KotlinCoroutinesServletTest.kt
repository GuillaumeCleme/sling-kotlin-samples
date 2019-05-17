package com.github.guillaumec91.core.servlets

import io.wcm.testing.mock.aem.junit5.AemContext
import io.wcm.testing.mock.aem.junit5.AemContextExtension
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException
import javax.servlet.ServletException

@ExtendWith(AemContextExtension::class)
internal class KotlinCoroutinesServletTest {

    private val fixture = KotlinCoroutinesServlet()

    @Test
    @Throws(ServletException::class, IOException::class)
    fun doGet(context: AemContext) {

        val request = context.request()
        val response = context.response()

        fixture.doGet(request, response)

        assertAll(
                { assertTrue(response.outputAsString.isNotEmpty(), "Test that response is not empty") },
                { assertTrue(response.outputAsString.startsWith("<span"), "Test start of output") },
                { assertTrue(response.outputAsString.endsWith("</span>"), "Test end of output") }
        )
    }
}

