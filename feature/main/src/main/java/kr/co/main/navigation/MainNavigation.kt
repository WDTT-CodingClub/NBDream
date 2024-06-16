package kr.co.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.main.MainBottomRoute
import kr.co.main.MainRoute
import kr.co.main.accountbook.main.AccountBookRoute
import kr.co.main.accountbook.register.AccountBookRegister
import kr.co.main.home.HomeRoute
import kr.co.main.home.chat.ChatRoute
import kr.co.main.my.MyPageRoute
import kr.co.main.my.profile.MyPageProfileEditRoute

const val MAIN_ROUTE = "mainRoute"
internal const val CHAT_ROUTE = "chatRoute"
internal const val NOTIFICATION_ROUTE = "notificationRoute"
internal const val ACCOUNT_BOOK_ROUTE = "accountBookRoute"

internal const val MyPageProfileEditRoute = "myPageProfileEditRoute"

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            mainBuilder = {
                composable(
                    route = MainBottomRoute.HOME.route
                ) {
                    HomeRoute(
                        navigateToChat = {
                            navController.navigate(CHAT_ROUTE)
                        },
                        navigateToNotification = {
                            navController.navigate(NOTIFICATION_ROUTE)
                        }
                    )
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
                    MyPageRoute(
                        navigateToProfileEdit = {
                            navController.navigate(MyPageProfileEditRoute)
                        }
                    )
                }
            }
        )
    }

    composable(
        route = CHAT_ROUTE
    ) {
        ChatRoute(
            popBackStack = navController::popBackStack
        )
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

    composable(
        route = MyPageProfileEditRoute
    ) {
        MyPageProfileEditRoute(
            popBackStack = navController::popBackStack
        )
    }
}