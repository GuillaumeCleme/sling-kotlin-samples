package com.github.guillaumec91.core.servlets

import com.github.guillaumec91.core.config.SimpleKotlinServletConfig
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

/**
 * Sample Kotlin based SlingServlet using a Java
 * annotation class for OSGI configurations
 */
//Designate to Java class due to annotation constraints
@Designate(ocd = SimpleKotlinServletConfig::class)
@Component(service = [Servlet::class],
        property = [
            Constants.SERVICE_DESCRIPTION + "=Simple Kotlin Servlet",
            "sling.servlet.methods=" + HttpConstants.METHOD_GET,
            "sling.servlet.paths=" + "/bin/kotlinsamples/simplekotlinservlet",
            "sling.servlet.extensions=" + "txt"
        ])
class SimpleKotlinServlet : SlingSafeMethodsServlet() {

    private var myParameter: String = ""

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: SlingHttpServletRequest,
                       resp: SlingHttpServletResponse) {
        resp.contentType = "text/plain"
        resp.writer.write("Hello World in Kotlin, myParameter: $myParameter")
        resp.writer.close()
    }

    @Activate
    protected fun activate(config: SimpleKotlinServletConfig) {
        myParameter = config.myParameter
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
