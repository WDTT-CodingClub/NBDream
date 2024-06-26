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
) {
    companion object {
        fun dummy(idx: Int = 0) = CommentEntity(
            memberId = 10L + idx,
            commentId = 10L + idx,
            nickname = "댓글닉네임${10L + idx}",
            profileImageUrl = "https://placehold.co/${60 + idx}x${60 + idx}",
            content = "댓글 ${10L + idx} 내용 어쩌구 저쩌구 ${10L + idx} 댓글끝",
            lastModifiedTime = "0000.00.00 00:${10 + idx}",
        )
    }
}
