package kr.co.main.calendar.common

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.widget.CustomDatePickerDialog
import kr.co.main.calendar.CalendarDesignToken
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.DatePicker
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun CalendarDatePicker(
    date: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp))
            .background(MaterialTheme.colors.gray9)
            .clickable {
                showDatePicker = true
            }
    ) {
        Row(
            modifier = modifier.padding(Paddings.xlarge),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.weight(9f),
                text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = DreamIcon.DatePicker,
                tint = MaterialTheme.colors.gray4,
                contentDescription = ""
            )
        }
    }
    if (showDatePicker) {
        CustomDatePickerDialog(
            date = date,
            onClickCancel = {
                showDatePicker = false
            }
        ) { dateString ->
            val selectedDate =
                LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            var isValid = true
            Timber.d("onClickConfirm) selectedDate: $selectedDate")

            minDate?.let { minDate ->
                if (selectedDate < minDate) {
                    Toast.makeText(
                        context,
                        "${minDate.year}년 ${minDate.monthValue}월 ${minDate.dayOfMonth}일 이전 날짜를 선택할 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    isValid = false
                }
            }
            maxDate?.let {
                if (selectedDate > maxDate) {
                    Toast.makeText(
                        context,
                        "$${maxDate.year}년 ${maxDate.monthValue}월 ${maxDate.dayOfMonth}일 이후 날짜를 선택할 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    isValid = false
                }
            }

            if(isValid) onDateSelect(selectedDate)
            showDatePicker = false
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CalendarDatePickerPreview() {
    CalendarDatePicker(
        modifier = Modifier.fillMaxWidth(),
        date = LocalDate.now(),
        onDateSelect = {}
    )
}