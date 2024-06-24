package kr.co.main.calendar.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.CropEntity
import kr.co.main.calendar.model.CropModel

internal object CropModelMapper
    : BaseMapper<CropEntity, CropModel>() {
    override fun toRight(left: CropEntity): CropModel = when (left.name) {
        CropEntity.Name.PEPPER -> CropModel.PEPPER
        CropEntity.Name.RICE -> CropModel.RICE
        CropEntity.Name.POTATO -> CropModel.POTATO
        CropEntity.Name.SWEET_POTATO -> CropModel.SWEET_POTATO
        CropEntity.Name.APPLE -> CropModel.APPLE
        CropEntity.Name.STRAWBERRY -> CropModel.STRAWBERRY
        CropEntity.Name.GARLIC -> CropModel.GARLIC
        CropEntity.Name.LETTUCE -> CropModel.LETTUCE
        CropEntity.Name.NAPPA_CABBAGE -> CropModel.NAPPA_CABBAGE
        CropEntity.Name.TOMATO -> CropModel.TOMATO
    }

    override fun toLeft(right: CropModel): CropEntity = when (right) {
        CropModel.PEPPER -> CropEntity(name = CropEntity.Name.PEPPER)
        CropModel.RICE -> CropEntity(name = CropEntity.Name.RICE)
        CropModel.POTATO -> CropEntity(name = CropEntity.Name.POTATO)
        CropModel.SWEET_POTATO -> CropEntity(name = CropEntity.Name.SWEET_POTATO)
        CropModel.APPLE -> CropEntity(name = CropEntity.Name.APPLE)
        CropModel.STRAWBERRY -> CropEntity(name = CropEntity.Name.STRAWBERRY)
        CropModel.GARLIC -> CropEntity(name = CropEntity.Name.GARLIC)
        CropModel.LETTUCE -> CropEntity(name = CropEntity.Name.LETTUCE)
        CropModel.NAPPA_CABBAGE -> CropEntity(name = CropEntity.Name.NAPPA_CABBAGE)
        CropModel.TOMATO -> CropEntity(name = CropEntity.Name.TOMATO)
    }
}