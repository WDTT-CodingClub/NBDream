package kr.co.main.calendar.model

import androidx.annotation.StringRes
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.HolidayEntity
import kr.co.main.R
import java.time.LocalDate

data class DiaryModel(
    val id: String,
    val crop: CropModel,
    val registerDate: LocalDate,
    val holidays: List<HolidayEntity> = emptyList(),
    val weatherForecast: WeatherForecastModel,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescriptionModel> = emptyList(),
    val images: List<String> = emptyList(),
    val memo: String = ""
) {
    data class WorkDescriptionModel(
        val id:String? = null,
        @StringRes val typeId: Int,
        val description: String
    ) {
        enum class TypeId(@StringRes val id: Int) {
            NOT_SET(R.string.feature_main_calendar_add_diary_input_work_category),
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
    }
}

internal fun DiaryEntity.convert() = DiaryModel(
    id = id,
    crop = crop.convert(),
    registerDate = registerDate,
    holidays = holidays,
    weatherForecast = weatherForecast.convert(),
    workLaborer = workLaborer,
    workHours = workHours,
    workArea = workArea,
    workDescriptions = workDescriptions.map { it.convert() },
    images = images,
    memo = memo
)

internal fun DiaryModel.convert() = DiaryEntity(
    id = id,
    crop = crop.convert(),
    registerDate = registerDate,
    holidays = holidays,
    weatherForecast = weatherForecast.convert(),
    workLaborer = workLaborer,
    workHours = workHours,
    workArea = workArea,
    workDescriptions = workDescriptions.map { it.convert() },
    images = images,
    memo = memo
)


internal fun DiaryEntity.WorkDescriptionEntity.convert() =
    DiaryModel.WorkDescriptionModel(
        id = id,
        typeId = when (type) {
            DiaryEntity.WorkDescriptionEntity.Type.SEED_PREP -> DiaryModel.WorkDescriptionModel.TypeId.SEED_PREP.id
            DiaryEntity.WorkDescriptionEntity.Type.SEEDBED_PREP -> DiaryModel.WorkDescriptionModel.TypeId.SEEDBED_PREP.id
            DiaryEntity.WorkDescriptionEntity.Type.SOW -> DiaryModel.WorkDescriptionModel.TypeId.SOW.id
            DiaryEntity.WorkDescriptionEntity.Type.GRAFT -> DiaryModel.WorkDescriptionModel.TypeId.GRAFT.id
            DiaryEntity.WorkDescriptionEntity.Type.BURY_SEEDLING -> DiaryModel.WorkDescriptionModel.TypeId.BURY_SEEDLING.id
            DiaryEntity.WorkDescriptionEntity.Type.SEEDBED_MANAGE -> DiaryModel.WorkDescriptionModel.TypeId.SEEDBED_MANAGE.id
            DiaryEntity.WorkDescriptionEntity.Type.STOP_TILLAGE -> DiaryModel.WorkDescriptionModel.TypeId.STOP_TILLAGE.id
            DiaryEntity.WorkDescriptionEntity.Type.INITIAL_FERTIL -> DiaryModel.WorkDescriptionModel.TypeId.INITIAL_FERTIL.id
            DiaryEntity.WorkDescriptionEntity.Type.PLANT_SEEDLING -> DiaryModel.WorkDescriptionModel.TypeId.PLANT_SEEDLING.id
            DiaryEntity.WorkDescriptionEntity.Type.STILTS_NETS -> DiaryModel.WorkDescriptionModel.TypeId.STILTS_NETS.id
            DiaryEntity.WorkDescriptionEntity.Type.ADDITIONAL_FERTIL -> DiaryModel.WorkDescriptionModel.TypeId.ADDITIONAL_FERTIL.id
            DiaryEntity.WorkDescriptionEntity.Type.PEST_CONTROL -> DiaryModel.WorkDescriptionModel.TypeId.PEST_CONTROL.id
            DiaryEntity.WorkDescriptionEntity.Type.WEED -> DiaryModel.WorkDescriptionModel.TypeId.WEED.id
            DiaryEntity.WorkDescriptionEntity.Type.SOIL_COVER -> DiaryModel.WorkDescriptionModel.TypeId.SOIL_COVER.id
            DiaryEntity.WorkDescriptionEntity.Type.TRIMMING -> DiaryModel.WorkDescriptionModel.TypeId.TRIMMING.id
            DiaryEntity.WorkDescriptionEntity.Type.WATER_MANAGE -> DiaryModel.WorkDescriptionModel.TypeId.WATER_MANAGE.id
            DiaryEntity.WorkDescriptionEntity.Type.HOUSE_MANAGE -> DiaryModel.WorkDescriptionModel.TypeId.HOUSE_MANAGE.id
            DiaryEntity.WorkDescriptionEntity.Type.TEMP_MANAGE -> DiaryModel.WorkDescriptionModel.TypeId.TEMP_MANAGE.id
            DiaryEntity.WorkDescriptionEntity.Type.HARVEST -> DiaryModel.WorkDescriptionModel.TypeId.HARVEST.id
            DiaryEntity.WorkDescriptionEntity.Type.PACK -> DiaryModel.WorkDescriptionModel.TypeId.PACK.id
            DiaryEntity.WorkDescriptionEntity.Type.SHIP -> DiaryModel.WorkDescriptionModel.TypeId.SHIP.id
            DiaryEntity.WorkDescriptionEntity.Type.ETC -> DiaryModel.WorkDescriptionModel.TypeId.ETC.id
        },
        description = description
    )

internal fun DiaryModel.WorkDescriptionModel.convert() = DiaryEntity.WorkDescriptionEntity(
    id = id ?: "", // TODO 서버에서 id 생성되기 전 필드 디폴트 값 ""로 설정해도 되는지?
    type = when (typeId) {
        DiaryModel.WorkDescriptionModel.TypeId.SEED_PREP.id -> DiaryEntity.WorkDescriptionEntity.Type.SEED_PREP
        DiaryModel.WorkDescriptionModel.TypeId.SEEDBED_PREP.id -> DiaryEntity.WorkDescriptionEntity.Type.SEEDBED_PREP
        DiaryModel.WorkDescriptionModel.TypeId.SOW.id -> DiaryEntity.WorkDescriptionEntity.Type.SOW
        DiaryModel.WorkDescriptionModel.TypeId.GRAFT.id -> DiaryEntity.WorkDescriptionEntity.Type.GRAFT
        DiaryModel.WorkDescriptionModel.TypeId.BURY_SEEDLING.id -> DiaryEntity.WorkDescriptionEntity.Type.BURY_SEEDLING
        DiaryModel.WorkDescriptionModel.TypeId.SEEDBED_MANAGE.id -> DiaryEntity.WorkDescriptionEntity.Type.SEEDBED_MANAGE
        DiaryModel.WorkDescriptionModel.TypeId.STOP_TILLAGE.id -> DiaryEntity.WorkDescriptionEntity.Type.STOP_TILLAGE
        DiaryModel.WorkDescriptionModel.TypeId.INITIAL_FERTIL.id -> DiaryEntity.WorkDescriptionEntity.Type.INITIAL_FERTIL
        DiaryModel.WorkDescriptionModel.TypeId.PLANT_SEEDLING.id -> DiaryEntity.WorkDescriptionEntity.Type.PLANT_SEEDLING
        DiaryModel.WorkDescriptionModel.TypeId.STILTS_NETS.id -> DiaryEntity.WorkDescriptionEntity.Type.STILTS_NETS
        DiaryModel.WorkDescriptionModel.TypeId.ADDITIONAL_FERTIL.id -> DiaryEntity.WorkDescriptionEntity.Type.ADDITIONAL_FERTIL
        DiaryModel.WorkDescriptionModel.TypeId.PEST_CONTROL.id -> DiaryEntity.WorkDescriptionEntity.Type.PEST_CONTROL
        DiaryModel.WorkDescriptionModel.TypeId.WEED.id -> DiaryEntity.WorkDescriptionEntity.Type.WEED
        DiaryModel.WorkDescriptionModel.TypeId.SOIL_COVER.id -> DiaryEntity.WorkDescriptionEntity.Type.SOIL_COVER
        DiaryModel.WorkDescriptionModel.TypeId.TRIMMING.id -> DiaryEntity.WorkDescriptionEntity.Type.TRIMMING
        DiaryModel.WorkDescriptionModel.TypeId.WATER_MANAGE.id -> DiaryEntity.WorkDescriptionEntity.Type.WATER_MANAGE
        DiaryModel.WorkDescriptionModel.TypeId.HOUSE_MANAGE.id -> DiaryEntity.WorkDescriptionEntity.Type.HOUSE_MANAGE
        DiaryModel.WorkDescriptionModel.TypeId.TEMP_MANAGE.id -> DiaryEntity.WorkDescriptionEntity.Type.TEMP_MANAGE
        DiaryModel.WorkDescriptionModel.TypeId.HARVEST.id -> DiaryEntity.WorkDescriptionEntity.Type.HARVEST
        DiaryModel.WorkDescriptionModel.TypeId.PACK.id -> DiaryEntity.WorkDescriptionEntity.Type.PACK
        DiaryModel.WorkDescriptionModel.TypeId.SHIP.id -> DiaryEntity.WorkDescriptionEntity.Type.SHIP
        DiaryModel.WorkDescriptionModel.TypeId.ETC.id -> DiaryEntity.WorkDescriptionEntity.Type.ETC
        else -> throw IllegalArgumentException("Unknown work type")
    },
    description = description
)
