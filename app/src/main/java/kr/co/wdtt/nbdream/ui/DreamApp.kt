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
import kr.co.wdtt.nbdream.ui.main.community.CommunityScreen
import kr.co.wdtt.nbdream.ui.main.navigation.MAIN_ROUTE
import kr.co.wdtt.nbdream.ui.main.navigation.mainNavGraph
import kr.co.wdtt.nbdream.ui.onboarding.navigation.ONBOARD_ROUTE

private enum class DreamNavRoute(
    val route: String,
) {
    SPLASH(SPLASH_ROUTE),
    MAIN(MAIN_ROUTE),
    ONBOARDING(ONBOARD_ROUTE),
}

const val SPLASH_ROUTE = "splash"
@Composable
fun DreamApp(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    var navRoute by remember { mutableStateOf(DreamNavRoute.SPLASH) }

    LaunchedEffect(Unit) {
        //TODO 스플래시후, 로컬 세션(데이터스토어)에서 저장된 사용자 체크, navRoute설정 ONBOARDING or MAIN
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

        OnBoardNavGraph(navController)
    }
}
