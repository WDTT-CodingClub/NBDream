package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.BulletinResData

@Serializable
internal data class BulletinResDto(
    val authorId: Long? = null,
    val bulletinId: Long? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val content: String? = null,
    val crop: String? = null,
    val imageUrls: List<String>? = null,
    val bulletinCategory: String? = null,
    val createdTime: String? = null,
    val comments: List<CommentResDto>? = null,
    val bookmarkedCount: Int? = null,
)


internal fun BulletinResDto.convertToData() = BulletinResData(
    authorId = authorId,
    bulletinId = bulletinId,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    content = content,
    crop = crop,
    imageUrls = imageUrls,
    bulletinCategory = bulletinCategory,
    createdTime = createdTime,
    comments = comments?.convertToDataList(),
    bookmarkedCount = bookmarkedCount,
)

internal fun List<BulletinResDto>.convertToDataList() = this.map { it.convertToData() }
