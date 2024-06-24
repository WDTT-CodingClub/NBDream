package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.type.CropType
import kr.co.main.model.calendar.type.CropModelType

internal object CropModelTypeMapper
    : BaseMapper<CropType, CropModelType>() {
    override fun toRight(left: CropType): CropModelType = when(left){
        CropType.PEPPER -> CropModelType.PEPPER
        CropType.RICE -> CropModelType.RICE
        CropType.POTATO -> CropModelType.POTATO
        CropType.SWEET_POTATO -> CropModelType.SWEET_POTATO
        CropType.APPLE -> CropModelType.APPLE
        CropType.STRAWBERRY -> CropModelType.STRAWBERRY
        CropType.GARLIC -> CropModelType.GARLIC
        CropType.LETTUCE -> CropModelType.LETTUCE
        CropType.NAPPA_CABBAGE -> CropModelType.NAPPA_CABBAGE
        CropType.TOMATO -> CropModelType.TOMATO
    }

    override fun toLeft(right: CropModelType): CropType = when(right){
        CropModelType.PEPPER -> CropType.PEPPER
        CropModelType.RICE -> CropType.RICE
        CropModelType.POTATO -> CropType.POTATO
        CropModelType.SWEET_POTATO -> CropType.SWEET_POTATO
        CropModelType.APPLE -> CropType.APPLE
        CropModelType.STRAWBERRY -> CropType.STRAWBERRY
        CropModelType.GARLIC -> CropType.GARLIC
        CropModelType.LETTUCE -> CropType.LETTUCE
        CropModelType.NAPPA_CABBAGE -> CropType.NAPPA_CABBAGE
        CropModelType.TOMATO -> CropType.TOMATO
    }
}