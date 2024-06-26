package kr.co.domain.entity

import java.time.LocalDateTime

data class CommentEntity(
    val memberId: Long,
    val commentId: Long,
    val bulletinId: Long,
    val bulletinAuthorName: String,
    val nickname: String,
    val profileImageUrl: String,
    val content: String,
    val createdTime: LocalDateTime,
    val lastModifiedTime: String,
)
