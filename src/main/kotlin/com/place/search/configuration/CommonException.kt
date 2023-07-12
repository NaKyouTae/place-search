package com.place.search.configuration

import org.springframework.http.HttpStatus

class CommonException private constructor(
    val msg: String?,
    val extra: Map<String, String> = emptyMap(),
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    val title: String? = null
) : RuntimeException(msg) {
    constructor(msg: String, extra: Map<String, String> = emptyMap(), title: String? = null) : this(
        msg = msg,
        extra = extra,
        httpStatus = HttpStatus.BAD_REQUEST,
        title = title
    )

    constructor(msg: String, httpStatus: HttpStatus) : this(
        msg = msg,
        extra = emptyMap(),
        httpStatus = httpStatus,
        title = null
    )
}
