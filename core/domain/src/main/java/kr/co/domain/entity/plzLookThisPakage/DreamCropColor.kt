package kr.co.domain.entity.plzLookThisPakage

import android.graphics.Color
import androidx.annotation.ColorInt

// TODO 작물별 색상 정하기
private val pepper = Color.parseColor("#FFFFFF")
private val rice = Color.parseColor("#FFFFFF")
private val potato = Color.parseColor("#C49C6B")
private val sweetPotato = Color.parseColor("#FFFFFF")
private val apple = Color.parseColor("#FFFFFF")
private val strawberry = Color.parseColor("#FFFFFF")
private val garlic = Color.parseColor("#FFFFFF")
private val lettuce = Color.parseColor("#FFFFFF")
private val nappaCabbage = Color.parseColor("#FFFFFF")
private val tomato = Color.parseColor("#FFFFFF")

// TODO 다크모드 색상 정하기
data object CropColorSet {
    private val lightColors = DreamCropColor(
        pepper = pepper,
        rice = rice,
        potato = potato,
        sweetPotato = sweetPotato,
        apple = apple,
        strawberry = strawberry,
        garlic = garlic,
        lettuce = lettuce,
        nappaCabbage = nappaCabbage,
        tomato = tomato
    )
    private val darkColors = DreamCropColor()

    val localColors get()= lightColors
}

data class DreamCropColor(
    @ColorInt val pepper: Int = Color.WHITE,
    @ColorInt val rice: Int = Color.WHITE,
    @ColorInt val potato: Int = Color.WHITE,
    @ColorInt val sweetPotato: Int = Color.WHITE,
    @ColorInt val apple: Int = Color.WHITE,
    @ColorInt val strawberry: Int = Color.WHITE,
    @ColorInt val garlic: Int = Color.WHITE,
    @ColorInt val lettuce: Int = Color.WHITE,
    @ColorInt val nappaCabbage: Int = Color.WHITE,
    @ColorInt val tomato: Int = Color.WHITE
)
