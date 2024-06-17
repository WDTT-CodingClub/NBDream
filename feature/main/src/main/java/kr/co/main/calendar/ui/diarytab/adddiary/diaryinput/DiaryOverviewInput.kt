package kr.co.main.calendar.ui.diarytab.adddiary.diaryinput

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kr.co.main.R
import kr.co.main.calendar.ui.common.CalendarUnderLineTextField
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.typo

@Composable
internal fun DiaryOverviewInput(
    workLaborer: Int,
    workHour: Int,
    workArea: Int,
    onWorkLaborerInput: (Int) -> Unit,
    onWorkHourInput: (Int) -> Unit,
    onWorkAreaInput: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val overViewInfo = rememberOverviewInfo(
        workLaborer,
        workHour,
        workArea,
        onWorkLaborerInput,
        onWorkHourInput,
        onWorkAreaInput
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        for (info in overViewInfo) {
            OverviewItem(
                overviewName = info[0] as Int,
                overviewDigit = info[1] as Int,
                overviewValue = info[2] as Int,
                onOverviewValueChange = info[3] as (Int) -> Unit
            )
        }
    }
}

@Composable
private fun rememberOverviewInfo(
    workLaborer: Int,
    workHour: Int,
    workArea: Int,
    onWorkLaborerInput: (Int) -> Unit,
    onWorkHourInput: (Int) -> Unit,
    onWorkAreaInput: (Int) -> Unit
) =
    remember(workLaborer, workHour, workArea) {
        listOf(
            listOf(
                R.string.feature_main_calendar_add_diary_work_laborer,
                R.string.feature_main_calendar_add_diary_work_laborer_digit,
                workLaborer,
                onWorkLaborerInput
            ),
            listOf(
                R.string.feature_main_calendar_add_diary_work_hour,
                R.string.feature_main_calendar_add_diary_work_hour_digit,
                workHour,
                onWorkHourInput
            ),
            listOf(
                R.string.feature_main_calendar_add_diary_work_area,
                R.string.feature_main_calendar_add_diary_work_area_digit,
                workArea,
                onWorkAreaInput
            ),
        )
    }

@Composable
private fun OverviewItem(
    @StringRes overviewName: Int,
    @StringRes overviewDigit: Int,
    overviewValue: Int,
    onOverviewValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = Paddings.medium),
            text = stringResource(id = overviewName),
            style = MaterialTheme.typo.labelSB,
            textAlign = TextAlign.End
        )
        OverviewTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = Paddings.medium),
            value = overviewValue,
            onValueChange = onOverviewValueChange
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = overviewDigit),
            style = MaterialTheme.typo.labelSB,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun OverviewTextField(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarUnderLineTextField(
        modifier = modifier,
        value = value.toString(),
        onValueChange = {input ->
            onValueChange(input.toIntOrNull() ?: 0)
        },
        textAlign = TextAlign.Center,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Preview
@Composable
private fun OverviewInputPreview() {
    Surface {
        DiaryOverviewInput(
            workLaborer = 0,
            workHour = 0,
            workArea = 0,
            onWorkLaborerInput = { _ -> },
            onWorkHourInput = { _ -> },
            onWorkAreaInput = { _ -> }
        )
    }
}