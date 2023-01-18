package me.guillaumecle.core.servlets

import kotlinx.coroutines.*
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

private const val SERVLET_PATHS = "/bin/kotlinsamples/kotlincoroutinesservlet"

/**
 * Sample Kotlin based SlingServlet using
 * Coroutines
 *
 * @see https://kotlinlang.org/docs/tutorials/coroutines/coroutines-basic-jvm.html
 */
@Component(service = [Servlet::class],
        property = [
            "${Constants.SERVICE_DESCRIPTION}=Kotlin Coroutines Servlet",
            "sling.servlet.methods=${HttpConstants.METHOD_GET}",
            "sling.servlet.paths=$SERVLET_PATHS"
        ])
class KotlinCoroutinesServlet : SlingSafeMethodsServlet() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(ServletException::class, IOException::class)
    @ExperimentalCoroutinesApi
    public override fun doGet(req: SlingHttpServletRequest,
                       resp: SlingHttpServletResponse) {
        resp.contentType = "text/html"

        val deferred: Deferred<String> = GlobalScope.async {

            var asyncMsg: String = ""

            for (i in 1..10000){
                val j = i%2
                asyncMsg += j
                resp.writer.print("<span style=\"color:blue\">$j</span>")
            }

            return@async asyncMsg
        }

        var syncMsg: String = ""

        for (i in 1..10000){
            val j = i%2
            syncMsg += j
            resp.writer.print("<span style=\"color:red\">$j</span>")
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
