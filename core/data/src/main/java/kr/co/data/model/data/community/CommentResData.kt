package kr.co.data.model.data.community

import kr.co.domain.entity.CommentEntity
import java.time.LocalDateTime

data class CommentResData(
    val memberId: Long? = null,
    val commentId: Long? = null,
    val bulletinId: Long? = null,
    val bulletinAuthorName: String? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    val isAuthor: Boolean? = null,
    val createdTime: LocalDateTime,
    val lastModifiedTime: String? = null,
)


internal fun CommentResData.convertToEntity(): CommentEntity? {
    return if (
        this.memberId == null ||
        this.commentId == null ||
        this.nickname == null ||
        this.profileImageUrl == null ||
        this.content == null ||
        this.isAuthor == null ||
        this.lastModifiedTime == null ||
        this.bulletinId == null ||
        this.bulletinAuthorName == null
    ) null
    else CommentEntity(
        memberId = this.memberId,
        commentId = this.commentId,
        bulletinId = bulletinId,
        bulletinAuthorName = bulletinAuthorName,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        content = this.content,
        isAuthor = this.isAuthor,
        createdTime = this.createdTime,
        lastModifiedTime = this.lastModifiedTime,
    )
}

internal fun List<CommentResData>.convertToEntities() =
    this.mapNotNull { it.convertToEntity() }
