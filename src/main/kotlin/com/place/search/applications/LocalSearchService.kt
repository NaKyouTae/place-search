package com.place.search.applications

import com.place.search.domain.converter.LocalSearchConverter
import com.place.search.presentation.external.response.kakao.KakaoApiResponse
import com.place.search.presentation.external.response.kakao.KakaoLocalSearchDocumentResponse
import com.place.search.presentation.external.response.naver.NaverLocalSearchResponse
import com.place.search.presentation.external.service.KakaoLocalService
import com.place.search.presentation.external.service.NaverLocalService
import com.place.search.presentation.internal.request.LocalSearchRequest
import org.springframework.dao.CannotAcquireLockException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class LocalSearchService(
    private val kakaoLocalService: KakaoLocalService,
    private val naverLocalService: NaverLocalService,
    private val localSearchConverter: LocalSearchConverter,
    private val searchHistoryService: SearchHistoryService,
) {

    @Retryable(
        value = [DataIntegrityViolationException::class, CannotAcquireLockException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000, maxDelay = 5000)
    )
    fun searchLocalByKeyword(request: LocalSearchRequest): List<String> {
        val naverResponse = searchLocalByNaver(request, SEARCH_API_RESULT_SIZE)
        val kakaoResponse = searchLocalByKakao(request, SEARCH_API_RESULT_SIZE)

        val addNaverResponse = if (kakaoResponse.documents.size < SEARCH_API_RESULT_SIZE) {
            searchLocalByNaver(request, SEARCH_API_RESULT_SIZE - kakaoResponse.documents.size)
                .let { naverResponse.copy(items = naverResponse.items + it.items) }
                .let { localSearchConverter.convertFromNaver(it) }
        } else emptyList()

        val addKakaoResponse = if (naverResponse.items.size < SEARCH_API_RESULT_SIZE) {
            searchLocalByKakao(request, SEARCH_API_RESULT_SIZE - naverResponse.items.size)
                .let { kakaoResponse.copy(documents = kakaoResponse.documents + it.documents) }
                .let { localSearchConverter.convertFromKakao(it) }
        } else emptyList()

        val kakaoLocalSearchResponse = localSearchConverter.convertFromKakao(kakaoResponse) + addKakaoResponse
        val naverLocalSearchResponse = localSearchConverter.convertFromNaver(naverResponse) + addNaverResponse

        val unionList = kakaoLocalSearchResponse.intersect(naverLocalSearchResponse).toList()
        val kakaoSub = kakaoLocalSearchResponse.minus(naverLocalSearchResponse).toList()
        val naverSub = naverLocalSearchResponse.minus(kakaoLocalSearchResponse).toList()

        searchHistoryService.trackSearchKeyword(request.query)

        return unionList + kakaoSub + naverSub
    }

    private fun searchLocalByNaver(request: LocalSearchRequest, size: Int): NaverLocalSearchResponse {
        val localRequest = localSearchConverter.convertToNaver(request, size)
        return naverLocalService.searchPlaceByNaverApi(localRequest)
    }

    private fun searchLocalByKakao(request: LocalSearchRequest, size: Int): KakaoApiResponse<KakaoLocalSearchDocumentResponse> {
        val localRequest = localSearchConverter.convertToKakao(request, size)
        return kakaoLocalService.searchPlaceByKakaoApi(localRequest)
    }

    companion object {
        const val SEARCH_API_RESULT_SIZE = 5
    }
}
