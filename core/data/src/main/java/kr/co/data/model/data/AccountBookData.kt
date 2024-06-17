package kr.co.data.model.data

data class AccountBookData(
    val id: String,
    val title: String,
    val category: String,
    val transactionType: String,
    val amount: Long,
    val imageUrls: List<String>,
    val registerDateTime: String
)