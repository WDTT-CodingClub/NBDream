package kr.co.wdtt.nbdream.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.wdtt.nbdream.ui.onboarding.OnBoardRoute

const val ONBOARD_ROUTE = "onboardRoute"
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
        OnBoardRoute(onKakaoClick = { /*TODO*/ }, onNaverClick = { /*TODO*/ }) {

        }
    }

    composable(
        route = ADDRESS_ROUTE
    ) {

    }

    composable(
        route = ADDRESS_FIND_ROUTE
    ) {

    }

    composable(
        route = CROP_ROUTE
    ) {

    }

    composable(
        route = WELCOME_ROUTE
    ) {

    }
}