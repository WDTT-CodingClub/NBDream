package kr.co.data.model.data.community

data class GetBulletinDetailData(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: BulletinResData? = null,
)
