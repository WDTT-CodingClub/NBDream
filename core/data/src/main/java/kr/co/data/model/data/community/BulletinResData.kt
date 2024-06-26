package kr.co.data.model.data.community

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType
import java.time.LocalDate
import java.time.LocalDateTime

data class BulletinResData(
    val authorId: Long? = null,
    val bulletinId: Long? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    val crop: String? = null,
    val imageUrls: List<String>? = null,
    val bulletinCategory: String? = null,
    val createdTime: LocalDate? = null,
    val comments: List<CommentResData>? = null,
    val bookmarkedCount: Int? = null,
    val author: Boolean? = null,
    val bookmarked: Boolean? = null,
)


internal fun BulletinResData.convertToEntity(): BulletinEntity? {
    return if (
        this.authorId == null ||
        this.bulletinId == null ||
        this.nickname == null ||
        this.profileImageUrl == null ||
        this.crop == null ||
        this.bulletinCategory == null ||
        this.createdTime == null ||
        this.bookmarkedCount == null ||
        this.author == null ||
        this.bookmarked == null
    ) null
    else BulletinEntity(
        authorId = this.authorId,
        bulletinId = this.bulletinId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        content = this.content ?: "",
        crop = this.crop.toCropEntity(),
        imageUrls = this.imageUrls ?: emptyList(),
        bulletinCategory = this.bulletinCategory.toBulletinCategory(),
        createdTime = this.createdTime,
        comments = this.comments?.convertToEntities() ?: emptyList(),
        bookmarkedCount = this.bookmarkedCount,
        author = this.author,
        bookmarked = this.bookmarked,
    )
}

private fun String.toCropEntity(): CropEntity {
//    for (type in CropType.entries)
//        if (this == type.koreanName) return CropEntity(type)
//    return CropEntity(CropType.PEPPER)
    return CropEntity(CropType.ofValue(this))
}

private fun String.toBulletinCategory(): BulletinEntity.BulletinCategory {
    for (name in BulletinEntity.BulletinCategory.entries)
        if (this == name.queryName) return name
    return BulletinEntity.BulletinCategory.Free
}

internal fun List<BulletinResData>.convertToEntities() = this.mapNotNull { it.convertToEntity() }
