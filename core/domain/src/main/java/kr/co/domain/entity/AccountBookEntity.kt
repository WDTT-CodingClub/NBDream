package kr.co.domain.entity


data class AccountBookEntity(
    val id: Long,
    val title: String? = null,
    val category: Category? = null,
    val imageUrl: List<String> = emptyList(),
    val registerDateTime: String? = null,
    val year: Int? = null,
    val month: Int? = null,
    val day: Int? = null,
    val dayName: String? = null,
    val transactionType: TransactionType? = null,
    val amount: Long? = null,
) {
    enum class Category {
        FARM_PRODUCT_SALES,
        FERTILIZER,
        SEEDS_AND_SEEDLINGS,
        PESTICIDES,
        FARM_MACHINERY,
        DISTRIBUTION_EXPENSE,
        RENT,
        SEED_SALES,
        FARM_EQUIPMENT_USAGE_FEE,
        FARM_EQUIPMENT_RENTAL,
        OTHER;
    }

    enum class TransactionType {
        REVENUE,
        EXPENSE
    }

    enum class SortOrder {
        EARLIEST,
        OLDEST
    }
}