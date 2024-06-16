package kr.co.data.model.data

data class AccountBookData(
    val id: String,
    val title: String,
    val category: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayName: String,
    val transactionType: String,
    val amount: Long,
    val imageUrls: List<String>,
    val registerDateTime: String
)