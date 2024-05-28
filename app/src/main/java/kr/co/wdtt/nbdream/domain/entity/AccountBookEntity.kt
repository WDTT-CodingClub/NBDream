package kr.co.wdtt.nbdream.domain.entity

data class AccountBookEntity(
    val id: String,
    val userId: String? = null, // 응답 시 불필요
    val title: String,
    val category: String,
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