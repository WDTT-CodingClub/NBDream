package kr.co.wdtt.nbdream.domain.entity

import androidx.annotation.StringRes
import kr.co.wdtt.nbdream.R

data class AccountBookEntity(
    val id: String,
    val userId: String? = null, // 응답 시 불필요
    val title: String,
    val category: Category = Category.OTHER,
    val imageUrl: List<String> = emptyList(), // 응답
    // add 요청 이미지
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
)

enum class Category(
    @StringRes val category: Int
) {
    FARM_PRODUCT_SALES(R.string.farm_product_sales),
    FERTILIZER(R.string.fertilizer),
    SEEDS_AND_SEEDLINGS(R.string.seeds_and_seedlings),
    PESTICIDES(R.string.pesticides),
    FARM_MACHINERY(R.string.farm_machinery),
    DISTRIBUTION_EXPENSE(R.string.distribution_expense),
    RENT(R.string.rent),
    SEED_SALES(R.string.seed_sales),
    FARM_EQUIPMENT_USAGE_FEE(R.string.farm_equipment_usage_fee),
    TENT_RENTAL(R.string.farm_equipment_rental),
    OTHER(R.string.other),
}
