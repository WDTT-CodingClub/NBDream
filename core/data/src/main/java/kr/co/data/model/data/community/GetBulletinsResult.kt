package kr.co.data.model.data.community

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CommentEntity
import kr.co.domain.entity.CropEntity

data class GetBulletinsResult(
    val code: Long? = null,
    val status: String? = null,
    val message: String? = null,
    val data: Data? = null,
) {
    data class Data(
        val bulletins: List<Bulletin>? = null,
        val hasNext: Boolean? = null,
    ) {
        data class Bulletin(
            val authorId: Long? = null,
            val bulletinId: Long? = null,
            val nickname: String? = null,
            val profileImageUrl: String? = null,
            val content: String? = null,
            val crop: String? = null,
            val imageUrls: List<String>? = null,
            val bulletinCategory: String? = null,
            val createdTime: String? = null,
            val comments: List<Comment>? = null,
            val bookmarkedCount: Long? = null,
        ) {
            data class Comment(
                val memberId: Long? = null,
                val nickname: String? = null,
                val profileImageUrl: String? = null,
                val content: String? = null,
                val lastModifiedTime: String? = null,
            )
        }
    }
}


internal fun GetBulletinsResult.Data.Bulletin.Comment.convertToEntity(): CommentEntity? {
    return if (
        this.memberId == null ||
        this.nickname == null ||
        this.profileImageUrl == null ||
        this.content == null ||
        this.lastModifiedTime == null
    ) null
    else CommentEntity(
        memberId = this.memberId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        content = this.content,
        lastModifiedTime = this.lastModifiedTime,
    )
}

internal fun GetBulletinsResult.Data.Bulletin.convertToEntity(): BulletinEntity? {
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
        crop = when (this.crop) {
            "PEPPER" -> CropEntity(CropEntity.Name.PEPPER)
            "RICE" -> CropEntity(CropEntity.Name.RICE)
            "POTATO" -> CropEntity(CropEntity.Name.POTATO)
            "SWEET_POTATO" -> CropEntity(CropEntity.Name.SWEET_POTATO)
            "APPLE" -> CropEntity(CropEntity.Name.APPLE)
            "STRAWBERRY" -> CropEntity(CropEntity.Name.STRAWBERRY)
            "GARLIC" -> CropEntity(CropEntity.Name.GARLIC)
            "LETTUCE" -> CropEntity(CropEntity.Name.LETTUCE)
            "NAPPA_CABBAGE" -> CropEntity(CropEntity.Name.NAPPA_CABBAGE)
            "TOMATO" -> CropEntity(CropEntity.Name.TOMATO)
            else -> CropEntity(CropEntity.Name.PEPPER)
        },
        imageUrls = this.imageUrls,
        bulletinCategory = when (this.bulletinCategory) {
            "Free" -> (BulletinEntity.BulletinCategory.Free)
            "Qna" -> (BulletinEntity.BulletinCategory.Qna)
            "Disease" -> (BulletinEntity.BulletinCategory.Disease)
            else -> BulletinEntity.BulletinCategory.Free
        },
        createdTime = this.createdTime,
        comments = this.comments.mapNotNull { it.convertToEntity() },
        bookmarkedCount = this.bookmarkedCount,
    )
}

internal fun GetBulletinsResult.convertToEntityList(): List<BulletinEntity> {
    return if (data?.bulletins == null) emptyList()
    else data.bulletins.mapNotNull { it.convertToEntity() }
}
