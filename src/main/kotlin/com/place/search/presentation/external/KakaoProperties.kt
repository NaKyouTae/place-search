package com.place.search.presentation.external

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("kakao")
class KakaoProperties {
    lateinit var baseUrl: String
    lateinit var key: String
}
