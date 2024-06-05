package kr.co.wdtt.nbdream.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = DreamTypography(
    displayB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    displaySB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    headerB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    headerSB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    headerM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    header2B = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    header2SB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    header2M = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    titleSB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.4, // 140%
        letterSpacing = (-0.01).em // -0.01%
    ),
    titleM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.4, // 140%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodyB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodySB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodyM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodyR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelSB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelL = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    )
)

@Immutable
data class DreamTypography(
    val displayB: TextStyle = TextStyle.Default,
    val displaySB: TextStyle = TextStyle.Default,
    val headerB: TextStyle = TextStyle.Default,
    val headerSB: TextStyle = TextStyle.Default,
    val headerM: TextStyle = TextStyle.Default,
    val header2B: TextStyle = TextStyle.Default,
    val header2SB: TextStyle = TextStyle.Default,
    val header2M: TextStyle = TextStyle.Default,
    val titleSB: TextStyle = TextStyle.Default,
    val titleM: TextStyle = TextStyle.Default,
    val bodyB: TextStyle = TextStyle.Default,
    val bodySB: TextStyle = TextStyle.Default,
    val bodyM: TextStyle = TextStyle.Default,
    val bodyR: TextStyle = TextStyle.Default,
    val labelSB: TextStyle = TextStyle.Default,
    val labelM: TextStyle = TextStyle.Default,
    val labelR: TextStyle = TextStyle.Default,
    val labelL: TextStyle = TextStyle.Default,
)

val LocalTypography = staticCompositionLocalOf { DreamTypography() }