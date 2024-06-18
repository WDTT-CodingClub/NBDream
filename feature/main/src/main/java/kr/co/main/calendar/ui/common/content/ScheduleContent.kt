package kr.co.main.calendar.ui.common.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kr.co.common.util.toDateString
import kr.co.common.util.toDateTimeString
import kr.co.main.calendar.common.CalendarDesignToken
import kr.co.main.calendar.model.ScheduleModel
import kr.co.main.calendar.providers.FakeScheduleModelProvider
import kr.co.main.calendar.ui.common.CalendarCategoryIndicator
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Alarm
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun ScheduleContent(
    schedule: ScheduleModel,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            ScheduleTitle(
                modifier = Modifier.padding(end = Paddings.xsmall),
                category = schedule.category,
                title = schedule.title
            )
            ScheduleDate(
                startDate = schedule.startDate.toDateString(),
                endDate = schedule.endDate.toDateString()
            )
        }

        if (schedule.isAlarmOn) {
            ScheduleAlarm(
                modifier = Modifier.padding(start = Paddings.xlarge),
                alarmDateTime = schedule.alarmDateTime?.toDateTimeString()
            )
        }
        ScheduleMemo(
            modifier = Modifier.padding(start = Paddings.xlarge),
            memo = schedule.memo
        )
    }
}

@Composable
private fun ScheduleTitle(
    category: ScheduleModel.Category,
    title: String,
    modifier: Modifier = Modifier
) {
    val categoryColor = remember(category) {
        when (category) {
            is ScheduleModel.Category.Crop -> Color(category.crop.color).toArgb()
            is ScheduleModel.Category.All -> Color.LightGray.toArgb()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarCategoryIndicator(
            modifier = Modifier.padding(end = Paddings.small),
            categoryColor = categoryColor
        )
        Text(
            text = title,
            style = MaterialTheme.typo.bodyM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
private fun ScheduleDate(
    startDate: String,
    modifier: Modifier = Modifier,
    endDate: String? = null
) {
    val scheduleDate = if (startDate != endDate) " $startDate~$endDate" else " $startDate"
    Text(
        modifier = modifier,
        text = scheduleDate,
        style = MaterialTheme.typo.labelL,
        color = MaterialTheme.colors.text2
    )
}

@Composable
private fun ScheduleAlarm(
    alarmDateTime: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        alarmDateTime?.let {
            Icon(
                modifier = Modifier.size(CalendarDesignToken.DIARY_ALARM_ICON_SIZE.dp),
                imageVector = DreamIcon.Alarm, contentDescription = "",
                tint = MaterialTheme.colors.yellow1
            )
            Text(
                modifier = Modifier.padding(start = Paddings.xsmall),
                text = alarmDateTime,
                style = MaterialTheme.typo.labelL,
                color = MaterialTheme.colors.yellow1
            )
        }
    }
}

@Composable
private fun ScheduleMemo(
    memo: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = memo,
        style = MaterialTheme.typo.labelL,
        color = MaterialTheme.colors.text2
    )
}

@Preview(showBackground = true)
@Composable
private fun ScheduleContentPreview(
    @PreviewParameter(FakeScheduleModelProvider::class) schedule: ScheduleModel
) {
    ScheduleContent(schedule = schedule)
}