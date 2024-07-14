package kr.co.main.calendar.screen.calendarScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.main.R
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.icon.dreamicon.Arrowright
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate

private const val MIN_YEAR = 2000
private const val MAX_YEAR = 2050


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarYearMonthBottomSheet(
    calendarYear: Int,
    calendarMonth: Int,
    onYearSelect: (Int) -> Unit,
    onMonthSelect: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedYear by remember { mutableIntStateOf(calendarYear) }
    var selectedMonth by remember { mutableIntStateOf(calendarMonth) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = rememberModalBottomSheetState(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            YearPicker(
                selectedYear = selectedYear,
                onYearClick = {
                    selectedYear = it
                }
            )
            Spacer(modifier = Modifier.height(Paddings.large))
            MonthPicker(
                selectedMonth = calendarMonth,
                onMonthClick = {
                    selectedMonth = it
                }
            )
            Spacer(modifier = Modifier.height(Paddings.large))
            Button(
                onClick = {
                    onYearSelect(selectedYear)
                    onMonthSelect(selectedMonth)
                    onDismissRequest()
                }
            ) {
                Text(
                    text = stringResource(id = kr.co.nbdream.core.ui.R.string.core_ui_dialog_confirm)
                )
            }
        }
    }
}

@Composable
private fun YearPicker(
    selectedYear: Int,
    onYearClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.clickable {
                if(selectedYear == MIN_YEAR)
                    Toast.makeText(
                        context,
                        context.getString(R.string.feature_main_calendar_year_month_picker_min_year_warning),
                        Toast.LENGTH_LONG
                    ).show()
                else onYearClick(selectedYear - 1)
            },
            imageVector = DreamIcon.Arrowleft,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(horizontal = Paddings.medium),
            text = selectedYear.toString(),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.text2
        )
        Icon(
            modifier = Modifier.clickable {
                if(selectedYear == MAX_YEAR)
                    Toast.makeText(
                        context,
                        context.getString(R.string.feature_main_calendar_year_month_picker_max_year_warning),
                        Toast.LENGTH_LONG
                    ).show()
                else onYearClick(selectedYear + 1)
            },
            imageVector = DreamIcon.Arrowright,
            contentDescription = ""
        )
    }
}

@Composable
private fun MonthPicker(
    selectedMonth: Int,
    onMonthClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(12) {
            val month = it + 1
            Box(
                modifier = Modifier
                    .padding(Paddings.medium)
                    .clip(CircleShape)
                    .background(
                        if (month == selectedMonth) MaterialTheme.colors.primary
                        else MaterialTheme.colors.gray9
                    )
                    .clickable {
                        onMonthClick(month)
                    }
            ) {
                Text(
                    text = month.toString(),
                    style = MaterialTheme.typo.body1,
                    color =
                    if (month == selectedMonth) MaterialTheme.colors.white
                    else MaterialTheme.colors.text2
                )
            }
        }
    }
}