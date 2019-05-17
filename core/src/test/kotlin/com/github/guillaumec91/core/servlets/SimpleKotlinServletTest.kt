package com.github.guillaumec91.core.servlets

import com.github.guillaumec91.core.config.KotlinSamplesConfig
import io.wcm.testing.mock.aem.junit5.AemContext
import io.wcm.testing.mock.aem.junit5.AemContextExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException
import javax.servlet.ServletException

@ExtendWith(AemContextExtension::class)
internal class SimpleKotlinServletTest {

    private val fixture = SimpleKotlinServlet()

    @Test
    @Throws(ServletException::class, IOException::class)
    fun doGet(context: AemContext) {
        context.build().resource("/content/test", "jcr:title", "resource title").commit()
        context.currentResource("/content/test")

        val config = `mock`(KotlinSamplesConfig.SimpleKotlinServletConfig::class.java)
        `when`<String>(config.myParameter).thenReturn("parameter value")

        val request = context.request()
        val response = context.response()

        fixture.activate(config)
        fixture.doGet(request, response)

        assertEquals("Hello World in Kotlin, myParameter: ${config?.myParameter}", response.outputAsString)
    }
}

