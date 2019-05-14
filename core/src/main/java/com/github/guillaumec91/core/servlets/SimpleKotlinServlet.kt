package com.github.guillaumec91.core.servlets

import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.HttpConstants
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.osgi.framework.Constants
import org.osgi.service.component.annotations.Activate
import org.osgi.service.component.annotations.Component
import org.osgi.service.metatype.annotations.AttributeDefinition
import org.osgi.service.metatype.annotations.Designate
import org.osgi.service.metatype.annotations.ObjectClassDefinition
import java.io.IOException
import javax.servlet.Servlet
import javax.servlet.ServletException

@Designate(ocd = SimpleKotlinServlet.Config::class)
@Component(service = [Servlet::class],
        property = [
            Constants.SERVICE_DESCRIPTION + "=Simple Kotlin Servlet",
            "sling.servlet.methods=" + HttpConstants.METHOD_GET,
            "sling.servlet.paths=" + "/bin/kotlinsamples/simplekotlinservlet",
            "sling.servlet.extensions=" + "txt"
        ])
class SimpleKotlinServlet : SlingSafeMethodsServlet() {

    private var myParameter: String? = null

    @ObjectClassDefinition(name = "Sample Kotlin servlet",
            description = "Simple Kotlin servlet with configurable properties")
    public annotation class  Config (
        val value : String = "hello"
    )

    @AttributeDefinition(name = "A parameter", description = "Can be configured in /system/console/configMgr")
    fun myParameter(config: Config) : String {
        return config.value
    }

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: SlingHttpServletRequest,
                       resp: SlingHttpServletResponse) {
        resp.contentType = "text/plain"
        resp.writer.write("Hello World in Kotlin, myParameter: $myParameter")
    }

    @Activate
    protected fun activate(config: Config) {
        myParameter = myParameter(config)
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
