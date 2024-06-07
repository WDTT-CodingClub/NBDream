package kr.co.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccountBookListResponse(
    val resultCode: Int,
    val resultMessage: String,
    val resultData: ResultData
) {
    @Serializable
    data class ResultData(
        val categories: List<String>,
        val totalRevenue: Long,
        val totalExpense: Long,
        val totalCost: Long,
        val items: List<Item>
    ) {
        @Serializable
        data class Item(
            val id: String,
            val title: String,
            val category: String,
            val year: Int,
            val month: Int,
            val day: Int,
            val dayName: String,
            val revenue: Long?,
            val expense: Long?,
            val thumbnail: String,
            val imageSize: Int,
        )
    }
}
