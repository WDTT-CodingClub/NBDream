package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.CommentResData

@Serializable
internal data class CommentResDto(
    val memberId: Long? = null,
    val commentId: Long? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    val lastModifiedTime: String? = null,
)


internal fun CommentResDto.convertToData() = CommentResData(
    memberId = memberId,
    commentId = commentId,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    content = content,
    lastModifiedTime = lastModifiedTime,
)

internal fun List<CommentResDto>.convertToDataList() = this.map { it.convertToData() }
