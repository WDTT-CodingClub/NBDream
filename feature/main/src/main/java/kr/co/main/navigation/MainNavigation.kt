package kr.co.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.main.MainBottomRoute
import kr.co.main.MainRoute
import kr.co.main.accountbook.main.AccountBookRoute
import kr.co.main.accountbook.register.AccountBookRegister
import kr.co.main.home.HomeRoute

const val MAIN_ROUTE = "mainRoute"
internal const val CHAT_ROUTE = "chatRoute"
internal const val NOTIFICATION_ROUTE = "notificationRoute"
internal const val ACCOUNT_BOOK_ROUTE = "accountBookRoute"

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            mainBuilder = {
                composable(
                    route = MainBottomRoute.HOME.route
                ) {
                    HomeRoute()
                }

                composable(
                    route = MainBottomRoute.CALENDAR.route
                ) {

                }

                composable(
                    route = MainBottomRoute.ACCOUNT.route
                ) {
                    AccountBookRoute(navigationToRegister = { navController.navigate(ACCOUNT_BOOK_ROUTE) })
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
        route = CHAT_ROUTE
    ) {

    }

    composable(
        route = NOTIFICATION_ROUTE
    ) {

    }

    composable(
        route = ACCOUNT_BOOK_ROUTE
    ) {
        AccountBookRegister()
    }
}