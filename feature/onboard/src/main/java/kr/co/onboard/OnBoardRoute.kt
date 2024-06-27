package kr.co.onboard

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.domain.entity.type.AuthType
import kr.co.onboard.login.Login
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.widget.DreamSocialButton

@Composable
internal fun OnBoardRoute(
    viewModel: OnBoardViewModel = hiltViewModel(),
    navigateToAddress: () -> Unit = {},
) {


    val scope = rememberCoroutineScope()

    val (wink, setWink) = remember { mutableStateOf(false) }

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
        ), ""
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
        ), ""
    )

    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                delay(1_400)
                setWink(true)
                delay(600)
                setWink(false)
                delay(3_000)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.showAddressScreen.collect {
            navigateToAddress()
        }
    }

    OnBoardScreen(
        onSocialLoginClick = viewModel::onSocialLoginClick,
        wink = wink,
        backgroundOffset1 = backgroundOffset1,
        backgroundOffset2 = backgroundOffset2
    )
}

@Composable
private fun OnBoardScreen(
    onSocialLoginClick: (AuthType) -> Unit = {},
    wink: Boolean = false,
    backgroundOffset1: Float = 0f,
    backgroundOffset2: Float = 0f,
    splashFontFamily: FontFamily = FontFamily(
        Font(kr.co.nbdream.core.ui.R.font.nanum_myeongjo_bold, weight = FontWeight.Bold),
        Font(kr.co.nbdream.core.ui.R.font.nanum_myeongjo, weight = FontWeight.Normal)
    ),
) {
    Scaffold(
        topBar = {

        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFE8E0))
                .scaffoldBackground(scaffoldPadding)
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

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.weight(2f))

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.logo_aura),
                        contentDescription = null
                    )
                    Image(
                        painter = if (wink.not()) painterResource(id = kr.co.nbdream.core.ui.R.drawable.logo) else painterResource(
                            id = kr.co.nbdream.core.ui.R.drawable.logo_wink
                        ),
                        contentDescription = "앱 마스코트"
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = com.kakao.vectormap.R.string.app_name),
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

                Spacer(modifier = Modifier.weight(1f))


                AnimatedVisibility(
                    visible = true,
                    enter = slideIn(
                        animationSpec = spring(
                            stiffness = 1000f,
                            visibilityThreshold = IntOffset.VisibilityThreshold
                        ),
                        initialOffset = { IntOffset(0, 800) }
                    ) + fadeIn(animationSpec = tween(1000)),
                ) {
                    Column(
                        modifier = Modifier.navigationBarsPadding(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AuthType.entries.forEach {
                            DreamSocialButton(type = it.ordinal) {
                                onSocialLoginClick(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
