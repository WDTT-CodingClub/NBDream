package kr.co.data.model.data.community

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
