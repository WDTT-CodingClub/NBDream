package kr.co.domain.entity

data class AccountBookTotalEntity(
    val categories: List<String>,
    val totalRevenue: Long,
    val totalExpense: Long,
    val totalCost: Long,
    val hasNext: Boolean
)
