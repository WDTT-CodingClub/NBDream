package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.GetBulletinDetailData

@Serializable
internal data class GetBulletinDetailResponse(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: BulletinResDto? = null,
)


internal fun GetBulletinDetailResponse.convertToData() = GetBulletinDetailData(
    code = code,
    status = status,
    message = message,
    data = data?.convertToData(),
)
