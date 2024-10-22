package kr.co.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.main.MainBottomRoute
import kr.co.main.MainNav
import kr.co.main.MainRoute
import kr.co.main.accountbook.content.AccountBookContentRoute
import kr.co.main.accountbook.create.AccountBookCreateRoute
import kr.co.main.accountbook.main.AccountBookRoute
import kr.co.main.accountbook.model.EntryType
import kr.co.main.calendar.screen.addDiaryScreen.AddDiaryRoute
import kr.co.main.calendar.screen.addScheduleScreen.AddScheduleRoute
import kr.co.main.calendar.screen.calendarScreen.CalendarRoute
import kr.co.main.calendar.screen.farmWorkInfoScreen.FarmWorkInfoRoute
import kr.co.main.calendar.screen.searchDiaryScreen.SearchDiaryRoute
import kr.co.main.community.CommunityRoute
import kr.co.main.community.detail.BulletinDetailRoute
import kr.co.main.community.writing.BulletinWritingRoute
import kr.co.main.home.HomeRoute
import kr.co.main.home.chat.ChatRoute
import kr.co.main.my.MyPageRoute
import kr.co.main.my.community.bookmark.MyPageBookmarkRoute
import kr.co.main.my.community.written.MyPageWriteRoute
import kr.co.main.my.profile.MyPageProfileEditRoute
import kr.co.main.my.setting.MyPageSettingRoute
import kr.co.main.my.setting.crop.MyPageCropSelectRoute
import kr.co.main.my.setting.delete.MyPageSettingDeleteAccountRoute
import kr.co.main.my.setting.info.MyPageSettingAppInfoRoute
import kr.co.main.my.setting.notification.MyPageSettingNotificationRoute
import kr.co.main.my.setting.policy.MyPageSettingPrivacyPolicyRoute
import kr.co.main.navigation.AccountBookRoute.ACCOUNT_KEY
import kr.co.main.notification.NotificationRoute
import timber.log.Timber
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


const val MAIN_ROUTE = "mainRoute"

internal const val CHAT_ROUTE = "chatRoute"

internal const val NOTIFICATION_ROUTE = "notificationRoute/?grant={grant}"

internal data object AccountBookRoute {
    const val ACCOUNT_BOOK_CREATE_ROUTE = "accountBookCreateRoute"
    const val ACCOUNT_BOOK_CONTENT_ROUTE = "accountBookContentRoute"
    const val ACCOUNT_BOOK_UPDATE_ROUTE = "accountBookUpdateRoute"
    const val ACCOUNT_KEY = "accountRefresh"
}

internal data object BulletinRoute {
    private const val WRITING_ROUTE = "community_writing_route"
    const val WRITING_ROUTE_RECEIVE = "$WRITING_ROUTE?crop={crop}&category={category}"
    const val WRITING_ROUTE_SEND = "$WRITING_ROUTE?crop=%d&category=%d"

    private const val BULLETIN_DETAIL_ROUTE = "community_bulletin_detail_route"
    const val BULLETIN_DETAIL_ROUTE_RECEIVE = "$BULLETIN_DETAIL_ROUTE/{id}"
    const val BULLETIN_DETAIL_ROUTE_SEND = "$BULLETIN_DETAIL_ROUTE/%d"

    private const val BULLETIN_UPDATE_ROUTE = "community_bulletin_update_route"
    const val BULLETIN_UPDATE_ROUTE_RECEIVE = "$BULLETIN_UPDATE_ROUTE/{id}"
    const val BULLETIN_UPDATE_ROUTE_SEND = "$BULLETIN_UPDATE_ROUTE/%d"

    const val BULLETIN_KEY = "communityRefresh"
}

internal data object MyPageRoute {
    const val EDIT_ROUTE = "myPageProfileEditRoute"

    const val SETTING_ROUTE = "myPageSettingRoute"
    const val SETTING_NOTIFICATION_ROUTE = "myPageSettingNotificationRoute/?grant={grant}"
    const val SETTING_PRIVACY_POLICY_ROUTE = "myPageSettingPrivacyPolicyRoute"
    const val SETTING_LOGOUT_ROUTE = "myPageSettingLogoutRoute"
    const val SETTING_APP_INFO_ROUTE = "myPageSettingAppInfoRoute"
    const val SETTING_DELETE_ACCOUNT_ROUTE = "myPageSettingDeleteAccountRoute"

