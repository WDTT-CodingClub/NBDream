package kr.co.wdtt.nbdream.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.ui.theme.NBDreamTheme
import kr.co.wdtt.nbdream.R

@Composable
internal fun Splash(
    splashVisible: Boolean
) {
    val splashFontFamily = FontFamily(
        Font(kr.co.nbdream.core.ui.R.font.nanum_myeongjo_bold, weight = FontWeight.Bold),
        Font(kr.co.nbdream.core.ui.R.font.nanum_myeongjo, weight = FontWeight.Normal)
    )

    val scope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition("")

    val backgroundOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1000f,  // Height of the screen
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 17000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),""
    )

    val backgroundOffset2 by infiniteTransition.animateFloat(
        initialValue = 1000f,  // Height of the screen
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 17000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),""
    )

    val (wink, setWink) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            delay(1400)
            setWink(true)
            delay(600)
            setWink(false)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFE8E0))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            List(2) {
                Box {
                    Image(
                        modifier = Modifier.offset(
                            x = 0.dp,
                            y = backgroundOffset1.dp
                        ),
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.backgrounddeco),
                        contentDescription = "배경 이미지"
                    )
                    Image(
                        modifier = Modifier.offset(
                            x = 0.dp,
                            y = backgroundOffset2.dp
                        ),
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.backgrounddeco),
                        contentDescription = "배경 이미지"
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.logo_aura),
                    contentDescription = null
                )
                Column {
                    AnimatedVisibility(
                        visible = splashVisible,
                        enter = slideIn(
                            animationSpec = spring(
                                stiffness = 1000f,
                                visibilityThreshold = IntOffset.VisibilityThreshold
                            ),
                            initialOffset = { IntOffset(0, 800) }
                        ) + fadeIn(animationSpec = tween(1000)),
                        exit = fadeOut(animationSpec = tween(1000))
                    ) {
                        Image(
                            painter = if (wink.not()) painterResource(id = kr.co.nbdream.core.ui.R.drawable.logo) else painterResource(
                                id = kr.co.nbdream.core.ui.R.drawable.logo_wink
                            ),
                            contentDescription = "앱 마스코트"
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = splashVisible,
                enter = slideIn(
                    animationSpec = spring(
                        stiffness = 1000f,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    initialOffset = { IntOffset(0, 400) }
                ) + fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = splashFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color(0xFF413E33)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "초보 농부를 위한 농업 파트너",
                        fontFamily = splashFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF413E33).copy(0.8f)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        Splash(splashVisible = true)
    }
}