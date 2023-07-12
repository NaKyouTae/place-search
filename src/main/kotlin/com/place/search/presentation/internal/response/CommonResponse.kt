package com.place.search.presentation.internal.response

data class CommonResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val result: T?,
)
