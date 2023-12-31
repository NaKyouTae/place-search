package com.place.search.service

import com.place.search.applications.LocalSearchService
import com.place.search.applications.SearchHistoryService
import com.place.search.domain.converter.LocalSearchConverter
import com.place.search.domain.repositories.SearchHistoryRepository
import com.place.search.presentation.external.service.KakaoLocalService
import com.place.search.presentation.external.service.NaverLocalService
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest

abstract class AbstractServiceTest: KoinTest, BehaviorSpec({
    beforeSpec {
        startKoin {
            modules(
                serviceModule,
                converterModule,
                repositoryModule,
            )
        }
    }
}) {
    companion object {
        val serviceModule = module {
            single { mockk<KakaoLocalService>() }
            single { mockk<NaverLocalService>() }
            singleOf(::LocalSearchService)
            singleOf(::SearchHistoryService)
        }
        val converterModule = module {
            singleOf(::LocalSearchConverter)
        }
        val repositoryModule = module {
            single { mockk<SearchHistoryRepository>() }
        }
    }
}
