package com.place.search.presentation.external

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("naver")
class NaverProperties {
    lateinit var baseUrl: String
    lateinit var id: String
    lateinit var secret: String
}
