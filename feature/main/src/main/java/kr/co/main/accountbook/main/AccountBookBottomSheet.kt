package kr.co.main.accountbook.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.ui.theme.colors
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountBookCategoryBottomSheet(
    onSelectedListener: (String) -> Unit,
    categories: List<String>,
    dismissBottomSheet: () -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = dismissBottomSheet,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentColor = Color.White,
        dragHandle = null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "카테고리를 선택하세요",
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 12.dp),
                color = Color.Black
            )
            categories.forEach { category ->
                val backgroundColor = if (category == selectedCategory) MaterialTheme.colors.primary else Color.Transparent
                Text(
                    text = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedCategory = category
                            onSelectedListener(category)
                        }
                        .background(color = backgroundColor)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBookCalendarBottomSheet(
    onSelectedListener: (String, String) -> Unit,
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>,
    dismissBottomSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val options = listOf("당일", "1주일", "1개월", "3개월", "6개월")
    var selectedOption by remember { mutableStateOf("1개월") }

    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerType by remember { mutableStateOf("start") }
    val datePickerState = remember { mutableStateOf<CustomDatePickerDialogState?>(null) }

    ModalBottomSheet(
        onDismissRequest = dismissBottomSheet,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentColor = Color.White,
        dragHandle = null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "조회기간",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                options.forEach { option ->
                    OptionBox(
                        option = option,
                        selectedOption = selectedOption
                    ) {
                        updateDatesForOption(option, startDate, endDate)
                        selectedOption = option
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RowUnderline(
                    date = startDate.value.format(DateTimeFormatter.ISO_DATE),
                    onDateClicked = {
                        datePickerType = "start"
                        showDatePicker = true
                    },
                    selectedDate = startDate.value.format(DateTimeFormatter.ISO_DATE)
                )
                Text(
                    text = "ㅡ",
                    modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                RowUnderline(
                    date = endDate.value.format(DateTimeFormatter.ISO_DATE),
                    onDateClicked = {
                        datePickerType = "end"
                        showDatePicker = true
                    },
                    selectedDate = endDate.value.format(DateTimeFormatter.ISO_DATE)
                )
            }
            Button(
                onClick = {
                    onSelectedListener(
                        startDate.value.format(DateTimeFormatter.ISO_DATE),
                        endDate.value.format(DateTimeFormatter.ISO_DATE)
                    )
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "조회",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    if (showDatePicker) {
        CustomDatePickerDialog(
            selectedDate = if (datePickerType == "start") startDate.value.format(DateTimeFormatter.BASIC_ISO_DATE) else endDate.value.format(
                DateTimeFormatter.BASIC_ISO_DATE),
            onClickCancel = {
                showDatePicker = false
            },
            onClickConfirm = { selectedDate ->
                if (datePickerType == "start") {
                    startDate.value = LocalDate.parse(selectedDate, DateTimeFormatter.BASIC_ISO_DATE)
                } else {
                    endDate.value = LocalDate.parse(selectedDate, DateTimeFormatter.BASIC_ISO_DATE)
                }
                showDatePicker = false
            }
        )
    }
}

private fun updateDatesForOption(
    option: String,
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>
) {
    val today = LocalDate.now()
    startDate.value = when (option) {
        "당일" -> today
        "1주일" -> today.minusWeeks(1)
        "1개월" -> today.minusMonths(1)
        "3개월" -> today.minusMonths(3)
        "6개월" -> today.minusMonths(6)
        else -> startDate.value
    }
    endDate.value = today
}

@Composable
fun RowUnderline(
    date: String,
    onDateClicked: () -> Unit,
    selectedDate: String
) {
    val underlineColor = MaterialTheme.colors.grey6
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onDateClicked)
            .drawBehind {
                val strokeWidth = 2f
                val y = size.height - strokeWidth / 2 + 4.dp.toPx()
                drawLine(
                    color = underlineColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Text(
            text = date,
            modifier = Modifier.padding(start = 8.dp, end = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.width(32.dp))
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
private fun OptionBox(
    option: String,
    selectedOption: String,
    onClick: () -> Unit
) {
    val isSelected = option == selectedOption
    val borderColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.grey1
    val textColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.grey6

    Box(
        modifier = Modifier
            .height(32.dp)
            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = option,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    selectedDate: String?,
    onClickCancel: () -> Unit,
    onClickConfirm: (yyyyMMdd: String) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { onClickCancel() },
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        val datePickerState = rememberDatePickerState(
            yearRange = 2014..2024,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate?.let {
                val formatter = SimpleDateFormat("yyyyMMdd", Locale.KOREA).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
                formatter.parse(it)?.time
                    ?: System.currentTimeMillis()
            } ?: System.currentTimeMillis(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return true
                }
            })

        DatePicker(
            state = datePickerState,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {
                onClickCancel()
            }) {
                Text(text = "취소")
            }

            Spacer(modifier = Modifier.width(5.dp))

            Button(onClick = {
                datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                    val yyyyMMdd = SimpleDateFormat(
                        "yyyyMMdd",
                        Locale.KOREA
                    ).format(Date(selectedDateMillis))

                    onClickConfirm(yyyyMMdd)
                }
            }) {
                Text(text = "확인")
            }
        }
    }
}

data class CustomDatePickerDialogState(
    var selectedDate: String? = null,
    var isShowDialog: Boolean = false,
    val onClickConfirm: (yyyyMMdd: String) -> Unit = {},
    val onClickCancel: () -> Unit = {}
)