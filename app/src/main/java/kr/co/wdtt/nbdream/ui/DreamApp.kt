package kr.co.wdtt.nbdream.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.co.wdtt.nbdream.ui.main.navigation.MAIN_ROUTE
import kr.co.wdtt.nbdream.ui.main.navigation.mainNavGraph
import kr.co.onboard.login.AddressSelectionListener
import kr.co.onboard.login.LocationSearchScreen
import kr.co.onboard.login.LocationSearchWebViewScreen
import kr.co.wdtt.nbdream.ui.onboarding.navigation.ONBOARD_ROUTE
import kr.co.wdtt.nbdream.ui.onboarding.navigation.onboardNavGraph

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
//    var navRoute by remember { mutableStateOf(DreamNavRoute.SPLASH) }
//
//    LaunchedEffect(Unit) {
//        viewModel.isAuthorized.collectLatest {
//            navRoute = if (it) DreamNavRoute.MAIN else DreamNavRoute.ONBOARDING
//        }
//    }
//
//    DreamAppScreen(
//        startDestination = navRoute,
//        navController = navController,
//    )

    val navController = rememberNavController()
    var fullRoadAddr by remember { mutableStateOf("") }
    var jibunAddr by remember { mutableStateOf("") }

    NavHost(navController, startDestination = "location_search") {
        composable("location_search") {
            LocationSearchScreen(
                navController = navController,
                initialFullRoadAddr = fullRoadAddr,
                initialJibunAddr = jibunAddr,
                onAddressSelected = { roadAddr, jibun ->
                    fullRoadAddr = roadAddr
                    jibunAddr = jibun
                }
            )
        }
        composable("webview") {
            LocationSearchWebViewScreen(navController = navController, addressSelectionListener = object : AddressSelectionListener {
                override fun onAddressSelected(fullRoadAddr: String, jibunAddr: String) {
                    navController.previousBackStackEntry?.savedStateHandle?.set("fullRoadAddr", fullRoadAddr)
                    navController.previousBackStackEntry?.savedStateHandle?.set("jibunAddr", jibunAddr)
                    navController.popBackStack()
                }
            })
        }
    }

//    SelectCropScreen()
//    LocationSearchScreen()
//    Login()
//    HomeRoute()

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