    const val BOOKMARK_ROUTE = "myPageBookmarkRoute"
    const val WRITE_ROUTE = "myPageWriteRoute"
    const val CROP_SELECT_ROUTE = "myPageCropSelectRoute"
}


fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    isPermissionGranted: Boolean
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            mainBuilder = {
                composable(
                    route = MainBottomRoute.HOME.route
                ) {
                    HomeRoute(
                        navigateToNotification = {
                            val formattedRoute = NOTIFICATION_ROUTE.replace("{grant}", isPermissionGranted.toString())
                            navController.navigate(formattedRoute)
                        },
                        navigateToAddress = {
                            navController.navigate(MyPageRoute.EDIT_ROUTE)
                        },
                        navigateToChat = {
                            navController.navigate(CHAT_ROUTE)
                        },
                        navigateToCalendar = {
                            MainNav.controller.navigate(
                                MainBottomRoute.CALENDAR.route
                            ) {
                                popUpTo(MainNav.controller.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

                composable(
                    route = MainBottomRoute.CALENDAR.route
                ) {
                    CalendarRoute(
                        navController = navController,
                        navToMyPage = {
                            Timber.d("navToMyPage) called")
                            MainNav.controller.navigate(MainBottomRoute.MY_PAGE.route) {
                                popUpTo(MainNav.controller.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        navToAddSchedule = { cropNameId, screenModeId, scheduleId ->
                            navController.navigate(
                                CalendarNavGraph.AddScheduleRoute.buildRoute(
                                    listOf(cropNameId, screenModeId, scheduleId)
                                )
                            )
                        },
                        navToAddDiary = { cropNameId, screenModeId, diaryId ->
                            navController.navigate(
                                CalendarNavGraph.AddDiaryRoute.buildRoute(
                                    listOf(cropNameId, screenModeId, diaryId)
                                )
                            )
                        },
                        navToSearchDiary = { cropNameId ->
                            navController.navigate(
                                CalendarNavGraph.SearchDiaryRoute.buildRoute(
                                    listOf(cropNameId)
                                )
                            )
                        },
                        navToFarmWorkInfo = { title, videoUrl ->
                            navController.navigate(
                                CalendarNavGraph.FarmWorkInfoRoute.buildRoute(
                                    listOf(
                                        title,
                                        URLEncoder.encode(
                                            videoUrl,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    )
                                )
                            )
                        }
                    )
                }

                composable(
                    route = MainBottomRoute.ACCOUNT.route
                ) {
                    AccountBookRoute(
                        navigationToRegister = {
                            navController.navigate("${AccountBookRoute.ACCOUNT_BOOK_CREATE_ROUTE}?entryType=${EntryType.CREATE.name}")
                        },
                        navigationToContent = { id ->
                            navController.navigate("${AccountBookRoute.ACCOUNT_BOOK_CONTENT_ROUTE}/$id")
                        },
                        navController = navController,
                    )
                }

                composable(
                    route = MainBottomRoute.COMMUNITY.route
                ) {
                    CommunityRoute(
                        navigateToWriting = { crop, category ->
                            navController.navigate(
                                BulletinRoute.WRITING_ROUTE_SEND
                                    .format(crop.ordinal, category.ordinal)
                            )
                        },
                        navigateToNotification = {},
                        navigateToBulletinDetail = { id ->
                            navController.navigate(
                                BulletinRoute.BULLETIN_DETAIL_ROUTE_SEND.format(id)
                            )
                        },
                        navController = navController
                    )
                }

                composable(
                    route = MainBottomRoute.MY_PAGE.route
                ) {
                    MyPageRoute(
                        navigateToProfileEdit = {
                            navController.navigate(MyPageRoute.EDIT_ROUTE)
                        },
                        navigateToSetting = {
                            navController.navigate(MyPageRoute.SETTING_ROUTE)
                        },
                        navigateToBookmark = {
                            navController.navigate(MyPageRoute.BOOKMARK_ROUTE)
                        },
                        navigateToWrite = {
                            navController.navigate(MyPageRoute.WRITE_ROUTE)
                        },
                        navigateToCropSelect = {
                            navController.navigate(MyPageRoute.CROP_SELECT_ROUTE)
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
        route = NOTIFICATION_ROUTE,
        arguments = listOf(
            navArgument("grant") {
                type = NavType.BoolType
                nullable = false
            },
        ),
    ) {
        NotificationRoute(
            popBackStack = navController::popBackStack,
            navigationToSchedule = {
                MainNav.controller.navigate(
                    MainBottomRoute.CALENDAR.route
                ) {
                    popUpTo(MainNav.controller.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            navigationToCommunity = { id ->
                navController.navigate(
                    BulletinRoute.BULLETIN_DETAIL_ROUTE_SEND.format(id)
                )
            }
        )
    }

    composable(
        route = CalendarNavGraph.AddScheduleRoute.route,
        arguments = CalendarNavGraph.AddScheduleRoute.arguments
    ) {
        AddScheduleRoute(
            popBackStack = navController::popBackStack,
            navigateToCalendar = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    CalendarNavGraph.ARG_REINITIALIZE,
                    true
                )
                navController.popBackStack()
            }
        )
    }
    composable(
        route = CalendarNavGraph.AddDiaryRoute.route,
        arguments = CalendarNavGraph.AddDiaryRoute.arguments
    ) {
        AddDiaryRoute(
            popBackStack = navController::popBackStack,
            navToCalendar = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    CalendarNavGraph.ARG_REINITIALIZE,
                    true
                )
                navController.popBackStack()
            }
        )
    }
    composable(
        route = CalendarNavGraph.SearchDiaryRoute.route,
        arguments = CalendarNavGraph.SearchDiaryRoute.arguments
    ) {
        SearchDiaryRoute(
            popBackStack = navController::popBackStack,
        )
    }
    composable(
        route = CalendarNavGraph.FarmWorkInfoRoute.route,
        arguments = CalendarNavGraph.FarmWorkInfoRoute.arguments
    ) {
        FarmWorkInfoRoute(
            popBackStack = navController::popBackStack
        )
    }


    composable(
        route = "${AccountBookRoute.ACCOUNT_BOOK_CREATE_ROUTE}?entryType={entryType}"
    ) {
        AccountBookCreateRoute(
            popBackStack = navController::popBackStack,
            navigationToAccountBook = {
                navController.previousBackStackEntry?.savedStateHandle?.set(ACCOUNT_KEY, true)
                navController.popBackStack()
            },
            navigationToContent = {}
        )
    }

    composable(
        route = "${AccountBookRoute.ACCOUNT_BOOK_UPDATE_ROUTE}/{id}?entryType={entryType}"
    ) {
        AccountBookCreateRoute(
            popBackStack = navController::popBackStack,
            navigationToAccountBook = {},
            navigationToContent = { id ->
                navController.popBackStack()
                navController.navigate(
                    "${AccountBookRoute.ACCOUNT_BOOK_CONTENT_ROUTE}/$id?isUpdate=true"
                ) {
                    popUpTo("${AccountBookRoute.ACCOUNT_BOOK_CONTENT_ROUTE}/$id") {
                        inclusive = true
                    }
                }

            }
        )
    }

    composable(
        route = "${AccountBookRoute.ACCOUNT_BOOK_CONTENT_ROUTE}/{id}?isUpdate={isUpdate}"
    ) { backStackEntry ->
        val idString = backStackEntry.arguments?.getString("id")
        val id = idString?.toLongOrNull()
        val isUpdateString = backStackEntry.arguments?.getString("isUpdate")
        val isUpdate = isUpdateString?.toBoolean() ?: false

        AccountBookContentRoute(
            popBackStack = {
                if (isUpdate) {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "reinitialize",
                        true
                    )
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        ACCOUNT_KEY,
                        true
                    )
                }
                navController.popBackStack()

            },
            navigationToUpdate = {
                navController.navigate(
                    "${AccountBookRoute.ACCOUNT_BOOK_UPDATE_ROUTE}/$id?entryType=${EntryType.UPDATE.name}"
                )
            },
            navigationTopAccountBook = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    ACCOUNT_KEY,
                    true
                )
                navController.popBackStack()
            }
        )
    }


    composable(
        route = BulletinRoute.WRITING_ROUTE_RECEIVE,
        arguments = listOf(
            navArgument("crop") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("category") {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        BulletinWritingRoute(
            popBackStack = navController::popBackStack,
            navigationToDetail = {},
            navigationToCommunity = {
                navController.previousBackStackEntry?.savedStateHandle
                    ?.set(BulletinRoute.BULLETIN_KEY, true)
                navController.popBackStack()
            }
        )
    }

    composable(
        route = BulletinRoute.BULLETIN_DETAIL_ROUTE_RECEIVE,
        arguments = listOf(
            navArgument("id") {
                type = NavType.LongType
                nullable = false
            },
        ),
    ) {
        BulletinDetailRoute(
            popBackStack = navController::popBackStack,
            navigateToUpdate = {
                navController.navigate(BulletinRoute.BULLETIN_UPDATE_ROUTE_SEND.format(it))
            }
        )
    }

    composable(
        route = BulletinRoute.BULLETIN_UPDATE_ROUTE_RECEIVE,
    ) {
        BulletinWritingRoute(
            popBackStack = navController::popBackStack,
            navigationToDetail = { id ->
                navController.popBackStack()
                navController.navigate(
                    BulletinRoute.BULLETIN_DETAIL_ROUTE_SEND.format(id)
                ) {
                    popUpTo(BulletinRoute.BULLETIN_DETAIL_ROUTE_SEND.format(id)) {
                        inclusive = true
                    }
                }
            },
            navigationToCommunity = {}
        )
    }

    composable(
        route = MyPageRoute.EDIT_ROUTE
    ) {
        MyPageProfileEditRoute(
            popBackStack = navController::popBackStack,
            navigateToMyPage = navController::popBackStack,
        )
    }

    composable(
        route = MyPageRoute.SETTING_ROUTE
    ) {
        MyPageSettingRoute(
            popBackStack = navController::popBackStack,
            navigateToNotification = {
                val formattedRoute = MyPageRoute.SETTING_NOTIFICATION_ROUTE.replace("{grant}", isPermissionGranted.toString())
                navController.navigate(formattedRoute)
            },
            navigateToPrivacyPolicy = {
                navController.navigate(MyPageRoute.SETTING_PRIVACY_POLICY_ROUTE)
            },
            navigateToAppInfo = {
                navController.navigate(MyPageRoute.SETTING_APP_INFO_ROUTE)
            },
            navigateToDeleteAccount = {
                navController.navigate(MyPageRoute.SETTING_DELETE_ACCOUNT_ROUTE)
            }
        )
    }

    composable(
        route = MyPageRoute.SETTING_NOTIFICATION_ROUTE,
        arguments = listOf(
            navArgument("grant") {
                type = NavType.BoolType
                nullable = false
            },
        ),
    ) {
        MyPageSettingNotificationRoute(
            popBackStack = navController::popBackStack
        )
    }

    composable(
        route = MyPageRoute.SETTING_PRIVACY_POLICY_ROUTE
    ) {
        MyPageSettingPrivacyPolicyRoute(
            popBackStack = navController::popBackStack
        )
    }

    composable(
        route = MyPageRoute.SETTING_APP_INFO_ROUTE
    ) {
        MyPageSettingAppInfoRoute(
            popBackStack = navController::popBackStack
        )
    }

    composable(
        route = MyPageRoute.SETTING_DELETE_ACCOUNT_ROUTE
    ) {
        MyPageSettingDeleteAccountRoute(
            popBackStack = navController::popBackStack,
        )
    }

    composable(
        route = MyPageRoute.BOOKMARK_ROUTE
    ) {
        MyPageBookmarkRoute(
            popBackStack = navController::popBackStack,
            navigateToBulletinDetail = {
                navController.navigate(BulletinRoute.BULLETIN_DETAIL_ROUTE_SEND.format(it))
            }
        )
    }

    composable(
        route = MyPageRoute.WRITE_ROUTE
    ) {
        MyPageWriteRoute(
            popBackStack = navController::popBackStack,
            navigateToBulletinDetail = {
                navController.navigate(BulletinRoute.BULLETIN_DETAIL_ROUTE_SEND.format(it))
            }
        )
    }

    composable(
        route = MyPageRoute.CROP_SELECT_ROUTE
    ) {
        MyPageCropSelectRoute(
            popBackStack = navController::popBackStack,
            navigateToMyPage = navController::popBackStack,
        )
    }
}
