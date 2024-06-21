package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.ApiResponseLongData

@Serializable
internal data class ApiResponseLong(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: Long?,  // 게시판 id
)


internal fun ApiResponseLong.convertToData() = ApiResponseLongData(
    code = code,
    status = status,
    message = message,
    data = data,
)
