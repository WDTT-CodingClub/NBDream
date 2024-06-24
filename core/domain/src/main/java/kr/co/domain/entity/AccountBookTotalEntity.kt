package kr.co.domain.entity

data class AccountBookTotalEntity(
    val categories: List<String>,
    val totalRevenue: Long,
    val totalExpense: Long,
    val totalCost: Long,
    val revenuePercent: List<PercentCategory>,
    val expensePercent: List<PercentCategory>,
    val hasNext: Boolean
) {
    data class PercentCategory(
        val percent: Int,
        val category: String
    )
}