package kr.co.main.calendar.providers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.ScheduleModel
import java.time.LocalDate
import java.time.LocalDateTime

internal class FakeScheduleModelProvider : PreviewParameterProvider<ScheduleModel> {
    @RequiresApi(Build.VERSION_CODES.O)
    override val values = sequenceOf(
        ScheduleModel(
            id = 1,
            category = ScheduleModel.Category.Crop(CropModel.POTATO),
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