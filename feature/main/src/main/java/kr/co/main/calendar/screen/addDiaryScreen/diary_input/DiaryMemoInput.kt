package kr.co.main.calendar.screen.addDiaryScreen.diary_input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kr.co.main.R
import kr.co.main.calendar.common.CalendarContainerTextField
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun DiaryMemoInput(
    memo: String,
    onMemoInput: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MemoTextField(
            modifier = Modifier.fillMaxWidth(),
            memo = memo,
            onMemoInput = onMemoInput
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            MemoTextCount(
                modifier = Modifier.align(Alignment.BottomEnd),
                memo = memo
            )
        }
    }
}

@Composable
private fun MemoTextField(
    memo: String,
    onMemoInput: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarContainerTextField(
        modifier = modifier,
        value = memo,
        onValueChange = onMemoInput,
        placeHolder = {
            Text(
                text = stringResource(id = R.string.feature_main_calendar_add_diary_input_memo),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
        }
    )
}

@Composable
private fun MemoTextCount(
    memo: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "${memo.length}/1000",
        style = MaterialTheme.typo.labelM,
        color = if (memo.length > 1000) MaterialTheme.colors.red1 else MaterialTheme.colors.text2
    )
}

@Preview
@Composable
private fun DiaryMemoInputPreview() {
    Surface {
        DiaryMemoInput(
            memo = "",
            onMemoInput = { _ -> }
        )
    }
}