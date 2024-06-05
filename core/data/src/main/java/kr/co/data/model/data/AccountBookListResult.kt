package kr.co.data.model.data

data class AccountBookListResult(
    val categories: List<String>,
    val totalRevenue: Long,
    val totalExpense: Long,
    val totalCost: Long,
    val items: List<Item>
) {
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