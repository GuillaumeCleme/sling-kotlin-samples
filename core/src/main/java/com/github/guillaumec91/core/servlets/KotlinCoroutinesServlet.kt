package com.github.guillaumec91.core.servlets

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.HttpConstants
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.osgi.framework.Constants
import org.osgi.service.component.annotations.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * Sample Kotlin based SlingServlet using
 * Coroutines
 *
 * @see https://kotlinlang.org/docs/tutorials/coroutines/coroutines-basic-jvm.html
 */
@Component(service = [Servlet::class],
        property = [
            Constants.SERVICE_DESCRIPTION + "=Simple Kotlin Servlet",
            "sling.servlet.methods=" + HttpConstants.METHOD_GET,
            "sling.servlet.paths=" + "/bin/kotlinsamples/kotlincoroutinesservlet",
            "sling.servlet.extensions=" + "txt"
        ])
class KotlinCoroutinesServlet : SlingSafeMethodsServlet() {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: SlingHttpServletRequest,
                       resp: SlingHttpServletResponse) {
        resp.contentType = "text/html"

        val deferred: Deferred<String> = GlobalScope.async {

            var asyncMsg: String = ""

            for (i in 1..10000){
                val j = i%2
                asyncMsg += j
                resp.writer.println("<span style=\"color:blue\">$j</span>")
            }

            return@async asyncMsg
        }

        var syncMsg: String = ""

        for (i in 1..10000){
            val j = i%2
            syncMsg += j
            resp.writer.println("<span style=\"color:red\">$j</span>")
        }

        runBlocking {
            deferred.await()  // wait until child coroutine completed

            log.info("Main thread said: $syncMsg")
            log.info("Coroutine said: ${deferred.getCompleted()}")

            resp.writer.close()
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
