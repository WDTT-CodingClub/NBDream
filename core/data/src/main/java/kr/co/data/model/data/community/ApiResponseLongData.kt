package kr.co.data.model.data.community

data class ApiResponseLongData(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: Long?,  // 게시판 id
)
