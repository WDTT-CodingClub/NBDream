package kr.co.wdtt.nbdream.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kr.co.main.navigation.MAIN_ROUTE
import kr.co.main.navigation.mainNavGraph
import kr.co.onboard.login.AddressSelectionListener
import kr.co.onboard.login.LocationSearchScreen
import kr.co.onboard.login.LocationSearchWebViewScreen
import kr.co.onboard.navigation.ONBOARD_ROUTE
import kr.co.onboard.navigation.onboardNavGraph
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
    navController: NavHostController = rememberNavController()
) {
    var navRoute by remember { mutableStateOf(DreamNavRoute.SPLASH) }

    LaunchedEffect(Unit) {
        viewModel.isAuthorized.collectLatest {
            navRoute = if (it) DreamNavRoute.MAIN else DreamNavRoute.ONBOARDING
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
            //splash screen
        }

        mainNavGraph(navController)

        onboardNavGraph(navController)
    }
}
