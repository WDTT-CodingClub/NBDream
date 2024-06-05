package kr.co.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val primary = Color(0xFF5ABE20)
private val secondary = Color(0xFF5C4740)

private val green1 = Color(0xFFCEE997)
private val green2 = Color(0xFF00B451)
private val green3 = Color(0xFF00AC5B)
private val green4 = Color(0xFF00A679)
private val green5 = Color(0xFF00957A)
private val green6 = Color(0xFF007C83)

private val grey1 = Color(0xFFF1F1F1)
private val grey2 = Color(0xFFE7E7E7)
private val grey3 = Color(0xFFE9E9E9)
private val grey4 = Color(0xFFD8D8D8)
private val grey5 = Color(0xFFC8C8C8)
private val grey6 = Color(0xFFA9A9A9)

private val text1 = Color(0xFF292929)
private val text2 = Color(0xFF5A5A5A)

private val black = Color(0xFF000000)
private val white = Color(0xFFFFFFFF)

private val red1 = Color(0xFFF45262)

private val blue1 = Color(0xFFABC5F8)

private val orange1 = Color(0xFFDE9E63)

val kakaoYellow = Color(0xFFFEE500)
val naverGreen = Color(0xFF03C75A)

sealed class ColorSet {
    open lateinit var lightColors: DreamColor
    open lateinit var darkColors: DreamColor

    data object Dream : ColorSet() {
        override var lightColors = DreamColor(
            material = lightColorScheme(
                primary = primary,
                onPrimary = white,
                primaryContainer = secondary,
                onPrimaryContainer = white,
            ),
            primary = primary,
            secondary = secondary,
            green1 = green1,
            green2 = green2,
            green3 = green3,
            green4 = green4,
            green5 = green5,
            green6 = green6,
            grey1 = grey1,
            grey2 = grey2,
            grey3 = grey3,
            grey4 = grey4,
            grey5 = grey5,
            grey6 = grey6,
            text1 = text1,
            text2 = text2,
            black = black,
            white = white,
            red1 = red1,
            blue1 = blue1,
            orange1 = orange1,
        )
        override var darkColors = DreamColor(
            material = darkColorScheme(),

        )
    }
}

data class DreamColor(
    val material: ColorScheme,
    val primary: Color = material.primary,
    val secondary: Color = material.secondary,
    val green1: Color = Color.Green,
    val green2: Color = Color.Green,
    val green3: Color = Color.Green,
    val green4: Color = Color.Green,
    val green5: Color = Color.Green,
    val green6: Color = Color.Green,
    val grey1: Color = Color.Gray,
    val grey2: Color = Color.Gray,
    val grey3: Color = Color.Gray,
    val grey4: Color = Color.Gray,
    val grey5: Color = Color.Gray,
    val grey6: Color = Color.Gray,
    val text1: Color = Color.Black,
    val text2: Color = Color.White,
    val black: Color = Color.Black,
    val white: Color = Color.White,
    val red1: Color = Color.Red,
    val blue1: Color = Color.Blue,
    val orange1: Color = Color.Yellow,
)