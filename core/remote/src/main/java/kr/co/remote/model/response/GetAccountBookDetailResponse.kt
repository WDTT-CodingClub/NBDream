package kr.co.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccountBookDetailResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val id: Long,
        val title: String,
        val category: String,
        val transactionType: String,
        val amount: Long,
        val registerDateTime: String,
        val imageUrls: List<String>
    )
}
