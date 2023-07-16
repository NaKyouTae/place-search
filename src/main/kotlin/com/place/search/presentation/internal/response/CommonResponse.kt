package com.place.search.presentation.internal.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "CommonResponse", description = "API 공통 응답 값")
data class CommonResponse<T>(
    @Schema(name = "isSuccess", description = "API 통신 성공 여부")
    val isSuccess: Boolean,

    @Schema(name = "message", description = "API 통신 메시지")
    val message: String,

    @Schema(name = "result", description = "API 별 값", nullable = true)
    val result: T?,
)
