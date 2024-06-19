package kr.co.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccountBookListResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val categories: List<String>,
        val totalRevenue: Long,
        val totalExpense: Long,
        val totalCost: Long,
        val hasNext: Boolean,
        val items: List<Item>
    ) {
        @Serializable
        data class Item(
            val id: Long,
            val title: String,
            val category: String,
            val year: Int,
            val month: Int,
            val day: Int,
            val dayName: String,
            val transactionType: String,
            val amount: Long,
            val thumbnail: String?,
            val imageSize: Int
        )
    }
}
