package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.ApiResponseBulletinsResDtoData

@Serializable
internal data class ApiResponseBulletinsResDto(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: Data? = null,
) {
    @Serializable
    internal data class Data(
        val bulletins: List<BulletinResDto>? = null,
        val hasNext: Boolean? = null,
    )
}


internal fun ApiResponseBulletinsResDto.Data.convertToResultData() =
    ApiResponseBulletinsResDtoData.Data(
        bulletins = bulletins?.convertToDataList(),
        hasNext = hasNext,
    )

internal fun ApiResponseBulletinsResDto.convertToData() = ApiResponseBulletinsResDtoData(
    code = code,
    status = status,
    message = message,
    data = data?.convertToResultData(),
)
