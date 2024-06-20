package kr.co.data.model.data

data class AccountBookData(
    val id: Long,
    val title: String,
    val category: String,
    val transactionType: String,
    val amount: Long,
    val registerDateTime: String,
    val imageUrls: List<String>
)