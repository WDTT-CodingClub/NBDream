package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.CommentResData
import kr.co.remote.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
internal data class CommentResDto(
    val memberId: Long? = null,
    val commentId: Long? = null,
    val bulletinId: Long? = null,
    val bulletinAuthor: String? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdDate: LocalDateTime,
    val lastModifiedTime: String? = null,
)


internal fun CommentResDto.convertToData() = CommentResData(
    memberId = memberId,
    commentId = commentId,
    bulletinId = bulletinId,
    bulletinAuthorName = bulletinAuthor,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    content = content,
    createdTime = createdDate,
    lastModifiedTime = lastModifiedTime,
)

internal fun List<CommentResDto>.convertToDataList() = this.map { it.convertToData() }
