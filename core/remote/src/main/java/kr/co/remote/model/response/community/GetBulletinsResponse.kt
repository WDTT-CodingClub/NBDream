package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.GetBulletinsResult

@Serializable
internal data class GetBulletinsResponse(
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


internal fun GetBulletinsResponse.Data.convertToResultData() = GetBulletinsResult.Data(
    bulletins = bulletins?.convertToDataList(),
    hasNext = hasNext,
)

internal fun GetBulletinsResponse.convertToResult() = GetBulletinsResult(
    code = code,
    status = status,
    message = message,
    data = data?.convertToResultData(),
)
