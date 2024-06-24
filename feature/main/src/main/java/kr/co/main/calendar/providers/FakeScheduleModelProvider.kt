package kr.co.main.calendar.providers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.ScheduleModel
import kr.co.main.model.calendar.type.CropModelColorType
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScheduleModelType
import java.time.LocalDate
import java.time.LocalDateTime

internal class FakeScheduleModelProvider : PreviewParameterProvider<ScheduleModel> {
    override val values = sequenceOf(
        ScheduleModel(
            id = 1,
            type = ScheduleModelType.Crop(
                CropModel(
                    type = CropModelType.POTATO,
                    color = CropModelColorType.POTATO)
            ),
            title = "감자 수확일",
            startDate = LocalDate.of(2024, 5, 21),
            endDate = LocalDate.of(2024, 5, 25),
            memo = "봄 감자, 알 감자 수확",
        )
    )

    override val count = values.count()
}