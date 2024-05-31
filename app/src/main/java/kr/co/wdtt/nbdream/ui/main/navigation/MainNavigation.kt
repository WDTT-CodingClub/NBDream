package kr.co.wdtt.nbdream.ui.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.wdtt.nbdream.ui.main.MainBottomRoute
import kr.co.wdtt.nbdream.ui.main.MainRoute

const val MAIN_ROUTE = "mainRoute"
internal const val NOTIFICATION_ROUTE = "notificationRoute"

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            mainBuilder = {
                composable(
                    route = MainBottomRoute.HOME.route
                ) {

                }

                composable(
                    route = MainBottomRoute.CALENDAR.route
                ) {

                }

                composable(
                    route = MainBottomRoute.ACCOUNT.route
                ) {

                }

                composable(
                    route = MainBottomRoute.COMMUNITY.route
                ) {

                }

                composable(
                    route = MainBottomRoute.MY_PAGE.route
                ) {

                }
            }
        )
    }

    composable(
        route = NOTIFICATION_ROUTE
    ) {

    }
}