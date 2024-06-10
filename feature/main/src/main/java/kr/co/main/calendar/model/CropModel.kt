package kr.co.main.calendar.model

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import kr.co.domain.entity.CropEntity
import kr.co.main.R

enum class CropModel(
    @StringRes val nameId: Int,
    @ColorInt val color: Int = 0,
    val ranking: Int // 인기순위, 작물 정렬에 사용
) {
    PEPPER(
        nameId = R.string.feature_main_crop_name_pepper,
        color = CropColorModel.localColors.pepper,
        ranking = 1
    ),
    RICE(
        nameId = R.string.feature_main_crop_name_rice,
        color = CropColorModel.localColors.rice,
        ranking = 2
    ),
    POTATO(
        nameId = R.string.feature_main_crop_name_potato,
        color = CropColorModel.localColors.potato,
        ranking = 3
    ),
    SWEET_POTATO(
        nameId = R.string.feature_main_crop_name_sweet_potato,
        color = CropColorModel.localColors.sweetPotato,
        ranking = 4
    ),
    APPLE(
        nameId = R.string.feature_main_crop_name_apple,
        color = CropColorModel.localColors.apple,
        ranking = 5
    ),
    STRAWBERRY(
        nameId = R.string.feature_main_crop_name_strawberry,
        color = CropColorModel.localColors.strawberry,
        ranking = 6
    ),
    GARLIC(
        nameId = R.string.feature_main_crop_name_garlic,
        color = CropColorModel.localColors.garlic,
        ranking = 7
    ),
    LETTUCE(
        nameId = R.string.feature_main_crop_name_lettuce,
        color = CropColorModel.localColors.lettuce,
        ranking = 8
    ),
    NAPPA_CABBAGE(
        nameId = R.string.feature_main_crop_name_nappa_cabbage,
        color = CropColorModel.localColors.nappaCabbage,
        ranking = 9
    ),
    TOMATO(
        nameId = R.string.feature_main_crop_name_tomato,
        color = CropColorModel.localColors.tomato,
        ranking = 10
    )
}

internal fun CropEntity.convert() = when(name){
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

internal fun CropModel.convert() = when(this){
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