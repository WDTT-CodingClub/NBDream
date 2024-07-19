package kr.co.ui.widget

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import kr.co.ui.theme.Shapes
import kr.co.ui.theme.colors
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    date: LocalDate? = LocalDate.now(),
    onClickCancel: () -> Unit,
    onClickConfirm: (yyyyMMdd: String) -> Unit
) {
    val initialDateMillis = date?.atStartOfDay(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
        ?: System.currentTimeMillis()
    val datePickerState = rememberDatePickerState(
        yearRange = IntRange(2000, 2050),
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = initialDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }
        }
    )
    val initialSelectedDate = remember { datePickerState.selectedDateMillis }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (initialSelectedDate != datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val formattedDate = formatter.format(Date(selectedDateMillis))

                onClickConfirm(formattedDate)
            }
        }
    }

    DatePickerDialog(
        onDismissRequest = { onClickCancel() },
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colors.white
        ),
        shape = Shapes.small,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colors.primary,
                selectedYearContainerColor = MaterialTheme.colors.primary,
                containerColor = MaterialTheme.colors.white,
                todayDateBorderColor = MaterialTheme.colors.primary,
                todayContentColor = MaterialTheme.colors.black,
                selectedDayContentColor = MaterialTheme.colors.black,
                dateTextFieldColors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                    focusedTextColor = MaterialTheme.colors.primary,
                    errorContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colors.primary,
                    unfocusedLabelColor = MaterialTheme.colors.primary,
                    disabledLabelColor = MaterialTheme.colors.primary,
                ),
            )
        )
    }
}