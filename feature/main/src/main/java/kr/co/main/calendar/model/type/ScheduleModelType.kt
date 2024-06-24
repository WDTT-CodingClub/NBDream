package kr.co.main.calendar.model.type

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kr.co.main.R
import kr.co.main.calendar.model.CropModel

internal sealed class ScheduleModelType(
    @StringRes val nameId: Int,
    @ColorInt val color: Int
) {
    data object All : ScheduleModelType(
        nameId = R.string.feature_main_calendar_category_all,
        color = Color.LightGray.toArgb()
    )

    data class Crop(val cropModel: CropModel) : ScheduleModelType(
        nameId = cropModel.type.nameId,
        color = cropModel.color.color
    )
}