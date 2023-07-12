package com.place.search.infrastructure

import com.zaxxer.hikari.HikariDataSource
import lombok.extern.slf4j.Slf4j
import org.h2.tools.Server
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Slf4j
@Configuration
@Profile("!prod")
class H2Configuration {
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): DataSource {
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093").start()
        return HikariDataSource()
    }
}
