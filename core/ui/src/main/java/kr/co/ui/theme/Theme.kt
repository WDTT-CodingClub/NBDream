package kr.co.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalColors = staticCompositionLocalOf { ColorSet.Dream.lightColors }

@Composable
fun NBDreamTheme(
    colorSet: ColorSet = ColorSet.Dream,
    darkTheme: Boolean = isSystemInDarkTheme(),
    shapes: Shapes = Shapes,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        colorSet.lightColors
    } else {
        colorSet.lightColors
    }

    CompositionLocalProvider(LocalColors provides colors) {
        CompositionLocalProvider(LocalTypography provides Typography) {
            MaterialTheme(
                content = content,
                shapes = shapes,
            )
        }
    }
}

val MaterialTheme.colors: DreamColor
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

val MaterialTheme.typo: DreamTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current