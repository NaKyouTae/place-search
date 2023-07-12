package com.place.search.presentation.external.service

import com.place.search.configuration.CommonException
import com.place.search.presentation.external.KakaoProperties
import com.place.search.presentation.external.request.KakaoLocalSearchRequest
import com.place.search.presentation.external.response.KakaoApiResponse
import com.place.search.presentation.external.response.LocalSearchDocumentResponse
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Service
class KakaoLocalService(
    private val webClientBuilder: WebClient.Builder,
    private val properties: KakaoProperties
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun searchPlaceByKakaoApi(request: KakaoLocalSearchRequest): KakaoApiResponse<LocalSearchDocumentResponse> {
        val uri = createLocalSearchUrl(request)
        return webClientBuilder.build()
            .get()
            .uri(uri)
            .header("Authorization: KakaoAK", properties.key)
            .retrieve()
            .onStatus({it.isError}) { response ->
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
            .bodyToMono(object : ParameterizedTypeReference<KakaoApiResponse<LocalSearchDocumentResponse>>() {})
            .block() ?: throw CommonException("[KAKAO API] Local Search 응답 결과가 없습니다.")
    }

    private fun createLocalSearchUrl(request: KakaoLocalSearchRequest): String {
        val builder = UriComponentsBuilder.fromHttpUrl(LOCAL_SEARCH_END_POINT)
            .queryParam("query", request.query)
            .queryParam("category_group_code", request.categoryGroupCode?.name)
            .queryParam("x", request.x)
            .queryParam("y", request.y)
            .queryParam("radius", request.radius)
            .queryParam("rect", request.rect)
            .queryParam("page", request.page)
            .queryParam("size", request.size)
            .queryParam("sort", request.sort)

        return builder.toUriString()
    }

    companion object {
        const val LOCAL_SEARCH_END_POINT = "/v2/local/search/keyword.json"
    }
}
