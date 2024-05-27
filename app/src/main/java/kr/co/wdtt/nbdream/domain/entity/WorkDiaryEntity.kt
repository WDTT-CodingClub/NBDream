package kr.co.wdtt.nbdream.domain.entity

import androidx.annotation.StringRes
import kr.co.wdtt.nbdream.R

data class WorkDiaryEntity(
    val id:String? = null,
    val userId: String? = null,
    val dreamCrop: DreamCrop,
    val registerDate: String,
    val year:Int,
    val month:Int,
    val day:Int,
    val weatherForecast: WeatherForecastEntity,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescription> = emptyList(),
    val images: List<DreamImage> = emptyList(),
    val memo: String
)


data class WorkDescription(
    val type: WorkType,
    val description: String,
)

enum class WorkType(@StringRes val id: Int){
    SEED_PREP(R.string.calendar_work_type_seed_prep),
    SEEDBED_PREP(R.string.calendar_work_type_seedbed_prep),
    SOW(R.string.calendar_work_type_sow),
    GRIFT(R.string.calendar_work_type_graft),
    BURY_SEEDLING(R.string.calendar_work_type_bury_seedling),
    SEEDBED_MANAGE(R.string.calendar_work_type_seedbed_manage),
    STOP_TILLAGE(R.string.calendar_work_type_stop_tillage),
    INITIAL_FERTIL(R.string.calendar_work_type_initial_fertil),
    PLANT_SEEDLING(R.string.calendar_work_type_plant_seedling),
    STILTS_NETS(R.string.calendar_work_type_stilts_nets),
    ADDITIONAL_FERTIL(R.string.calendar_work_type_additional_fertil),
    PEST_CONTROL(R.string.calendar_work_type_pest_control),
    WEED(R.string.calendar_work_type_weed),
    SOIL_COVER(R.string.calendar_work_type_soil_cover),
    TRIMMING(R.string.calendar_work_type_trimming),
    WATER_MANAGE(R.string.calendar_work_type_water_manage),
    HOUSE_MANAGE(R.string.calendar_work_type_house_manage),
    TEMP_MANAGE(R.string.calendar_work_type_temp_manage),
    HARVEST(R.string.calendar_work_type_harvest),
    PACK(R.string.calendar_work_type_pack),
    SHIP(R.string.calendar_work_type_ship),
    ETC(R.string.calendar_work_type_etc)
}

