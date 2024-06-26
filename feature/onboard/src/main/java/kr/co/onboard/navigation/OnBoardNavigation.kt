package kr.co.onboard.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.onboard.OnBoardRoute
import kr.co.onboard.address.InputAddressScreen
import kr.co.onboard.crop.SelectCropScreen
import kr.co.onboard.welcome.WelcomeScreen
import timber.log.Timber

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
//            navigateToCrop = {
//                navController.navigate(CROP_ROUTE)
//            }
            navController = navController
        )
    }

    composable(
        route = "SelectCropScreen/{fullRoadAddress}/{bCode}/{latitude}/{longitude}",
        arguments = listOf(
            navArgument("fullRoadAddress") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("bCode") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("latitude") {
                type = NavType.FloatType
            },
            navArgument("longitude") {
                type = NavType.FloatType
            }
        )
    ) { backStackEntry ->
        val fullRoadAddress = backStackEntry.arguments?.getString("fullRoadAddress") ?: ""
        val bCode = backStackEntry.arguments?.getString("bCode") ?: ""
        val latitude = backStackEntry.arguments?.getFloat("latitude") ?: 0f
        val longitude = backStackEntry.arguments?.getFloat("longitude") ?: 0f
        Timber.d("fullRoadAddress: $fullRoadAddress, bCode: $bCode, latitude: $latitude, longitude: $longitude")

        SelectCropScreen(
            navController = navController,
            navigateToWelcome = {
                navController.navigate(WELCOME_ROUTE)
            }
        )
    }

    composable(
        route = "WelcomeScreen/{fullRoadAddress}/{bCode}/{latitude}/{longitude}/{cropsString}",
        arguments = listOf(
            navArgument("fullRoadAddress") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("bCode") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("latitude") {
                type = NavType.FloatType
                nullable = false
            },
            navArgument("longitude") {
                type = NavType.FloatType
                nullable = false
            },
            navArgument("cropsString") {
                type = NavType.StringType
                nullable = true
            })
    ) { backStackEntry ->
        val fullRoadAddress = backStackEntry.arguments?.getString("fullRoadAddress") ?: ""
        val bCode = backStackEntry.arguments?.getString("bCode") ?: ""
        val latitude = backStackEntry.arguments?.getFloat("latitude") ?: 0f
        val longitude = backStackEntry.arguments?.getFloat("longitude") ?: 0f
        val cropsString = backStackEntry.arguments?.getString("cropsString") ?: ""
        Timber.d("fullRoadAddress: $fullRoadAddress, bCode: $bCode, latitude: $latitude, longitude: $longitude, cropsString: $cropsString")

        WelcomeScreen(
            navController = navController
        )
    }
}