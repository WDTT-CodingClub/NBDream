package kr.co.data.model.data.community

import kr.co.domain.entity.CommentEntity

data class CommentResData(
    val memberId: Long? = null,
    val commentId: Long? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    val lastModifiedTime: String? = null,
)


internal fun CommentResData.convertToEntity(): CommentEntity? {
    return if (
        this.memberId == null ||
        this.commentId == null ||
        this.nickname == null ||
        this.profileImageUrl == null ||
        this.content == null ||
        this.lastModifiedTime == null
    ) null
    else CommentEntity(
        memberId = this.memberId,
        commentId = this.commentId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        content = this.content,
        lastModifiedTime = this.lastModifiedTime,
    )
}

internal fun List<CommentResData>.convertToEntities() =
    this.mapNotNull { it.convertToEntity() }
