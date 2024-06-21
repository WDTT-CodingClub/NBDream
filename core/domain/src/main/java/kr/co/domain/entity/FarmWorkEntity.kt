package kr.co.domain.entity


data class FarmWorkEntity(
    val id: Int,
    val startMonth: Int,
    val startEra: Era,
    val endMonth: Int,
    val endEra: Era,
    val category: Category,
    val farmWork: String,
    val videoUrl: String? = null
) {
    enum class Era(
        val value: Int,
        val koreanName: String
    ) {
        EARLY(1, "상"),
        MID(2, "중"),
        LATE(3, "하");

        companion object {
            fun getEra(koreanName: String) = when (koreanName) {
                "상" -> EARLY
                "중" -> MID
                "하" -> LATE
                else -> throw IllegalArgumentException("Unknown era")
            }
        }
    }

    enum class Category(val koreanName: String) {
        GROWTH("생육과정(주요농작업)"),
        PEST("병해충방제"),
        CLIMATE("기상재해 및 예상되는 문제점");

        companion object {
            fun getCategory(koreanName: String) = when (koreanName) {
                "생육과정(주요농작업)" -> GROWTH
                "병해충방제" -> PEST
                "기상재해 및 예상되는 문제점" -> CLIMATE
                else -> throw IllegalArgumentException("Unknown category")
            }
        }
    }
}

