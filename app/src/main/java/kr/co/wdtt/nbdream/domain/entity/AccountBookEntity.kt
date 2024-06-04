package kr.co.wdtt.nbdream.domain.entity

import java.io.File

data class AccountBookEntity(
    val id: String,
    val userId: String? = null, // 응답 시 불필요
    val title: String,
    val category: Category = Category.OTHER,
    val imageUrl: List<String> = emptyList(), // 응답
    val imageFile: List<File> = emptyList(),
    val registerDateTime: String? = null, // 날짜 + 시각
    val year: Int? = null, // 응답
    val month: Int? = null, // 응답
    val day: Int? = null, // 응답
    val dayName: String? = null, // 응답 요일
    val revenue: Long? = null,
    val expense: Long? = null,
    val totalRevenue: Long? = null, // 총 수입
    val totalExpense: Long? = null, // 총 지출
    val totalCost: Long? = null,  // 총 비용
) {
    enum class Category(
        val value: String
    ) {
        FARM_PRODUCT_SALES("농산물 판매"),
        FERTILIZER("비료"),
        SEEDS_AND_SEEDLINGS("농자/종묘"),
        PESTICIDES("농약"),
        FARM_MACHINERY("농기계"),
        DISTRIBUTION_EXPENSE("유통비"),
        RENT("임차료"),
        SEED_SALES("종자 판매"),
        FARM_EQUIPMENT_USAGE_FEE("농기구 사용료"),
        FARM_EQUIPMENT__RENTAL("농기구 대여"),
        OTHER("기타"),
    }
}

enum class SortOrder {
    RECENCY, OLDEST
}