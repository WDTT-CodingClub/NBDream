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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kr.co.main.navigation.MAIN_ROUTE
import kr.co.main.navigation.mainNavGraph
import kr.co.onboard.navigation.ONBOARD_ROUTE
import kr.co.onboard.navigation.onboardNavGraph
import kr.co.wdtt.nbdream.MainViewModel
import kr.co.wdtt.nbdream.R

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
        viewModel.isAuthorized.filterNotNull().collectLatest {
            setSplashVisible(false)
            navRoute = if (it) DreamNavRoute.MAIN else DreamNavRoute.ONBOARDING
            //navRoute = DreamNavRoute.MAIN
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
            Splash(splashVisible)
        }

        mainNavGraph(navController)

        onboardNavGraph(navController)
    }
}
