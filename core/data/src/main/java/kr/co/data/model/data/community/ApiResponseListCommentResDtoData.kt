package kr.co.data.model.data.community

data class ApiResponseListCommentResDtoData(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: List<CommentResData>? = null,
)


internal fun ApiResponseListCommentResDtoData.convertToEntities() =
    data?.convertToEntities() ?: emptyList()
