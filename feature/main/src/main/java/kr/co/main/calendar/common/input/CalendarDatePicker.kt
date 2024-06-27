package kr.co.main.calendar.common.input

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import kr.co.main.calendar.CalendarDesignToken
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.DatePicker
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun CalendarDatePicker(
    date: LocalDate,
    onDateInput: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS))
            .background(MaterialTheme.colors.gray10)
            .clickable {
                //TODO Date Picker 띄우기 - 수빈님 PR 올리시면 참고
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
}

@Preview(showBackground = true)
@Composable
private fun CalendarDatePickerPreview() {
    CalendarDatePicker(
        modifier = Modifier.fillMaxWidth(),
        date = LocalDate.now(),
        onDateInput = {}
    )

}