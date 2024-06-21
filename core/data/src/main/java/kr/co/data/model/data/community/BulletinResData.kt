package kr.co.data.model.data.community

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity

data class BulletinResData(
    val authorId: Long? = null,
    val bulletinId: Long? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    val crop: String? = null,
    val imageUrls: List<String>? = null,
    val bulletinCategory: String? = null,
    val createdTime: String? = null,
    val comments: List<CommentResData>? = null,
    val bookmarkedCount: Int? = null,
)


internal fun BulletinResData.convertToEntity(): BulletinEntity? {
    return if (
        this.authorId == null ||
        this.bulletinId == null ||
        this.nickname == null ||
        this.profileImageUrl == null ||
        this.content == null ||
        this.crop == null ||
        this.imageUrls == null ||
        this.bulletinCategory == null ||
        this.createdTime == null ||
        this.comments == null ||
        this.bookmarkedCount == null
    ) null
    else BulletinEntity(
        authorId = this.authorId,
        bulletinId = this.bulletinId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        content = this.content,
        crop = this.crop.toCropEntity(),
        imageUrls = this.imageUrls,
        bulletinCategory = this.bulletinCategory.toBulletinCategory(),
        createdTime = this.createdTime,
        comments = this.comments.convertToEntities(),
        bookmarkedCount = this.bookmarkedCount,
    )
}

private fun String.toCropEntity(): CropEntity {
    for (name in CropEntity.Name.entries)
        if (this == name.koreanName) return CropEntity(name)
    return CropEntity(CropEntity.Name.PEPPER)
}

private fun String.toBulletinCategory(): BulletinEntity.BulletinCategory {
    for (name in BulletinEntity.BulletinCategory.entries)
        if (this == name.queryName) return name
    return BulletinEntity.BulletinCategory.Free
}

internal fun List<BulletinResData>.convertToEntities() = this.mapNotNull { it.convertToEntity() }
