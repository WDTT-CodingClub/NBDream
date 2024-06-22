package kr.co.main.calendar.ui

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
        override val baseRoute = "add_schedule_route"
        override val arguments = listOf(
            navArgument("cropNameId") { type = NavType.IntType }
        )

        override fun buildRoute(args: Any): String = "$baseRoute/${args as Int}"
    }

    data object AddDiaryRoute : CalendarNavGraph() {
        override val baseRoute = "add_diary_route"
        override val arguments = listOf(
            navArgument("cropNameId") { type = NavType.IntType }
        )

        override fun buildRoute(args: Any): String = "$baseRoute/${args as Int}"
    }

    data object SearchDiaryRoute : CalendarNavGraph() {
        override val baseRoute = "search_diary_route"
        override val arguments = listOf(
            navArgument("cropNameId") { type = NavType.IntType },
            navArgument("year") { type = NavType.IntType },
            navArgument("month") { type = NavType.IntType }
        )

        override fun buildRoute(args: Any): String = with(args as List<*>) {
            "${baseRoute}/${this[0] as Int}/${this[1] as Int}/${this[2] as Int}"
        }
    }
}