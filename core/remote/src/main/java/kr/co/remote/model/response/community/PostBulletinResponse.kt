package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.PostBulletinResult

@Serializable
internal data class PostBulletinResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: Long?,  // 게시판 id
)


internal fun PostBulletinResponse.convertToResult() = PostBulletinResult(
    code = code,
    status = status,
    message = message,
    data = data,
)
