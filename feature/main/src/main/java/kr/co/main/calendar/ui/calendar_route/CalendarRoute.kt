package kr.co.main.calendar.ui.calendar_route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.co.main.calendar.ui.calendar_route.add_diary_screen.AddDiaryScreen
import kr.co.main.calendar.ui.calendar_route.add_schedule_screen.AddScheduleScreen
import kr.co.main.calendar.ui.calendar_route.calendar_screen.CalendarScreen

internal data class CalendarRouteNavState(
    val navController: NavHostController,
    val startDestination: String,
    val navActions: CalendarRouteNavActions
)

@Composable
internal fun CalendarRoute(
    navToNotification: () -> Unit,
    calendarRouteNavState: CalendarRouteNavState =
        rememberCalendarRouteNavState()
) {
    NavHost(
        navController = calendarRouteNavState.navController,
        startDestination = calendarRouteNavState.startDestination
    ) {
        composable(
            route = CalendarRouteNavDest.CALENDAR_SCREEN.route
        ) {
            CalendarScreen(
                modifier = Modifier.fillMaxSize(),
                navToAddSchedule = calendarRouteNavState.navActions::navigateToAddScheduleScreen,
                navToAddDiary = calendarRouteNavState.navActions::navigateToAddDiaryScreen,
                navToNotification = navToNotification
            )
        }
        composable(
            route = CalendarRouteNavDest.ADD_SCHEDULE_SCREEN.route
        ) {
            AddScheduleScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(
            route = CalendarRouteNavDest.ADD_DIARY_SCREEN.route
        ) {
            AddDiaryScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun rememberCalendarRouteNavState(
    navController: NavHostController = rememberNavController(),
    startDestination: String = CalendarRouteNavDest.CALENDAR_SCREEN.route,
    navActions: CalendarRouteNavActions = remember(navController) {
        CalendarRouteNavActions(navController)
    }
) = remember(navController, startDestination, navActions) {
    CalendarRouteNavState(navController, startDestination, navActions)
}
