package kr.co.main.calendar.model.type

import androidx.annotation.StringRes
import kr.co.main.R

internal enum class CropModelType(
    @StringRes val nameId: Int
) {
    PEPPER(R.string.feature_main_crop_name_pepper),
    RICE(R.string.feature_main_crop_name_rice),
    POTATO(R.string.feature_main_crop_name_potato),
    SWEET_POTATO(R.string.feature_main_crop_name_sweet_potato),
    APPLE(R.string.feature_main_crop_name_apple),
    STRAWBERRY(R.string.feature_main_crop_name_strawberry),
    GARLIC(R.string.feature_main_crop_name_garlic),
    LETTUCE(R.string.feature_main_crop_name_lettuce),
    NAPPA_CABBAGE(R.string.feature_main_crop_name_nappa_cabbage),
    TOMATO(R.string.feature_main_crop_name_tomato);

    companion object {
        fun ofValue(@StringRes nameId: Int) = when (nameId) {
            PEPPER.nameId -> PEPPER
            RICE.nameId -> RICE
            POTATO.nameId -> POTATO
            SWEET_POTATO.nameId -> SWEET_POTATO
            APPLE.nameId -> APPLE
            STRAWBERRY.nameId -> STRAWBERRY
            GARLIC.nameId -> GARLIC
            LETTUCE.nameId -> LETTUCE
            NAPPA_CABBAGE.nameId -> NAPPA_CABBAGE
            TOMATO.nameId -> TOMATO
            else -> throw IllegalArgumentException("Unknown nameId")
        }
    }
}
