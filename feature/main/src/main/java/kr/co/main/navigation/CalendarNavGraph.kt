package kr.co.main.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kr.co.main.model.calendar.type.ScreenModeType
import timber.log.Timber
import java.time.LocalDate

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

    fun buildRoute(values: List<*>): String = StringBuilder()
        .append("${baseRoute}?")
        .apply {
            values.forEachIndexed { index, value ->
                value?.let{
                    append("${arguments[index].name}=${value}&")
                }
            }
            if(values.any{it != null}) deleteCharAt(lastIndex)
        }
        .toString()

    data object AddScheduleRoute : CalendarNavGraph() {
        override val baseRoute = ADD_SCHEDULE_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) {
                nullable = true
            },
            navArgument(ARG_SCREEN_MODE_ID) {
                defaultValue = ScreenModeType.POST_MODE.id
                type = NavType.IntType
            },
            navArgument(ARG_SCHEDULE_ID) {
                nullable = true
            }
        )
    }

    data object AddDiaryRoute : CalendarNavGraph() {
        override val baseRoute = ADD_DIARY_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) {
                nullable = true
            },
            navArgument(ARG_SCREEN_MODE_ID) {
                defaultValue = ScreenModeType.POST_MODE.id
                type = NavType.IntType
            },
            navArgument(ARG_DIARY_ID) {
                nullable = true
            }
        )
    }

    data object SearchDiaryRoute : CalendarNavGraph() {
        override val baseRoute = SEARCH_DIARY_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_CROP_NAME_ID) {
                nullable = true
            }
        )
    }

    data object FarmWorkInfoRoute : CalendarNavGraph() {
        override val baseRoute = FARM_WORK_INFO_BASE_ROUTE
        override val arguments = listOf(
            navArgument(ARG_FARM_WORK_TITLE) {
                type = NavType.StringType
                nullable = true
            },
            navArgument(ARG_FARM_WORK_VIDEO_URL){
                type = NavType.StringType
                nullable = true
            }
        )
    }

    companion object {
        private const val ADD_SCHEDULE_BASE_ROUTE = "add_schedule_route"
        private const val ADD_DIARY_BASE_ROUTE = "add_diary_route"
        private const val SEARCH_DIARY_BASE_ROUTE = "search_diary_route"
        private const val FARM_WORK_INFO_BASE_ROUTE = "farm_work_info_route"

        const val ARG_CROP_NAME_ID = "crop_name_id"
        const val ARG_SCREEN_MODE_ID = "screen_mode_id"
        const val ARG_SCHEDULE_ID = "schedule_id"
        const val ARG_DIARY_ID = "diary_id"

        const val ARG_REINITIALIZE = "reinitialize"

        const val ARG_FARM_WORK_TITLE = "farm_work_title"
        const val ARG_FARM_WORK_VIDEO_URL = "farm_work_video_url"
    }
}