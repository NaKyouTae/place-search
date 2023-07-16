package com.place.search.domain.repositories

import com.place.search.domain.entity.SearchHistory
import org.springframework.data.jpa.repository.JpaRepository

interface SearchHistoryRepository: JpaRepository<SearchHistory, String> {
    fun findByKeyword(keyword: String): SearchHistory?
    fun findTop10ByOrderByViewsDescCreatedAtDesc(): List<SearchHistory>
}
