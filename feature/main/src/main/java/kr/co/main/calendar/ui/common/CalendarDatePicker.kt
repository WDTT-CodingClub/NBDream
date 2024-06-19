package kr.co.main.calendar.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kr.co.common.util.toDateString
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate

@Composable
internal fun CalendarDatePicker(
    date: LocalDate,
    onDateInput: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable {
                    //TODO Date Picker 띄우기 - 수빈님 PR 올리시면 참고
                },
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier,
                text = date.toDateString(),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
            Icon(
                modifier = Modifier,
                imageVector = DreamIcon.Edit, // TODO 캘린더 아이콘으로 바꾸기
                contentDescription = ""
            )
        }
    }
}