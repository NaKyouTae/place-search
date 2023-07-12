package com.place.search.domain.converter

import com.place.search.presentation.external.request.KakaoLocalSearchRequest
import com.place.search.presentation.internal.request.LocalSearchRequest
import org.springframework.stereotype.Component

@Component
class LocalSearchConverter {
    fun convert(request: LocalSearchRequest): KakaoLocalSearchRequest {
        return KakaoLocalSearchRequest(
            query = request.query,
            categoryGroupCode = request.categoryGroupCode,
            x = request.x,
            y = request.y,
            radius = request.radius,
            rect = request.rect,
            page = request.page,
            size = request.size,
            sort = request.sort,
        )
    }
}
