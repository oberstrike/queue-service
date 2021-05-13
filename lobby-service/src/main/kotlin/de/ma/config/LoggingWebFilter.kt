package de.ma.config

import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import kotlin.system.measureTimeMillis

@WebFilter("/*")
@ApplicationScoped
class LoggingWebFilter : Filter {

    companion object {
        val LOG: Logger = Logger.getLogger(LoggingWebFilter::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        val uri = httpServletRequest.requestURI
        val method = httpServletRequest.method
        val contentLength = httpServletRequest.contentLength

        val result = measureTimeMillis{
            chain.doFilter(request, response)
        }

        val requestMessage =
            "[URI: $uri, method: $method, query: ${request.queryString}, content-length: $contentLength, passedTime: $result]"

        LOG.info(requestMessage)
    }

}

