package com.place.search.configuration

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Slf4j
@Component
class APIAuthFilter: OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authToken = request.getHeader("Authorization")
        val path = request.requestURI

        logger.debug("auth token $path $authToken")

        if (isStartWithWhiteList(path) || isValidAuthToken(authToken)) {
            filterChain.doFilter(request, response)
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid authentication header")
        }
    }

    private fun isValidAuthToken(authToken: String?): Boolean {
        return authToken == ACCESS_TOKEN
    }

    private fun isStartWithWhiteList(path: String): Boolean {
        val whiteList = listOf("/h2-console", "/swagger-ui")
        return whiteList.any { path.startsWith(it) }
    }

    companion object {
        const val ACCESS_TOKEN = ""
    }
}
