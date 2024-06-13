package kr.co.domain.entity

import java.time.LocalDate

data class DiaryEntity(
    val id: String,
    val crop: CropEntity,
    val registerDate: LocalDate,
    val holidays: List<HolidayEntity> = emptyList(),
    val weatherForecast: WeatherForecastEntity,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescriptionEntity> = emptyList(),
    val images: List<String> = emptyList(),
    val memo: String = ""
) {
    data class WorkDescriptionEntity(
        val id:String,
        val type: Type,
        val description: String,
    ) {
        enum class Type {
            SEED_PREP,
            SEEDBED_PREP,
            SOW,
            GRAFT,
            BURY_SEEDLING,
            SEEDBED_MANAGE,
            STOP_TILLAGE,
            INITIAL_FERTIL,
            PLANT_SEEDLING,
            STILTS_NETS,
            ADDITIONAL_FERTIL,
            PEST_CONTROL,
            WEED,
            SOIL_COVER,
            TRIMMING,
            WATER_MANAGE,
            HOUSE_MANAGE,
            TEMP_MANAGE,
            HARVEST,
            PACK,
            SHIP,
            ETC
        }
    }
}




