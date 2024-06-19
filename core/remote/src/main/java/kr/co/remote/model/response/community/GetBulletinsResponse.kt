package kr.co.remote.model.response.community

import kotlinx.serialization.Serializable
import kr.co.data.model.data.community.GetBulletinsResult

@Serializable
data class GetBulletinsResponse(
    val code: Long? = null,
    val status: String? = null,
    val message: String? = null,
    val data: Data? = null,
) {
    @Serializable
    data class Data(
        val bulletins: List<Bulletin>? = null,
        val hasNext: Boolean? = null,
    ) {
        @Serializable
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
            @Serializable
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


internal fun GetBulletinsResponse.Data.Bulletin.Comment.convertToResultComment() =
    GetBulletinsResult.Data.Bulletin.Comment(
        memberId = memberId,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        content = content,
        lastModifiedTime = lastModifiedTime,
    )

internal fun GetBulletinsResponse.Data.Bulletin.convertToResultBulletin() =
    GetBulletinsResult.Data.Bulletin(
        authorId = authorId,
        bulletinId = bulletinId,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        content = content,
        crop = crop,
        imageUrls = imageUrls,
        bulletinCategory = bulletinCategory,
        createdTime = createdTime,
        comments = comments?.map { it.convertToResultComment() },
        bookmarkedCount = bookmarkedCount,
    )

internal fun GetBulletinsResponse.Data.convertToResultData() =
    GetBulletinsResult.Data(
        bulletins = bulletins?.map { it.convertToResultBulletin() },
        hasNext = hasNext,
    )

internal fun GetBulletinsResponse.convertToResult() = GetBulletinsResult(
    code = code,
    status = status,
    message = message,
    data = data?.convertToResultData(),
)
