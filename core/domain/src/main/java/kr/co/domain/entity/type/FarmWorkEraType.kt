package kr.co.domain.entity.type

enum class FarmWorkEraType(
    val value: Int,
    val koreanName: String
) {
    EARLY(1, "상"),
    MID(2, "중"),
    LATE(3, "하");

    companion object {
        fun ofValue(koreanName: String) = when (koreanName) {
            EARLY.koreanName -> EARLY
            MID.koreanName -> MID
            LATE.koreanName -> LATE
            else -> throw IllegalArgumentException("Unknown era")
        }
    }
}