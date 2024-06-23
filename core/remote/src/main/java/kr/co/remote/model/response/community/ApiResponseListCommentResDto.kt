package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.ApiResponseListCommentResDtoData

@Serializable
internal data class ApiResponseListCommentResDto(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: List<CommentResDto>? = null,
)


internal fun ApiResponseListCommentResDto.convertToData() = ApiResponseListCommentResDtoData(
    code = code,
    status = status,
    message = message,
    data = data?.convertToDataList(),
)
