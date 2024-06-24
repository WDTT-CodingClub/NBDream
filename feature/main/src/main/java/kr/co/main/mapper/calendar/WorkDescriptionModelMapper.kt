package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.type.WorkDescriptionType
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.WorkDescriptionModelType

internal object WorkDescriptionModelMapper
    : BaseMapper<DiaryEntity.WorkDescriptionEntity, DiaryModel.WorkDescriptionModel>() {
    override fun toRight(left: DiaryEntity.WorkDescriptionEntity): DiaryModel.WorkDescriptionModel =
        with(left) {
            DiaryModel.WorkDescriptionModel(
                id = id,
                type = when (type) {
                    WorkDescriptionType.SEED_PREP -> WorkDescriptionModelType.SEED_PREP
                    WorkDescriptionType.SEEDBED_PREP -> WorkDescriptionModelType.SEEDBED_PREP
                    WorkDescriptionType.SOW -> WorkDescriptionModelType.SOW
                    WorkDescriptionType.GRAFT -> WorkDescriptionModelType.GRAFT
                    WorkDescriptionType.BURY_SEEDLING -> WorkDescriptionModelType.BURY_SEEDLING
                    WorkDescriptionType.SEEDBED_MANAGE -> WorkDescriptionModelType.SEEDBED_MANAGE
                    WorkDescriptionType.STOP_TILLAGE -> WorkDescriptionModelType.STOP_TILLAGE
                    WorkDescriptionType.INITIAL_FERTIL -> WorkDescriptionModelType.INITIAL_FERTIL
                    WorkDescriptionType.PLANT_SEEDLING -> WorkDescriptionModelType.PLANT_SEEDLING
                    WorkDescriptionType.STILTS_NETS -> WorkDescriptionModelType.STILTS_NETS
                    WorkDescriptionType.ADDITIONAL_FERTIL -> WorkDescriptionModelType.ADDITIONAL_FERTIL
                    WorkDescriptionType.PEST_CONTROL -> WorkDescriptionModelType.PEST_CONTROL
                    WorkDescriptionType.WEED -> WorkDescriptionModelType.WEED
                    WorkDescriptionType.SOIL_COVER -> WorkDescriptionModelType.SOIL_COVER
                    WorkDescriptionType.TRIMMING -> WorkDescriptionModelType.TRIMMING
                    WorkDescriptionType.WATER_MANAGE -> WorkDescriptionModelType.WATER_MANAGE
                    WorkDescriptionType.HOUSE_MANAGE -> WorkDescriptionModelType.HOUSE_MANAGE
                    WorkDescriptionType.TEMP_MANAGE -> WorkDescriptionModelType.TEMP_MANAGE
                    WorkDescriptionType.HARVEST -> WorkDescriptionModelType.HARVEST
                    WorkDescriptionType.PACK -> WorkDescriptionModelType.PACK
                    WorkDescriptionType.SHIP -> WorkDescriptionModelType.SHIP
                    WorkDescriptionType.ETC -> WorkDescriptionModelType.ETC
                },
                description = description
            )
        }

    override fun toLeft(right: DiaryModel.WorkDescriptionModel): DiaryEntity.WorkDescriptionEntity =
        with(right) {
            DiaryEntity.WorkDescriptionEntity(
                id = id,
                type = when (type) {
                    WorkDescriptionModelType.SEED_PREP -> WorkDescriptionType.SEED_PREP
                    WorkDescriptionModelType.SEEDBED_PREP -> WorkDescriptionType.SEEDBED_PREP
                    WorkDescriptionModelType.SOW -> WorkDescriptionType.SOW
                    WorkDescriptionModelType.GRAFT -> WorkDescriptionType.GRAFT
                    WorkDescriptionModelType.BURY_SEEDLING -> WorkDescriptionType.BURY_SEEDLING
                    WorkDescriptionModelType.SEEDBED_MANAGE -> WorkDescriptionType.SEEDBED_MANAGE
                    WorkDescriptionModelType.STOP_TILLAGE -> WorkDescriptionType.STOP_TILLAGE
                    WorkDescriptionModelType.INITIAL_FERTIL -> WorkDescriptionType.INITIAL_FERTIL
                    WorkDescriptionModelType.PLANT_SEEDLING -> WorkDescriptionType.PLANT_SEEDLING
                    WorkDescriptionModelType.STILTS_NETS -> WorkDescriptionType.STILTS_NETS
                    WorkDescriptionModelType.ADDITIONAL_FERTIL -> WorkDescriptionType.ADDITIONAL_FERTIL
                    WorkDescriptionModelType.PEST_CONTROL -> WorkDescriptionType.PEST_CONTROL
                    WorkDescriptionModelType.WEED -> WorkDescriptionType.WEED
                    WorkDescriptionModelType.SOIL_COVER -> WorkDescriptionType.SOIL_COVER
                    WorkDescriptionModelType.TRIMMING -> WorkDescriptionType.TRIMMING
                    WorkDescriptionModelType.WATER_MANAGE -> WorkDescriptionType.WATER_MANAGE
                    WorkDescriptionModelType.HOUSE_MANAGE -> WorkDescriptionType.HOUSE_MANAGE
                    WorkDescriptionModelType.TEMP_MANAGE -> WorkDescriptionType.TEMP_MANAGE
                    WorkDescriptionModelType.HARVEST -> WorkDescriptionType.HARVEST
                    WorkDescriptionModelType.PACK -> WorkDescriptionType.PACK
                    WorkDescriptionModelType.SHIP -> WorkDescriptionType.SHIP
                    WorkDescriptionModelType.ETC -> WorkDescriptionType.ETC
                },
                description = description
            )
        }
}