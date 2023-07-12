package com.place.search.configuration

import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebConfiguration {
    @Bean
    fun authFilterRegistration(): FilterRegistrationBean<*> {
        val registration = FilterRegistrationBean<Filter>()
        val urlPatterns = listOf("/api/**")

        registration.filter = APIAuthFilter()
        registration.urlPatterns = urlPatterns
        registration.order = Ordered.HIGHEST_PRECEDENCE

        return registration
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .anyRequest().permitAll()
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .headers { header -> header.frameOptions { it.disable() } }

        return http.build()
    }
}
