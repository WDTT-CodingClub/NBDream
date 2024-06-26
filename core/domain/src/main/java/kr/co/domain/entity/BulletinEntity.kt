package kr.co.domain.entity

import kr.co.domain.entity.type.CropType
import java.time.LocalDate
import java.time.LocalDateTime

data class BulletinEntity(
    val authorId: Long,
    val bulletinId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val content: String,
    val crop: CropEntity,
    val imageUrls: List<String>,
    val bulletinCategory: BulletinCategory,
    val createdTime: LocalDate,
    val comments: List<CommentEntity>,
    val bookmarkedCount: Int,
    val author: Boolean,
    val bookmarked: Boolean,
) {
    companion object {
        fun dummy(idx: Int = 0) = BulletinEntity(
            authorId = 22222220 + (idx).toLong(),
            bulletinId = 33333330 + (idx).toLong(),
            nickname = "nickname${idx}",
            profileImageUrl = "https://placehold.co/${70 + idx}x${70 + idx}",
            content = "content${idx}",
            crop = CropEntity(CropType.entries[(idx) % CropType.entries.size]),
            imageUrls = List(5) { "https://placehold.co/${220 + idx}x${220 + idx}" },
            bulletinCategory = BulletinCategory.entries[(idx) % BulletinCategory.entries.size],
            createdTime = LocalDate.now(),
            comments = List(5) { CommentEntity.dummy(it) },
            bookmarkedCount = 220 + idx,
            author = idx % 2 == 0,
            bookmarked = idx % 2 == 1,
        )
    }

    enum class BulletinCategory(
        val queryName: String,
        val koreanName: String,
    ) {
        Free("free", "자유 주제"),
        Qna("qna", "질문"),
        Disease("bug", "병해충"),
        ;

        companion object {
            fun fromKoreanName(koreanName: String) = entries.first { it.koreanName == koreanName }
        }
    }

}
