package com.place.search.applications

import com.place.search.domain.entity.SearchHistory
import com.place.search.domain.repositories.SearchHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchHistoryService(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    @Transactional
    fun trackSearchKeyword(keyword: String) {
        val searchHistory = searchHistoryRepository.findByKeyword(keyword)

        val updatedSearchHistory =
            searchHistory?.let { it.copy(views = it.views + 1) } ?: SearchHistory(keyword = keyword, views = 1)

        searchHistoryRepository.save(updatedSearchHistory)
    }

    @Transactional(readOnly = true)
    fun getSearchKeywordTop10(): List<SearchHistory> {
        return searchHistoryRepository.findTop10ByOrderByViewsDescCreatedAtDesc()
    }
}
