package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.type.CropModelType

internal object CropModelMapper
    : BaseMapper<CropEntity, CropModel>() {
    override fun toRight(left: CropEntity): CropModel =
        CropModel.create(CropModelTypeMapper.toRight(left.type))
    override fun toLeft(right: CropModel): CropEntity = when (right.type) {
        CropModelType.PEPPER -> CropEntity(type = CropType.PEPPER)
        CropModelType.RICE -> CropEntity(type = CropType.RICE)
        CropModelType.POTATO -> CropEntity(type = CropType.POTATO)
        CropModelType.SWEET_POTATO -> CropEntity(type = CropType.SWEET_POTATO)
        CropModelType.APPLE -> CropEntity(type = CropType.APPLE)
        CropModelType.STRAWBERRY -> CropEntity(type = CropType.STRAWBERRY)
        CropModelType.GARLIC -> CropEntity(type = CropType.GARLIC)
        CropModelType.LETTUCE -> CropEntity(type = CropType.LETTUCE)
        CropModelType.NAPPA_CABBAGE -> CropEntity(type = CropType.NAPPA_CABBAGE)
        CropModelType.TOMATO -> CropEntity(type = CropType.TOMATO)
    }
}