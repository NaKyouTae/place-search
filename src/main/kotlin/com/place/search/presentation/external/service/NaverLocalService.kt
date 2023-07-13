package com.place.search.presentation.external.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.place.search.configuration.CommonException
import com.place.search.presentation.external.NaverProperties
import com.place.search.presentation.external.request.KakaoLocalSearchRequest
import com.place.search.presentation.external.request.NaverLocalSearchRequest
import com.place.search.presentation.external.response.naver.NaverLocalSearchResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.lang.StringBuilder

@Service
class NaverLocalService(
    private val objectMapper: ObjectMapper,
    private val properties: NaverProperties,
    private val webClientBuilder: WebClient.Builder,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun searchPlaceByNaverApi(request: NaverLocalSearchRequest): NaverLocalSearchResponse {
        val uri = createLocalSearchUri(request)
        val res = webClientBuilder.build()
            .get()
            .uri(uri)
            .headers {
                it.add("X-Naver-Client-Id", properties.id)
                it.add("X-Naver-Client-Secret", properties.secret)
            }
            .retrieve()
            .onStatus({it.isError}) { response ->
                response.bodyToMono(String::class.java).subscribe { errStr ->
                    logger.error("[NAVER API] Local Search errStr = $errStr")
                }
                Mono.error(
                    CommonException(
                        "[NAVER API] Local Search 중 에러가 발생했습니다",
                        HttpStatus.INTERNAL_SERVER_ERROR
                    )
                )
            }
            .bodyToMono(String::class.java)
            .block()

        return objectMapper.readValue(res, NaverLocalSearchResponse::class.java)
    }

    private fun createLocalSearchUri(request: NaverLocalSearchRequest): String {
        val uri = StringBuilder()

        uri.append(properties.baseUrl + LOCAL_SEARCH_END_POINT)
        uri.append("?query=", request.query)
        uri.append("&display=", request.display)
        uri.append("&start=", request.start)

        return uri.toString()
    }

    companion object {
        const val LOCAL_SEARCH_END_POINT = "/v1/search/local.json"
    }
}
