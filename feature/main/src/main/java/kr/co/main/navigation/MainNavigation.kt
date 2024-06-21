package kr.co.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.main.MainBottomRoute
import kr.co.main.MainRoute
import kr.co.main.accountbook.main.AccountBookRoute
import kr.co.main.accountbook.register.AccountBookRegister
import kr.co.main.calendar.ui.calendar_screen.add_diary_screen.AddDiaryRoute
import kr.co.main.calendar.ui.calendar_screen.add_schedule_screen.AddScheduleRoute
import kr.co.main.calendar.ui.calendar_screen.calendar_screen.CalendarRoute
import kr.co.main.community.BulletinDetailScreen
import kr.co.main.community.BulletinWritingRoute
import kr.co.main.community.CommunityRoute
import kr.co.main.home.HomeRoute
import kr.co.main.home.chat.ChatRoute
import kr.co.main.my.MyPageRoute
import kr.co.main.my.profile.MyPageProfileEditRoute
import kr.co.main.my.setting.MyPageSettingRoute
import kr.co.main.my.setting.delete.MyPageSettingDeleteAccountRoute


const val MAIN_ROUTE = "mainRoute"

internal const val CHAT_ROUTE = "chatRoute"

internal const val NOTIFICATION_ROUTE = "notificationRoute"

internal data object CalendarRoute {
    const val ADD_SCHEDULE_ROUTE = "add_schedule_route"
    const val ADD_DIARY_ROUTE = "add_diary_route"
    const val SEARCH_DIARY_ROUTE = "search_diary_route"
}

internal const val ACCOUNT_BOOK_ROUTE = "accountBookRoute"

internal data object CommunityRoute {
    const val WRITING_ROUTE = "community_writing_route"
    const val BULLETIN_DETAIL_ROUTE = "community_bulletin_detail_route"
}

internal data object MyPageRoute {
    const val EDIT_ROUTE = "myPageProfileEditRoute"
    const val SETTING_ROUTE = "myPageSettingRoute"
    const val SETTING_NOTIFICATION_ROUTE = "myPageSettingNotificationRoute"
    const val SETTING_PRIVACY_POLICY_ROUTE = "myPageSettingPrivacyPolicyRoute"
    const val SETTING_LOGOUT_ROUTE = "myPageSettingLogoutRoute"
    const val SETTING_APP_INFO_ROUTE = "myPageSettingAppInfoRoute"
    const val SETTING_DELETE_ACCOUNT_ROUTE = "myPageSettingDeleteAccountRoute"
}


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
                        navToAddSchedule = { navController.navigate(CalendarRoute.ADD_SCHEDULE_ROUTE) },
                        navToAddDiary = { navController.navigate(CalendarRoute.ADD_DIARY_ROUTE) },
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
                    CommunityRoute(
                        navigateToWriting = { navController.navigate(CommunityRoute.WRITING_ROUTE) },
                        navigateToNotification = {},
                        navigateToBulletinDetail = {
                            navController.navigate(CommunityRoute.BULLETIN_DETAIL_ROUTE)
                        },
                    )
                }

                composable(
                    route = MainBottomRoute.MY_PAGE.route
                ) {
                    MyPageRoute(
                        navigateToProfileEdit = {
                            navController.navigate(MyPageRoute.EDIT_ROUTE)
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
        route = CalendarRoute.ADD_SCHEDULE_ROUTE
    ) {
        AddScheduleRoute()
    }
    composable(
        route = CalendarRoute.ADD_DIARY_ROUTE
    ) {
        AddDiaryRoute()
    }
    composable(
        route = CalendarRoute.SEARCH_DIARY_ROUTE
    ) {

    }

    composable(
        route = ACCOUNT_BOOK_ROUTE
    ) {
        AccountBookRegister()
    }

    composable(
        route = CommunityRoute.WRITING_ROUTE
    ) {
        BulletinWritingRoute(popBackStack = navController::popBackStack)
    }

    composable(
        route = CommunityRoute.BULLETIN_DETAIL_ROUTE
    ) {
        BulletinDetailScreen(popBackStack = navController::popBackStack)
    }

    composable(
        route = MyPageRoute.EDIT_ROUTE
    ) {
        MyPageProfileEditRoute(
            popBackStack = navController::popBackStack,
            navigateToMyPage = {
                navController.navigate(MAIN_ROUTE) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }

    composable(
        route = MyPageRoute.SETTING_ROUTE
    ) {
        MyPageSettingRoute(
            popBackStack = navController::popBackStack,
            navigateTo = { route ->
                navController.navigate(route)
            }
        )
    }

    composable(
        route = MyPageRoute.SETTING_NOTIFICATION_ROUTE
    ) {

    }

    composable(
        route = MyPageRoute.SETTING_PRIVACY_POLICY_ROUTE
    ) {

    }

    composable(
        route = MyPageRoute.SETTING_LOGOUT_ROUTE
    ) {

    }

    composable(
        route = MyPageRoute.SETTING_APP_INFO_ROUTE
    ) {

    }

    composable(
        route = MyPageRoute.SETTING_DELETE_ACCOUNT_ROUTE
    ) {
        MyPageSettingDeleteAccountRoute(
            popBackStack = navController::popBackStack
        )
    }
}
