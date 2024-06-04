package kr.co.domain.entity

import androidx.annotation.StringRes

// TODO 작물 콘텐츠 번호 하드 코딩
enum class DreamCrop(
    val cropCode: String,
    @StringRes val cropName: Int,
    val ranking: Int
) {
//    PEPPER("", R.string.dream_crop_name_pepper, 1),
//    RICE("", R.string.dream_crop_name_rice, 2),
//    POTATO("", R.string.dream_crop_name_potato, 3),
//    SWEET_POTATO("", R.string.dream_crop_name_sweet_potato, 4),
//    APPLE("", R.string.dream_crop_name_apple, 5),
//    STRAWBERRY("", R.string.dream_crop_name_strawberry, 6),
//    GARLIC("", R.string.dream_crop_name_garlic, 7),
//    LETTUCE("", R.string.dream_crop_name_lettuce, 8),
//    NAPPA_CABBAGE("", R.string.dream_crop_name_nappa_cabbage, 9),
//    TOMATO("", R.string.dream_crop_name_tomato, 10);
;
    companion object {
        fun getCropByCode(cropCode: String): kr.co.domain.entity.DreamCrop =
            entries.first { it.cropCode == cropCode }
    }
}