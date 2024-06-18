package kr.co.main.calendar.ui.calendarscreen

import androidx.navigation.NavController

internal enum class CalendarRouteNavDest(val route: String) {
    CALENDAR_SCREEN("calendar_screen"),
    ADD_SCHEDULE_SCREEN("add_schedule_screen"),
    ADD_DIARY_SCREEN("add_diary_screen")
}

internal class CalendarRouteNavActions(
    private val navController: NavController
) {
    fun navigateToAddScheduleScreen() {
        navController.navigate(CalendarRouteNavDest.ADD_SCHEDULE_SCREEN.route)
    }

    fun navigateToAddDiaryScreen() {
        navController.navigate(CalendarRouteNavDest.ADD_DIARY_SCREEN.route)
    }
}