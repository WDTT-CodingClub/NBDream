package kr.co.main.calendar.model

import kr.co.main.calendar.model.type.CropModelColorType
import kr.co.main.calendar.model.type.CropModelType

internal data class CropModel(
    val type: CropModelType,
    val color: CropModelColorType
) {
    companion object {
        fun create(type: CropModelType) = when (type) {
            CropModelType.PEPPER -> CropModel(
                type = CropModelType.PEPPER,
                color = CropModelColorType.PEPPER
            )

            CropModelType.RICE -> CropModel(
                type = CropModelType.RICE,
                color = CropModelColorType.RICE
            )

            CropModelType.POTATO -> CropModel(
                type = CropModelType.POTATO,
                color = CropModelColorType.POTATO
            )

            CropModelType.SWEET_POTATO -> CropModel(
                type = CropModelType.SWEET_POTATO,
                color = CropModelColorType.SWEET_POTATO
            )

            CropModelType.APPLE -> CropModel(
                type = CropModelType.APPLE,
                color = CropModelColorType.APPLE
            )

            CropModelType.STRAWBERRY -> CropModel(
                type = CropModelType.STRAWBERRY,
                color = CropModelColorType.STRAWBERRY
            )

            CropModelType.GARLIC -> CropModel(
                type = CropModelType.GARLIC,
                color = CropModelColorType.GARLIC
            )

            CropModelType.LETTUCE -> CropModel(
                type = CropModelType.LETTUCE,
                color = CropModelColorType.LETTUCE
            )

            CropModelType.NAPPA_CABBAGE -> CropModel(
                type = CropModelType.NAPPA_CABBAGE,
                color = CropModelColorType.NAPPA_CABBAGE
            )

            CropModelType.TOMATO -> CropModel(
                type = CropModelType.TOMATO,
                color = CropModelColorType.TOMATO
            )
        }
    }
}