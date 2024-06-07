package kr.co.main.calendar.providers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.domain.entity.ScheduleCategory
import kr.co.domain.entity.ScheduleEntity
import kr.co.domain.entity.plzLookThisPakage.DreamCrop
import java.time.LocalDate
import java.time.LocalDateTime

internal class FakeScheduleEntityProvider : PreviewParameterProvider<ScheduleEntity> {
    @RequiresApi(Build.VERSION_CODES.O)
    override val values = sequenceOf(
        ScheduleEntity(
            category = ScheduleCategory.Crop(DreamCrop.POTATO),
            title = "감자 수확일",
            startDate = LocalDate.of(2024, 5, 21),
            endDate = LocalDate.of(2024, 5, 25),
            memo = "봄 감자, 알 감자 수확",
            isAlarmOn = true,
            alarmDateTime = LocalDateTime.of(2024, 5, 21, 9, 0, 0)
        )
    )
    @RequiresApi(Build.VERSION_CODES.O)
    override val count = values.count()
}