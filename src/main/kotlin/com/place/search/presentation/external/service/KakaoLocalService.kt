package com.place.search.presentation.external.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.place.search.configuration.CommonException
import com.place.search.presentation.external.KakaoProperties
import com.place.search.presentation.external.request.KakaoLocalSearchRequest
import com.place.search.presentation.external.response.kakao.KakaoApiResponse
import com.place.search.presentation.external.response.kakao.KakaoLocalSearchDocumentResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.lang.StringBuilder

@Service
class KakaoLocalService(
    private val objectMapper: ObjectMapper,
    private val properties: KakaoProperties,
    private val webClientBuilder: WebClient.Builder,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun searchPlaceByKakaoApi(request: KakaoLocalSearchRequest): KakaoApiResponse<KakaoLocalSearchDocumentResponse> {
        val uri = createLocalSearchUri(request)
        val res = webClientBuilder.build()
            .get()
            .uri(uri)
            .header("Authorization", "KakaoAK ${properties.key}")
            .retrieve()
            .onStatus({ it.isError }) { response ->
                response.bodyToMono(String::class.java).subscribe { errStr ->
                    logger.error("[KAKAO API] Local Search errStr = $errStr")
                }
                Mono.error(
                    CommonException(
                        "[KAKAO API] Local Search 중 에러가 발생했습니다",
                        HttpStatus.INTERNAL_SERVER_ERROR
                    )
                )
            }
            .bodyToMono(String::class.java)
            .block()

        return objectMapper.readValue(
            res,
            objectMapper.typeFactory.constructParametricType(
                KakaoApiResponse::class.java,
                KakaoLocalSearchDocumentResponse::class.java
            )
        )
    }

    private fun createLocalSearchUri(request: KakaoLocalSearchRequest): String {
        val uri = StringBuilder()

        uri.append(properties.baseUrl + LOCAL_SEARCH_END_POINT)
        uri.append("?query=", request.query)
        uri.append("&page=", request.page)
        uri.append("&size=", request.size)

        return uri.toString()
    }

    companion object {
        const val LOCAL_SEARCH_END_POINT = "/v2/local/search/keyword.json"
    }
}
