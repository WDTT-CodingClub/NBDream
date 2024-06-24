package kr.co.main.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

internal sealed class CalendarNavGraph {
    protected abstract val baseRoute: String
    abstract val arguments: List<NamedNavArgument>

    val route: String
        get() = StringBuilder()
            .append(baseRoute)
            .apply {
                for (arg in arguments) append("/{${arg.name}}")
            }
            .toString()

    open fun buildRoute(args: Any): String = baseRoute

    data object AddScheduleRoute : CalendarNavGraph() {
        override val baseRoute = ADD_SCHEDULE_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) { type = NavType.IntType }
        )

        override fun buildRoute(args: Any): String = "$baseRoute/${args as Int}"
    }

    data object AddDiaryRoute : CalendarNavGraph() {
        override val baseRoute = ADD_DIARY_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) { type = NavType.IntType }
        )

        override fun buildRoute(args: Any): String = "$baseRoute/${args as Int}"
    }

    data object SearchDiaryRoute : CalendarNavGraph() {
        override val baseRoute = SEARCH_DIARY_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) { type = NavType.IntType },
            navArgument(ARG_YEAR) { type = NavType.IntType },
            navArgument(ARG_MONTH) { type = NavType.IntType }
        )

        override fun buildRoute(args: Any): String = with(args as List<*>) {
            "$baseRoute/${this[0] as Int}/${this[1] as Int}/${this[2] as Int}"
        }
    }

    companion object{
        private const val ADD_SCHEDULE_BASE_ROUTE = "add_schedule_route"
        private const val ADD_DIARY_BASE_ROUTE = "add_diary_route"
        private const val SEARCH_DIARY_BASE_ROUTE = "search_diary_route"

        const val ARG_CROP_NAME_ID = "crop_name_id"
        const val ARG_YEAR = "year"
        const val ARG_MONTH = "month"
    }
}