package kr.co.ui

import androidx.annotation.ColorInt

// TODO 작물 콘텐츠 번호 하드 코딩
enum class DreamCrop(
    val cropCode: String,
    val cropNameId: String,
    @ColorInt val cropColor: Int = 0,
    val ranking: Int // 인기순위, 작물 정렬에 사용
) {
    PEPPER(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_pepper",
        ranking = 1
    ),
    RICE(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_rice",
        ranking = 2
    ),
    POTATO(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_potato",
        ranking = 3
    ),
    SWEET_POTATO(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_sweet_potato",
        ranking = 4
    ),
    APPLE(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_apple",
        ranking = 5
    ),
    STRAW_BERRY(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_strawberry",
        ranking = 6
    ),
    GARLIC(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_garlic",
        ranking = 7
    ),
    LETTUCE(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_lettuce",
        ranking = 8
    ),
    NAPPA_CABBAGE(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_nappa_cabbage",
        ranking = 9
    ),
    TOMATO(
        cropCode = "",
        cropNameId = "R.string.dream_crop_name_tomato",
        ranking = 10
    );

    companion object {
        fun getCropByCode(cropCode: String): DreamCrop =
            entries.first { it.cropCode == cropCode }
    }
}
