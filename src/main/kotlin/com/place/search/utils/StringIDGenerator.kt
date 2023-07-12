package com.place.search.utils

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.UUID

@Component
class StringIDGenerator: IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, entity: Any?): Serializable {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        uuid.filter { it.isLetter() }
        return uuid.substring(0, 16)
    }
}
