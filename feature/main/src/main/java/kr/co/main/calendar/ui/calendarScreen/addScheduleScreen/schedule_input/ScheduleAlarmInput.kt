package kr.co.main.calendar.ui.calendarScreen.addScheduleScreen.schedule_input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDateTime

@Composable
internal fun ScheduleAlarmInput(
    isAlarmSet: Boolean,
    onAlarmSet: () -> Unit,
    onAlarmDateTimeInput: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    alarmDateTime: LocalDateTime? = null,
) {
    var showAlarmDateTimeInput by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()){
        Row(modifier = Modifier.fillMaxWidth()) {

        }

    }
}

@Composable
private fun AlarmDateTimeInput(
    alarmDateTime: LocalDateTime,
    onAlarmDateTimeInput: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
){

}


@Preview
@Composable
private fun ScheduleAlarmInputPreview() {
    var isAlarmSetState by remember { mutableStateOf(false) }
    var alarmDateTimeState by remember { mutableStateOf<LocalDateTime?>(null) }

    ScheduleAlarmInput(
        isAlarmSet = isAlarmSetState,
        onAlarmSet = {
            if (isAlarmSetState) {
                isAlarmSetState = false
                alarmDateTimeState = null
            } else {
                isAlarmSetState = true
                alarmDateTimeState = LocalDateTime.now()
            }
        },
        onAlarmDateTimeInput = {}
    )
}