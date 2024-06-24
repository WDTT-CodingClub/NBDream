package kr.co.main.calendar.ui.calendarScreen.addScheduleScreen.schedule_input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.co.main.model.calendar.type.ScheduleModelType
import java.time.LocalDate

internal sealed class ScheduleInputContent{
    abstract val headerId:Int

    data class GeneralInputContent(
        override val headerId:Int,
        val type: ScheduleModelType,
        val title:String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val onTypeInput: (ScheduleModelType) -> Unit,
        val onTitleInput: (String) -> Unit,
        val onStartDateInput: (LocalDate) -> Unit,
        val onEndDateInput: (LocalDate) -> Unit
    ): ScheduleInputContent()
}


@Composable
internal fun ScheduleInputWrapper(
    scheduleInputContent: ScheduleInputContent,
    modifier:Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {

    }
}