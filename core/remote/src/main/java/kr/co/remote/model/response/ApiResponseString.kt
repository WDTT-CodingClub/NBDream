package kr.co.remote.model.response

import kotlinx.serialization.Serializable
import kr.co.data.model.data.ApiResponseStringData

@Serializable
internal data class ApiResponseString(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: String?,
)


internal fun ApiResponseString.convertToData() = ApiResponseStringData(
    code = code,
    status = status,
    message = message,
    data = data,
)
