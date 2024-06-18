package kr.co.domain.entity

data class CommentEntity(
    val memberId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val content: String,
    val lastModifiedTime: String,
)
