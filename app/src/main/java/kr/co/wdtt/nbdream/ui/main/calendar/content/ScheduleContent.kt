package kr.co.wdtt.nbdream.ui.main.calendar.content

import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.domain.entity.ScheduleCategory
import kr.co.wdtt.nbdream.domain.entity.ScheduleEntity
import kr.co.wdtt.nbdream.ui.icon.dreamicon.Alarm
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon
import kr.co.wdtt.nbdream.ui.main.calendar.providers.FakeScheduleEntityProvider
import kr.co.wdtt.nbdream.ui.theme.Paddings
import kr.co.wdtt.nbdream.ui.theme.colors
import kr.co.wdtt.nbdream.ui.theme.typo
import kr.co.wdtt.nbdream.ui.util.toDateString
import kr.co.wdtt.nbdream.ui.util.toDateTimeString

private const val CROP_COLOR_CIRCLE_SIZE = 8
private const val ALARM_ICON_SIZE = 16

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleContent(
    schedule: ScheduleEntity,
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
    category: ScheduleCategory,
    title: String,
    modifier: Modifier = Modifier
) {
    val categoryColor = remember(category) {
        when (category) {
            is ScheduleCategory.Crop -> category.dreamCrop.cropColor
            is ScheduleCategory.All -> Color.LightGray.toArgb()
        }
    }

    Row(modifier = modifier) {
        CategoryIndicator(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = Paddings.small),
            categoryColor = categoryColor
        )
        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typo.bodyM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
private fun CategoryIndicator(
    @ColorInt categoryColor: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(CROP_COLOR_CIRCLE_SIZE.dp)
            .clip(CircleShape)
            .background(Color(categoryColor)),
    )
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
    Row(modifier = modifier) {
        alarmDateTime?.let {
            Image(
                modifier = Modifier.size(ALARM_ICON_SIZE.dp),
                imageVector = DreamIcon.Alarm, contentDescription = ""
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ScheduleContentPreview(
    @PreviewParameter(FakeScheduleEntityProvider::class) schedule: ScheduleEntity
) {
    ScheduleContent(schedule = schedule)
}
