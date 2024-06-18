package kr.co.onboard.crop.model

import androidx.annotation.DrawableRes
import kr.co.domain.entity.CropEntity

data class CropItem(
    val name: CropEntity.Name,
    @DrawableRes val imageRes: Int
)

fun CropEntity.Name.toKoreanName(): String {
    return when (this) {
        CropEntity.Name.PEPPER -> "고추"
        CropEntity.Name.RICE -> "벼"
        CropEntity.Name.POTATO -> "감자"
        CropEntity.Name.SWEET_POTATO -> "고구마"
        CropEntity.Name.APPLE -> "사과"
        CropEntity.Name.STRAWBERRY -> "딸기"
        CropEntity.Name.GARLIC -> "마늘"
        CropEntity.Name.LETTUCE -> "상추"
        CropEntity.Name.NAPPA_CABBAGE -> "배추"
        CropEntity.Name.TOMATO -> "토마토"
    }
}
