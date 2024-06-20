package kr.co.main.calendar.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.DiaryEntity
import kr.co.main.calendar.model.DiaryModel

internal object WorkDescriptionModelMapper
    : BaseMapper<DiaryEntity.WorkDescriptionEntity, DiaryModel.WorkDescriptionModel>() {
    override fun toRight(left: DiaryEntity.WorkDescriptionEntity): DiaryModel.WorkDescriptionModel =
        with(left) {
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
        }

    override fun toLeft(right: DiaryModel.WorkDescriptionModel): DiaryEntity.WorkDescriptionEntity =
        with(right) {
            DiaryEntity.WorkDescriptionEntity(
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
        }
}