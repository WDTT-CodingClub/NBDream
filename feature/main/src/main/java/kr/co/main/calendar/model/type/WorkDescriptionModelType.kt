package kr.co.main.calendar.model.type

import androidx.annotation.StringRes
import kr.co.main.R

internal enum class WorkDescriptionModelType(@StringRes val id: Int) {
    SEED_PREP(R.string.feature_main_calendar_work_type_seed_prep),
    SEEDBED_PREP(R.string.feature_main_calendar_work_type_seedbed_prep),
    SOW(R.string.feature_main_calendar_work_type_sow),
    GRAFT(R.string.feature_main_calendar_work_type_graft),
    BURY_SEEDLING(R.string.feature_main_calendar_work_type_bury_seedling),
    SEEDBED_MANAGE(R.string.feature_main_calendar_work_type_seedbed_manage),
    STOP_TILLAGE(R.string.feature_main_calendar_work_type_stop_tillage),
    INITIAL_FERTIL(R.string.feature_main_calendar_work_type_initial_fertil),
    PLANT_SEEDLING(R.string.feature_main_calendar_work_type_plant_seedling),
    STILTS_NETS(R.string.feature_main_calendar_work_type_stilts_nets),
    ADDITIONAL_FERTIL(R.string.feature_main_calendar_work_type_additional_fertil),
    PEST_CONTROL(R.string.feature_main_calendar_work_type_pest_control),
    WEED(R.string.feature_main_calendar_work_type_weed),
    SOIL_COVER(R.string.feature_main_calendar_work_type_soil_cover),
    TRIMMING(R.string.feature_main_calendar_work_type_trimming),
    WATER_MANAGE(R.string.feature_main_calendar_work_type_water_manage),
    HOUSE_MANAGE(R.string.feature_main_calendar_work_type_house_manage),
    TEMP_MANAGE(R.string.feature_main_calendar_work_type_temp_manage),
    HARVEST(R.string.feature_main_calendar_work_type_harvest),
    PACK(R.string.feature_main_calendar_work_type_pack),
    SHIP(R.string.feature_main_calendar_work_type_ship),
    ETC(R.string.feature_main_calendar_work_type_etc)
}