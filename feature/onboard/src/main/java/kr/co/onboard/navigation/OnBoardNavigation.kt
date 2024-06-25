package kr.co.onboard.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.onboard.OnBoardRoute
import kr.co.onboard.address.InputAddressScreen
import kr.co.onboard.crop.SelectCropScreen
import kr.co.onboard.welcome.WelcomeScreen

const val ONBOARD_ROUTE = "onboardRoute"
internal const val ADDRESS_ROUTE = "addressRoute"
internal const val CROP_ROUTE = "cropRoute"
internal const val WELCOME_ROUTE = "welcomeRoute"

fun NavGraphBuilder.onboardNavGraph(
    navController: NavController
) {
    composable(
        route = ONBOARD_ROUTE
    ) {
        OnBoardRoute(
            navigateToAddress = {
                navController.navigate(ADDRESS_ROUTE)
            }
        )
    }

    composable(
        route = ADDRESS_ROUTE
    ) {
        InputAddressScreen(
            modifier = Modifier,
            navigateToCrop = {
                navController.navigate(CROP_ROUTE)
            }
        )
    }

    composable(
        route = CROP_ROUTE
    ) {
        SelectCropScreen()
    }

    composable(
        route = WELCOME_ROUTE
    ) {
        WelcomeScreen()
    }
}