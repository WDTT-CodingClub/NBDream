package kr.co.domain.entity.type

enum class FarmWorkType(val koreanName: String) {
    GROWTH("생육과정(주요농작업)"),
    PEST("병충해 방제"),
    CLIMATE("기상재해 및 예상되는 문제점");

    companion object {
        fun ofValue(koreanName: String) = when (koreanName) {
            GROWTH.koreanName -> GROWTH
            PEST.koreanName -> PEST
            CLIMATE.koreanName -> CLIMATE
            else -> throw IllegalArgumentException("FarmWorkType) Unknown category")
        }
    }
}