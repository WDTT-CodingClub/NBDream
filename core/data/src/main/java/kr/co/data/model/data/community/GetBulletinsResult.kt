package kr.co.data.model.data.community

data class GetBulletinsResult(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: Data? = null,
) {
    data class Data(
        val bulletins: List<BulletinResData>? = null,
        val hasNext: Boolean? = null,
    )
}


internal fun GetBulletinsResult.convertToEntities() =
    data?.bulletins?.convertToEntities() ?: emptyList()
