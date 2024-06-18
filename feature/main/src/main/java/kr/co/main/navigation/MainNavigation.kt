package kr.co.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.main.MainBottomRoute
import kr.co.main.MainRoute
import kr.co.main.accountbook.main.AccountBookRoute
import kr.co.main.accountbook.register.AccountBookRegister
import kr.co.main.calendar.ui.calendar_route.CalendarRoute
import kr.co.main.home.HomeRoute
import kr.co.main.home.chat.ChatRoute
import kr.co.main.my.MyPageRoute
import kr.co.main.my.profile.MyPageProfileEditRoute
import kr.co.main.my.setting.MyPageSettingRoute
import kr.co.main.my.setting.delete.MyPageSettingDeleteAccountRoute

const val MAIN_ROUTE = "mainRoute"
internal const val CHAT_ROUTE = "chatRoute"
internal const val NOTIFICATION_ROUTE = "notificationRoute"
internal const val ACCOUNT_BOOK_ROUTE = "accountBookRoute"

internal const val MY_PAGE_EDIT_ROUTE = "myPageProfileEditRoute"
internal const val MY_PAGE_SETTING_ROUTE = "myPageSettingRoute"
internal const val MY_PAGE_SETTING_NOTIFICATION_ROUTE = "myPageSettingNotificationRoute"
internal const val MY_PAGE_SETTING_PRIVACY_POLICY_ROUTE = "myPageSettingPrivacyPolicyRoute"
internal const val MY_PAGE_SETTING_LOGOUT_ROUTE = "myPageSettingLogoutRoute"
internal const val MY_PAGE_SETTING_APP_INFO_ROUTE = "myPageSettingAppInfoRoute"
internal const val MY_PAGE_SETTING_DELETE_ACCOUNT_ROUTE = "myPageSettingDeleteAccountRoute"

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
                    CalendarRoute(
                        navToNotification = { navController.navigate(NOTIFICATION_ROUTE) }
                    )
                }

                composable(
                    route = MainBottomRoute.ACCOUNT.route
                ) {
                    AccountBookRoute(navigationToRegister = {
                        navController.navigate(
                            ACCOUNT_BOOK_ROUTE
                        )
                    })
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
                            navController.navigate(MY_PAGE_EDIT_ROUTE)
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
        route = MY_PAGE_EDIT_ROUTE
    ) {
        MyPageProfileEditRoute(
            popBackStack = navController::popBackStack
        )
    }

    composable(
        route = MY_PAGE_SETTING_ROUTE
    ) {
        MyPageSettingRoute(
            popBackStack = navController::popBackStack,
            navigateTo = { route ->
                navController.navigate(route)
            }
        )
    }

    composable(
        route = MY_PAGE_SETTING_NOTIFICATION_ROUTE
    ) {

    }

    composable(
        route = MY_PAGE_SETTING_PRIVACY_POLICY_ROUTE
    ) {

    }

    composable(
        route = MY_PAGE_SETTING_LOGOUT_ROUTE
    ) {

    }

    composable(
        route = MY_PAGE_SETTING_APP_INFO_ROUTE
    ) {

    }

    composable(
        route = MY_PAGE_SETTING_DELETE_ACCOUNT_ROUTE
    ) {
        MyPageSettingDeleteAccountRoute(
            popBackStack = navController::popBackStack
        )
    }
}