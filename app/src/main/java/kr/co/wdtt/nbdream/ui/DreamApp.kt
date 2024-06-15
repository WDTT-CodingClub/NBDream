package kr.co.wdtt.nbdream.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    navController: NavHostController = rememberNavController()
) {
    var navRoute by remember { mutableStateOf(DreamNavRoute.SPLASH) }

    LaunchedEffect(Unit) {
        viewModel.isAuthorized.drop(1).collectLatest {
            navRoute = if (it) DreamNavRoute.ONBOARDING else DreamNavRoute.ONBOARDING
        }
    }

    DreamAppScreen(
        startDestination = navRoute,
        navController = navController,
    )
}

@Composable
private fun DreamAppScreen(
    startDestination: DreamNavRoute = DreamNavRoute.SPLASH,
    navController: NavHostController
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(
                    id = kr.co.nbdream.core.ui.R.drawable.img_logo),
                    contentDescription = "logo"
                )
            }
        }

        mainNavGraph(navController)

        onboardNavGraph(navController)
    }
}
