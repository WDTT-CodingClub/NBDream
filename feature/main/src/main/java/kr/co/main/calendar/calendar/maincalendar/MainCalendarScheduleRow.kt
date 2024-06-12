package kr.co.main.calendar.calendar.maincalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kr.co.common.util.iterator
import kr.co.main.calendar.calendar.maincalendar.ScheduleItemScope.scheduleItem
import kr.co.main.calendar.common.CalendarDesignToken
import kr.co.main.calendar.model.ScheduleModel
import kr.co.main.calendar.providers.FakeScheduleModelProvider
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Alarm
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period

private const val SLOT_EMPTY = 0
private const val SLOT_FULL = 1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun MainCalendarScheduleRow(
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    schedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {
    val content = @Composable {
        schedules.filter {
            it.startDate in weekStartDate..weekEndDate ||
                    it.endDate in weekStartDate..weekEndDate
        }.forEach { schedule ->
            ScheduleItemScope.MainCalendarScheduleItem(
                schedule = schedule,
                modifier = Modifier.scheduleItem(
                    startDate = if (schedule.startDate <= weekStartDate) weekStartDate else schedule.startDate,
                    endDate = if (weekEndDate <= schedule.endDate) weekEndDate else schedule.endDate
                )
            )
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                val duration = with(it.parentData as ScheduleItemParentData) {
                    Period.between(startDate, endDate).days + 1
                }
                it.measure(
                    constraints.copy(
                        maxWidth = (constraints.maxWidth / 7) * (duration)
                    )
                )
            }

            val layoutHeight = getLayoutHeight(placeables)
            val scheduleSlots = mutableMapOf(
                DayOfWeek.SUNDAY.value to MutableList(layoutHeight) { SLOT_EMPTY },
                DayOfWeek.MONDAY.value to MutableList(layoutHeight) { SLOT_EMPTY },
                DayOfWeek.TUESDAY.value to MutableList(layoutHeight) { SLOT_EMPTY },
                DayOfWeek.WEDNESDAY.value to MutableList(layoutHeight) { SLOT_EMPTY },
                DayOfWeek.THURSDAY.value to MutableList(layoutHeight) { SLOT_EMPTY },
                DayOfWeek.FRIDAY.value to MutableList(layoutHeight) { SLOT_EMPTY },
                DayOfWeek.SATURDAY.value to MutableList(layoutHeight) { SLOT_EMPTY }
            )

            layout(
                width = constraints.maxWidth,
                height = layoutHeight
            ) {
                placeables.forEachIndexed { index, placeable ->
                    val scheduleParentData = placeable.parentData as ScheduleItemParentData
                    val startDate = scheduleParentData.startDate
                    val endDate = scheduleParentData.endDate

                    placeable.place(
                        x = getXPosition(
                            dayOfWeek = startDate.dayOfWeek,
                            constraints = constraints
                        ),
                        y = getYPosition(
                            scheduleSlots = scheduleSlots,
                            startValue = startDate.dayOfWeek.value,
                            endValue = endDate.dayOfWeek.value,
                            placeableHeight = placeable.height
                        )
                    )
                }
            }
        }
    )
}

@Composable
private fun ScheduleItemScope.MainCalendarScheduleItem(
    schedule: ScheduleModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(CalendarDesignToken.SCHEDULE_ITEM_HEIGHT.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(
                color = when (schedule.category) {
                    is ScheduleModel.Category.All -> Color.LightGray
                    is ScheduleModel.Category.Crop -> Color(schedule.category.crop.color)
                }
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (schedule.isAlarmOn) {
            Icon(
                modifier = Modifier.padding(end = Paddings.xsmall),
                imageVector = DreamIcon.Alarm,
                contentDescription = "",
                tint = Color.Unspecified
            )
        }
        Text(
            text = schedule.title,
            style = MaterialTheme.typo.labelR,
            color = MaterialTheme.colors.text1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun MainCalendarScheduleItemPreview(
    @PreviewParameter(FakeScheduleModelProvider::class) schedule: ScheduleModel
) {
    ScheduleItemScope.MainCalendarScheduleItem(schedule = schedule)
}

@LayoutScopeMarker
@Immutable
object ScheduleItemScope {
    @RequiresApi(Build.VERSION_CODES.O)
    @Stable
    fun Modifier.scheduleItem(
        startDate: LocalDate,
        endDate: LocalDate
    ) = then(
        ScheduleItemParentData(startDate, endDate)
    )
}

private class ScheduleItemParentData(
    val startDate: LocalDate,
    val endDate: LocalDate
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@ScheduleItemParentData
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getLayoutHeight(placeables: List<Placeable>): Int {
    if(placeables.isEmpty()) return 0

    var scheduleCount = mutableMapOf(
        DayOfWeek.SUNDAY.value to 0,
        DayOfWeek.MONDAY.value to 0,
        DayOfWeek.TUESDAY.value to 0,
        DayOfWeek.WEDNESDAY.value to 0,
        DayOfWeek.THURSDAY.value to 0,
        DayOfWeek.FRIDAY.value to 0,
        DayOfWeek.SATURDAY.value to 0
    )
    placeables.forEach {
        with(it.parentData as ScheduleItemParentData) {
            for (date in startDate..endDate) {
                scheduleCount[date.dayOfWeek.value] = scheduleCount[date.dayOfWeek.value]!! + 1
            }
        }
    }
    return scheduleCount.values.max() * placeables.first().height
}

private fun getYPosition(
    scheduleSlots: Map<Int, MutableList<Int>>,
    startValue: Int,
    endValue: Int,
    placeableHeight: Int
): Int {
    val maxSlot = scheduleSlots.values.first().size-1
    var availableSlot = maxSlot
    for(slot in 0 .. maxSlot) {
        var isAvailable = true
        for (i in startValue..endValue) {
            if(scheduleSlots[i]!![slot] != SLOT_EMPTY){
                isAvailable = false
                break
            }
        }
        if(isAvailable){
            for (i in startValue..endValue)
                scheduleSlots[i]!![slot] = SLOT_FULL
            availableSlot = slot
            break
        }
    }
    return availableSlot * placeableHeight
}