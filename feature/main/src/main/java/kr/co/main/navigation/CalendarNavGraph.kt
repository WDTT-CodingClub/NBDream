package kr.co.main.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kr.co.main.model.calendar.type.ScreenModeType

internal sealed class CalendarNavGraph {
    protected abstract val baseRoute: String
    abstract val arguments: List<NamedNavArgument>

    val route: String
        get() = StringBuilder()
            .append("${baseRoute}?")
            .apply {
                arguments.forEachIndexed { index, arg ->
                    append("${arg.name}={${arg.name}}")
                    if (index != arguments.lastIndex) append("&")
                }
            }
            .toString()

    fun buildRoute(argumentValues: List<*>): String = StringBuilder()
        .append(baseRoute)
        .apply {
            if (argumentValues.any { it != null }) {
                append("?")
                argumentValues.forEachIndexed { index, value ->
                    value?.let {
                        append("${arguments[index].name}=${value}")
                        if (index != arguments.lastIndex) append("&")
                    }
                }
            }
        }
        .toString()

    data object AddScheduleRoute : CalendarNavGraph() {
        override val baseRoute = ADD_SCHEDULE_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) {
                nullable = true
                defaultValue = null
                type = NavType.IntType
            },
            navArgument(ARG_SCREEN_MODE_ID) {
                defaultValue = ScreenModeType.POST_MODE.id
                type = NavType.IntType
            },
            navArgument(ARG_SCHEDULE_ID) {
                nullable = true
                defaultValue = null
                type = NavType.IntType
            }
        )
    }

    data object AddDiaryRoute : CalendarNavGraph() {
        override val baseRoute = ADD_DIARY_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) {
                type = NavType.IntType
            },
            navArgument(ARG_SCREEN_MODE_ID) {
                defaultValue = ScreenModeType.POST_MODE.id
                type = NavType.IntType
            },
            navArgument(ARG_DIARY_ID) {
                nullable = true
                defaultValue = null
                type = NavType.IntType
            }
        )
    }

    data object SearchDiaryRoute : CalendarNavGraph() {
        override val baseRoute = SEARCH_DIARY_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) {
                nullable = true
                defaultValue = null
                type = NavType.IntType
            },
            navArgument(ARG_YEAR) { type = NavType.IntType },
            navArgument(ARG_MONTH) { type = NavType.IntType }
        )
    }

    companion object {
        private const val ADD_SCHEDULE_BASE_ROUTE = "add_schedule_route"
        private const val ADD_DIARY_BASE_ROUTE = "add_diary_route"
        private const val SEARCH_DIARY_BASE_ROUTE = "search_diary_route"

        const val ARG_CROP_NAME_ID = "crop_name_id"
        const val ARG_YEAR = "year"
        const val ARG_MONTH = "month"
        const val ARG_SCREEN_MODE_ID = "screen_mode_id"
        const val ARG_SCHEDULE_ID = "schedule_id"
        const val ARG_DIARY_ID = "diary_id"
    }
}