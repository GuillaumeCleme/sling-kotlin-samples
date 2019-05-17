package com.github.guillaumec91.core.servlets

import com.github.guillaumec91.core.config.KotlinSamplesConfig
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.HttpConstants
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.osgi.framework.Constants
import org.osgi.service.component.annotations.Activate
import org.osgi.service.component.annotations.Component
import org.osgi.service.metatype.annotations.Designate
import java.io.IOException
import javax.servlet.Servlet
import javax.servlet.ServletException

private const val SERVLET_PATHS = "/bin/kotlinsamples/simplekotlinservlet"

/**
 * Sample Kotlin based SlingServlet using a Java
 * annotation class for OSGI configurations
 */
//Designate to Java class due to annotation constraints
@Designate(ocd = KotlinSamplesConfig.SimpleKotlinServletConfig::class)
@Component(service = [Servlet::class],
        property = [
            "${Constants.SERVICE_DESCRIPTION}=Simple Kotlin Servlet",
            "sling.servlet.methods=${HttpConstants.METHOD_GET}",
            "sling.servlet.paths=$SERVLET_PATHS"
        ])
open class SimpleKotlinServlet : SlingSafeMethodsServlet() {

    private var config: KotlinSamplesConfig.SimpleKotlinServletConfig? = null

    @Throws(ServletException::class, IOException::class)
    public override fun doGet(req: SlingHttpServletRequest,
                       resp: SlingHttpServletResponse) {
        resp.contentType = "text/plain"
        resp.writer.write("Hello World in Kotlin, myParameter: ${config?.myParameter}")
        resp.writer.close()
    }

    @Activate
    fun activate(config: KotlinSamplesConfig.SimpleKotlinServletConfig) {
        this.config = config
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
