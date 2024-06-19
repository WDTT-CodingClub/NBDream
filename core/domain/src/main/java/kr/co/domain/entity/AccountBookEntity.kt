package kr.co.domain.entity


data class AccountBookEntity(
    val id: String,
    val title: String,
    val category: Category = Category.OTHER,
    val imageUrl: List<String> = emptyList(),
    val registerDateTime: String? = null,
    val year: Int? = null,
    val month: Int? = null,
    val day: Int? = null,
    val dayName: String? = null,
    val transactionType: TransactionType? = null,
    val amount: Long? = null,
) {
    enum class Category(val display: String, val value: String) {
        FARM_PRODUCT_SALES("농산물 판매", "farm_product_sales"),
        FERTILIZER("비료", "fertilizer"),
        SEEDS_AND_SEEDLINGS("농자/종묘", "seeds_and_seedlings"),
        PESTICIDES("농약", "pesticides"),
        FARM_MACHINERY("농기계", "farm_machinery"),
        DISTRIBUTION_EXPENSE("유통비", "distribution_expense"),
        RENT("임차료", "rent"),
        SEED_SALES("종자 판매", "seed_sales"),
        FARM_EQUIPMENT_USAGE_FEE("농기구 사용료", "farm_equipment_usage_fee"),
        FARM_EQUIPMENT_RENTAL("농기구 대여", "farm_equipment_rental"),
        OTHER("기타", "other"),
        ;
    }

    enum class TransactionType(val value: String) {
        REVENUE("revenue"),
        EXPENSE("expense")
    }
}

enum class SortOrder(val value: String) {
    RECENCY("earliest"),
    OLDEST("oldest")
}