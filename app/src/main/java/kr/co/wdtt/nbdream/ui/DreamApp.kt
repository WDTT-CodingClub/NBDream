package kr.co.wdtt.nbdream.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.VisibilityThreshold
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kr.co.main.navigation.MAIN_ROUTE
import kr.co.main.navigation.mainNavGraph
import kr.co.onboard.navigation.ONBOARD_ROUTE
import kr.co.onboard.navigation.onboardNavGraph
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.wdtt.nbdream.MainViewModel

private enum class DreamNavRoute(
    val route: String,
) {
    SPLASH(SPLASH_ROUTE),
    MAIN(MAIN_ROUTE),
    ONBOARDING(ONBOARD_ROUTE),
}

private const val SPLASH_ROUTE = "splash"

@Composable
internal fun DreamApp(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    var navRoute by remember { mutableStateOf(DreamNavRoute.SPLASH) }
    var (splashVisible, setSplashVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        setSplashVisible(true)
        viewModel.isAuthorized.drop(1).collectLatest {
            setSplashVisible(false)
            navRoute = if (it) DreamNavRoute.MAIN else DreamNavRoute.ONBOARDING
        }
    }

    DreamAppScreen(
        startDestination = navRoute,
        navController = navController,
        splashVisible = splashVisible,
    )
}

@Composable
private fun DreamAppScreen(
    navController: NavHostController,
    splashVisible: Boolean,
    startDestination: DreamNavRoute = DreamNavRoute.SPLASH,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(DreamNavRoute.SPLASH.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    Text(
                        text = "농부의 꿈",
                        style = MaterialTheme.typo.h1.copy(
                            fontSize = 72.sp
                        ),
                        color = MaterialTheme.colors.white
                    )
                }

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
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_splash_empty),
                        contentDescription = "logo"
                    )
                }
            }
        }

        mainNavGraph(navController)

        onboardNavGraph(navController)
    }
}
