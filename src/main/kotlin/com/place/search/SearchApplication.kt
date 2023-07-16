package com.place.search

import com.place.search.presentation.external.KakaoProperties
import com.place.search.presentation.external.NaverProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(
	NaverProperties::class,
	KakaoProperties::class,
)
class SearchApplication

fun main(args: Array<String>) {
	runApplication<SearchApplication>(*args)
}
