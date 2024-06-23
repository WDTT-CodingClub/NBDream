package kr.co.onboard.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.onboard.address.InputAddressScreen
import kr.co.onboard.OnBoardRoute
import kr.co.onboard.crop.SelectCropScreen
import kr.co.onboard.mapview.LocationSearchWebViewScreen
import kr.co.onboard.welcome.WelcomeScreen
import timber.log.Timber

const val ONBOARD_ROUTE = "onboardRoute"
internal const val MAIN_ROUTE = "mainRoute"
internal const val ADDRESS_ROUTE = "addressRoute"
internal const val ADDRESS_FIND_ROUTE = "addressFindRoute"
internal const val CROP_ROUTE = "cropRoute"
internal const val WELCOME_ROUTE = "welcomeRoute"

fun NavGraphBuilder.onboardNavGraph(
    navController: NavController
) {
    composable(
        route = ONBOARD_ROUTE
    ) {
        OnBoardRoute(navController = navController)
    }

    composable(
        route = ADDRESS_ROUTE
    ) {
        InputAddressScreen(
            modifier = Modifier,
            navController = navController
        )
    }

    composable(
        route = ADDRESS_FIND_ROUTE
    ) {
        LocationSearchWebViewScreen(
            modifier = Modifier,
            onAddressSelected = { fullRoadAddr, jibunAddr ->
                Timber.d("fullRoadAddr : $fullRoadAddr")
                Timber.d("jibunAddr : $jibunAddr")
                navController.previousBackStackEntry?.savedStateHandle?.set("fullRoadAddr", fullRoadAddr)
                navController.previousBackStackEntry?.savedStateHandle?.set("jibunAddr", jibunAddr)
                navController.popBackStack()
            }
        )
    }

    composable(
        route = CROP_ROUTE
    ) {
        SelectCropScreen(
            navController = navController
        )
    }

    composable(
        route = WELCOME_ROUTE
    ) {
        WelcomeScreen(
            navController = navController
        )
    }

    composable("ADDRESS_ROUTE") {
//        LocationSearchScreen(
//            navController = navController,
//            initialFullRoadAddr = "",
//            initialJibunAddr = "",
//            onAddressSelected = { roadAddr, jibun ->
//
//            }
//        )
        InputAddressScreen(
            modifier = Modifier,
            navController = navController
        )
    }

    composable("ADDRESS_FIND_ROUTE") {
        LocationSearchWebViewScreen(
            modifier = Modifier,
            onAddressSelected = { fullRoadAddr, jibunAddr ->
                navController.previousBackStackEntry?.savedStateHandle?.set("fullRoadAddr", fullRoadAddr)
                navController.previousBackStackEntry?.savedStateHandle?.set("jibunAddr", jibunAddr)
                navController.popBackStack()
            }
        )
    }
}