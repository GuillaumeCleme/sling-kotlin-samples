package com.github.guillaumec91.core.filters

import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.engine.EngineConstants
import org.osgi.framework.Constants
import org.osgi.service.component.annotations.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Simple Kotlin servlet filter component that logs incoming requests.
 */
@Component(service = [Filter::class],
        property = [
            "${Constants.SERVICE_DESCRIPTION}=Demo to filter incoming requests",
            "${EngineConstants.SLING_FILTER_SCOPE}=${EngineConstants.FILTER_SCOPE_REQUEST}",
            "${Constants.SERVICE_RANKING}:Integer=-700"
        ])
class KotlinLoggingFilter : Filter {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse,
                          filterChain: FilterChain) {

        val slingRequest = request as SlingHttpServletRequest
        log.info("Request for {${slingRequest.requestPathInfo.resourcePath}}, with selector " +
                "{${slingRequest.requestPathInfo.selectorString}}")

        filterChain.doFilter(request, response)
    }

    override fun init(filterConfig: FilterConfig) {}

    override fun destroy() {}
}