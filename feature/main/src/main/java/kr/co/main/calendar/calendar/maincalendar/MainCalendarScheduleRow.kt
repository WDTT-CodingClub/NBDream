package kr.co.main.calendar.calendar.maincalendar

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
            val scheduleSlots = mutableMapOf<DayOfWeek, MutableList<Int>>().apply {
                for (dayOfWeek in DayOfWeek.SUNDAY..DayOfWeek.SATURDAY) {
                    put(dayOfWeek, MutableList(layoutHeight) { SLOT_EMPTY })
                }
            }

            layout(
                width = constraints.maxWidth,
                height = layoutHeight
            ) {
                for (placeable in placeables) {
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
                            startDayOfWeek = startDate.dayOfWeek,
                            endDayOfWeek = endDate.dayOfWeek,
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
            .clip(shape = RoundedCornerShape(CalendarDesignToken.SCHEDULE_ITEM_CORNER_RADIUS.dp))
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

private fun getLayoutHeight(placeables: List<Placeable>): Int {
    if (placeables.isEmpty()) return 0

    var scheduleCount = mutableMapOf<DayOfWeek, Int>().apply {
        for (dayOfWeek in DayOfWeek.SUNDAY..DayOfWeek.SATURDAY) {
            put(dayOfWeek, 0)
        }
    }
    placeables.forEach {
        with(it.parentData as ScheduleItemParentData) {
            for (date in startDate..endDate) {
                scheduleCount[date.dayOfWeek] = scheduleCount[date.dayOfWeek]!! + 1
            }
        }
    }
    return scheduleCount.values.max() * placeables.first().height
}

private fun getYPosition(
    scheduleSlots: Map<DayOfWeek, MutableList<Int>>,
    startDayOfWeek: DayOfWeek,
    endDayOfWeek: DayOfWeek,
    placeableHeight: Int
): Int {
    val maxSlot = scheduleSlots.values.first().size - 1

    for (slot in 0..maxSlot) {
        var isAvailable = true
        for (dayOfWeek in startDayOfWeek..endDayOfWeek) {
            if (scheduleSlots[dayOfWeek]!![slot] != SLOT_EMPTY) {
                isAvailable = false
                break
            }
        }
        if (isAvailable) {
            for (dayOfWeek in startDayOfWeek..endDayOfWeek) {
                scheduleSlots[dayOfWeek]!![slot] = SLOT_FULL
            }
            return slot * placeableHeight
        }
    }
    return maxSlot * placeableHeight
}

private operator fun ClosedRange<DayOfWeek>.iterator(): Iterator<DayOfWeek> {
    return object : Iterator<DayOfWeek> {
        private var next = this@iterator.start
        private val finalElement = this@iterator.endInclusive
        private var hasNext = (next != DayOfWeek.SATURDAY)

        override fun hasNext(): Boolean = hasNext
        override fun next(): DayOfWeek {
            val value = next
            if (value == finalElement) {
                hasNext = false
            } else {
                next = when (next) {
                    DayOfWeek.SUNDAY -> DayOfWeek.MONDAY
                    DayOfWeek.MONDAY -> DayOfWeek.TUESDAY
                    DayOfWeek.TUESDAY -> DayOfWeek.WEDNESDAY
                    DayOfWeek.WEDNESDAY -> DayOfWeek.THURSDAY
                    DayOfWeek.THURSDAY -> DayOfWeek.FRIDAY
                    DayOfWeek.FRIDAY -> DayOfWeek.SATURDAY
                    DayOfWeek.SATURDAY -> throw IllegalArgumentException("no day of week after saturday")
                }
            }
            return value
        }
    }
}
