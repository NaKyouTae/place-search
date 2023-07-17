package com.place.search.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(
    name = "kb_tb_search_history",
    indexes = [
        Index(name = "idx_keyword", columnList = "keyword", unique = true),
        Index(name = "idx_views", columnList = "views"),
    ]
)
data class SearchHistory(
    @Id
    @GeneratedValue(generator = "stringIDGenerator")
    @GenericGenerator(name = "stringIDGenerator", strategy = "com.place.search.utils.StringIDGenerator")
    @Column(name = "id", length = 16)
    var id: String? = null,

    @Column(nullable = false, length = 512)
    var keyword: String, // 검색 키워드

    @Column(nullable = false)
    var views: Long, // 검색 수
) : AuditingEntity()
