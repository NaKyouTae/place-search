package com.place.search.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditingEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    open var createdAt: Instant = Instant.now()

    @LastModifiedDate
    @Column(nullable = true)
    open var updatedAt: Instant? = null
}
