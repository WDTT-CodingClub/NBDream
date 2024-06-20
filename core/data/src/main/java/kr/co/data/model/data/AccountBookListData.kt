package kr.co.data.model.data

data class AccountBookListData(
    val categories: List<String>,
    val totalRevenue: Long,
    val totalExpense: Long,
    val totalCost: Long,
    val hasNext: Boolean,
    val items: List<Item>
) {
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
        val thumbnail: String,
        val imageSize: Int,
    )
}