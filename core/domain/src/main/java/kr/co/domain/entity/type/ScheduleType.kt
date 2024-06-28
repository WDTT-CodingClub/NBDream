package kr.co.domain.entity.type

sealed class ScheduleType(val koreanName: String) {
    data object All : ScheduleType("전체")
    data class Crop(val cropType: CropType) : ScheduleType(cropType.koreanName)

    companion object {
        fun ofValue(koreanName: String) = when (koreanName) {
            All.koreanName -> All
            in CropType.entries.map { it.koreanName } -> Crop(CropType.ofValue(koreanName))
            else -> throw IllegalArgumentException("Schedule Type) Unknown schedule category")
        }
    }
}