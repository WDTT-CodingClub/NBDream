package kr.co.main.calendar.screen.calendarScreen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.co.domain.entity.type.FarmWorkEraType
import kr.co.domain.entity.type.FarmWorkType
import kr.co.main.R
import kr.co.main.model.calendar.FarmWorkModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import timber.log.Timber

internal class FarmWorkCalendarStateHolder(
    private val calendarMonth: Int,
    private val farmWorks: List<FarmWorkModel>
) {
    val farmWorkEraInfo = listOf(
        R.string.feature_main_calendar_farm_work_era_early,
        R.string.feature_main_calendar_farm_work_era_mid,
        R.string.feature_main_calendar_farm_work_era_late,
    )

    val farmWorkCategoryInfo =
        listOf(
            Pair(R.string.feature_main_calendar_farm_work_growth, FarmWorkType.GROWTH),
            Pair(R.string.feature_main_calendar_farm_work_climate, FarmWorkType.CLIMATE),
            Pair(R.string.feature_main_calendar_farm_work_pest, FarmWorkType.PEST)
        )

    fun getFilteredFarmWorks(category: FarmWorkType) =
        farmWorks
            .filter { it.type == category }
            .map {
                if (it.startMonth < calendarMonth)
                    it.copy(
                        startMonth = calendarMonth,
                        startEra = FarmWorkEraType.EARLY
                    )
                else it
            }
            .map {
                if (it.endMonth > calendarMonth)
                    it.copy(
                        endMonth = calendarMonth,
                        endEra = FarmWorkEraType.LATE
                    )
                else it
            }
}


@Composable
internal fun FarmWorkCalendar(
    calendarMonth: Int,
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    val stateHolder = rememberFarmWorkCalendarStateHolder(
        calendarMonth = calendarMonth,
        farmWorks = farmWorks
    )

    if(farmWorks.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            FarmWorkEraRow(
                modifier = Modifier.padding(vertical = Paddings.medium),
                farmWorkEraInfo = stateHolder.farmWorkEraInfo
            )

            FarmWorkCalendarContent(
                farmWorkCategoryInfo = stateHolder.farmWorkCategoryInfo,
                getFilteredFarmWorks = stateHolder::getFilteredFarmWorks
            )
        }
    }
    else{
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xlarge),
            text = stringResource(id = R.string.feature_main_calendar_farm_work_empty),
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun rememberFarmWorkCalendarStateHolder(
    calendarMonth: Int,
    farmWorks: List<FarmWorkModel>,
) = remember(calendarMonth, farmWorks) {
    Timber.d("rememberFarmWorkCalendarStateHolder) farmworks: ${farmWorks.map { it.farmWork }}")
    FarmWorkCalendarStateHolder(
        calendarMonth = calendarMonth,
        farmWorks = farmWorks
    )
}


@Composable
private fun FarmWorkEraRow(
    farmWorkEraInfo: List<Int>,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        farmWorkEraInfo.forEach {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = it),
                style = MaterialTheme.typo.labelSB,
                color = MaterialTheme.colors.text1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FarmWorkCalendarContent(
    farmWorkCategoryInfo: List<Pair<Int, FarmWorkType>>,
    getFilteredFarmWorks: (FarmWorkType) -> List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        for (categoryInfo in farmWorkCategoryInfo) {
            if (getFilteredFarmWorks(categoryInfo.second).isEmpty())
                continue

            Text(
                modifier = Modifier.padding(vertical = Paddings.medium),
                text = stringResource(id = categoryInfo.first),
                style = MaterialTheme.typo.labelSB
            )
            FarmWorkCalendarRow(
                modifier = Modifier.fillMaxWidth(),
                farmWorks = getFilteredFarmWorks(categoryInfo.second)
            )
        }
    }
}

@Composable
private fun FarmWorkCalendarRow(
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    val content: @Composable () -> Unit = {
        farmWorks.forEach {
            FarmWorkItem(
                modifier = Modifier.padding(bottom = Paddings.xsmall),
                farmWork = it
            )
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.mapIndexed { index, measurable ->
                val eraLength = with(farmWorks[index]) { endEra.value - startEra.value + 1 }
                measurable.measure(
                    constraints.copy(
                        minWidth = (constraints.maxWidth / 3) * eraLength
                    )
                )
            }

            layout(
                width = constraints.maxWidth,
                height = placeables.first().height * placeables.size
            ) {
                var yPosition = 0
                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        x = when (farmWorks[index].startEra) {
                            FarmWorkEraType.EARLY -> 0
                            FarmWorkEraType.MID -> constraints.maxWidth / 3
                            FarmWorkEraType.LATE -> constraints.maxWidth / 3 * 2
                        },
                        y = yPosition
                    )
                    yPosition += placeable.height
                }
            }
        }
    )
}

@Composable
private fun FarmWorkItem(
    farmWork: FarmWorkModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(kr.co.main.calendar.CalendarDesignToken.FARM_WORK_ITEM_HEIGHT.dp)
            .clip(shape = RoundedCornerShape(kr.co.main.calendar.CalendarDesignToken.FARM_WORK_ITEM_CORNER_RADIUS.dp))
            .background(color = MaterialTheme.colors.gray8)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = farmWork.farmWork,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.text1,
            textAlign = TextAlign.Center
        )
    }
}
