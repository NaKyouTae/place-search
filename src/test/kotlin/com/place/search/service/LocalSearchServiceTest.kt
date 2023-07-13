package com.place.search.service

import com.place.search.applications.LocalSearchService
import com.place.search.common.DataGenerator
import com.place.search.presentation.external.service.KakaoLocalService
import com.place.search.presentation.external.service.NaverLocalService
import com.place.search.presentation.internal.request.LocalSearchRequest
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.koin.test.inject


class LocalSearchServiceTest: AbstractServiceTest() {
    init {
        val kakaoLocalService: KakaoLocalService by inject()
        val naverLocalService: NaverLocalService by inject()
        val localSearchService: LocalSearchService by inject()

        Given("장소 검색") {
            When("'곱창' 키워드로 검색") {
                val query = "곱창"

                every { kakaoLocalService.searchPlaceByKakaoApi(any()) } answers {
                    DataGenerator.generateKakaoLocalSearchApiResponse(listOf("A곱창", "B곱창", "C곱창", "D곱창"))
                }
                every { naverLocalService.searchPlaceByNaverApi(any()) } answers {
                    DataGenerator.generateNaverLocalSearchApiResponse(listOf("A곱창", "E곱창", "D곱창", "C곱창"))
                }

                val request = LocalSearchRequest(query)
                val places = localSearchService.searchLocalByKeyword(request)

                Then("공통 장소, 카카오 장소, 네이버 장소 순서로 리스트 반황") {
                    places shouldBe listOf("A곱창", "C곱창", "D곱창", "B곱창", "E곱창")
                }
            }

            When("'은행' 키워드로 검색") {
                val query = "은행"

                every { kakaoLocalService.searchPlaceByKakaoApi(any()) } answers {
                    DataGenerator.generateKakaoLocalSearchApiResponse(listOf("카카오뱅크", "우리은행", "국민은행", "부산은행", "새마을금고"))
                }
                every { naverLocalService.searchPlaceByNaverApi(any()) } answers {
                    DataGenerator.generateNaverLocalSearchApiResponse(listOf("카카오뱅크", "부산은행", "하나은행", "국민은행", "기업은행"))
                }

                val request = LocalSearchRequest(query)
                val places = localSearchService.searchLocalByKeyword(request)

                Then("공통 장소, 카카오 장소, 네이버 장소 순서로 리스트 반황") {
                    places shouldBe listOf("카카오뱅크", "국민은행", "부산은행", "우리은행", "새마을금고", "하나은행", "기업은행")
                }
            }
        }
    }
}
