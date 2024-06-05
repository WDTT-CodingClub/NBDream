package kr.co.domain.entity.plzLookThisPakage

// TODO 작물 콘텐츠 번호 하드 코딩
enum class DreamCrop(
    val cropCode: String,
//    @StringRes val cropNameId: Int,
//    @ColorInt val cropColor: Int,
    val ranking: Int, // 인기순위, 작물 정렬에 사용
    cropNameId: Int
) {
    PEPPER(
        cropCode = "",
//        cropNameId = R.string.dream_crop_name_pepper,
//        cropColor = CropColorSet.localColors.pepper,
        ranking = 1,
        kr.co.main.R.string.feature_main_dream_crop_name_pepper
    ),
    RICE(
        cropCode = "",
//        cropNameId = R.string.dream_crop_name_rice,
//        cropColor = CropColorSet.localColors.rice,
        ranking = 2,
        kr.co.main.R.string.feature_main_dream_crop_name_pepper
    ),
//    POTATO(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_potato,
//        cropColor = CropColorSet.localColors.potato,
//        ranking = 3
//    ),
//    SWEET_POTATO(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_sweet_potato,
//        cropColor = CropColorSet.localColors.sweetPotato,
//        ranking = 4
//    ),
//    APPLE(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_apple,
//        cropColor = CropColorSet.localColors.apple,
//        ranking = 5
//    ),
//    STRAW_BERRY(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_strawberry,
//        cropColor = CropColorSet.localColors.strawberry,
//        ranking = 6
//    ),
//    GARLIC(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_garlic,
//        cropColor = CropColorSet.localColors.garlic,
//        ranking = 7
//    ),
//    LETTUCE(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_lettuce,
//        cropColor = CropColorSet.localColors.lettuce,
//        ranking = 8
//    ),
//    NAPPA_CABBAGE(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_nappa_cabbage,
//        cropColor = CropColorSet.localColors.nappaCabbage,
//        ranking = 9
//    ),
//    TOMATO(
//        cropCode = "",
//        cropNameId = R.string.dream_crop_name_tomato,
//        cropColor = CropColorSet.localColors.tomato,
//        ranking = 10
//    )
;

    companion object {
        fun getCropByCode(cropCode: String): DreamCrop =
            entries.first { it.cropCode == cropCode }
    }
}