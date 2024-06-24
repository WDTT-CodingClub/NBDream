package kr.co.onboard.crop.model

import androidx.annotation.DrawableRes
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType

data class CropItem(
    val name: CropType,
    @DrawableRes val imageRes: Int
)

fun CropType.toKoreanName(): String =
    when (this) {
        CropType.PEPPER -> "고추"
        CropType.RICE -> "벼"
        CropType.POTATO -> "감자"
        CropType.SWEET_POTATO -> "고구마"
        CropType.APPLE -> "사과"
        CropType.STRAWBERRY -> "딸기"
        CropType.GARLIC -> "마늘"
        CropType.LETTUCE -> "상추"
        CropType.NAPPA_CABBAGE -> "배추"
        CropType.TOMATO -> "토마토"
    }

