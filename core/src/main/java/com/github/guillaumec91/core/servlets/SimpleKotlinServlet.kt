package com.github.guillaumec91.core.servlets

import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.HttpConstants
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.osgi.framework.Constants
import org.osgi.service.component.annotations.Component
import java.io.IOException
import javax.servlet.Servlet
import javax.servlet.ServletException


@Component(service = [Servlet::class],
        property = [
            Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
            "sling.servlet.methods=" + HttpConstants.METHOD_GET,
            "sling.servlet.paths=" + "/bin/kotlinsamples/simplekotlinservlet",
            "sling.servlet.extensions=" + "txt"
        ])
class SimpleKotlinServlet : SlingSafeMethodsServlet() {

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: SlingHttpServletRequest,
                       resp: SlingHttpServletResponse) {
        resp.contentType = "text/plain"
        resp.writer.write("Hello World in Kotlin")
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
