package kr.co.main.accountbook.main

import android.widget.Toast
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.model.DateRangeOption
import kr.co.main.accountbook.model.getDisplay
import kr.co.ui.theme.colors
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountBookCategoryBottomSheet(
    categories: List<AccountBookEntity.Category?>? = AccountBookEntity.Category.entries,
    onSelectedListener: (AccountBookEntity.Category) -> Unit,
    dismissBottomSheet: () -> Unit,
) {
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
            categories?.forEach { category ->
                val categoryName = category.getDisplay()
                val backgroundColor = Color.Transparent
                Text(
                    text = categoryName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (category != null) {
                                onSelectedListener(category)
                            }
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
internal fun AccountBookCalendarBottomSheet(
    selectedOption: DateRangeOption,
    onOptionSelected: (DateRangeOption) -> Unit,
    onSelectedListener: (String, String) -> Unit,
    dismissBottomSheet: () -> Unit,
) {
    var (startDate, endDate) = selectedOption.getDate()

    val sheetState = rememberModalBottomSheetState()
    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerType by remember { mutableStateOf("start") }

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
                DateRangeOption.entries.forEach { option ->
                    OptionBox(
                        option = option.label,
                        selectedOption = selectedOption.label,
                        onOptionSelected = { newOptionLabel ->
                            val newEnumOption =
                                DateRangeOption.entries.find { it.label == newOptionLabel }
                            newEnumOption?.let { onOptionSelected(it) }
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(2f)) {
                    DateRow(
                        date = startDate.format(DateTimeFormatter.ISO_DATE),
                        onClick = {
                            datePickerType = "start"
                            showDatePicker = true
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "ㅡ",
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
                Column(modifier = Modifier.weight(2f)) {
                    DateRow(
                        date = endDate.format(DateTimeFormatter.ISO_DATE),
                        onClick = {
                            datePickerType = "end"
                            showDatePicker = true
                        }
                    )
                }
            }
            Button(
                onClick = {
                    onSelectedListener(
                        startDate.format(DateTimeFormatter.ISO_DATE),
                        endDate.format(DateTimeFormatter.ISO_DATE)
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
            datePickerType = datePickerType,
            onClickCancel = {
                showDatePicker = false
            },
            onClickConfirm = { selectedDate ->
                val selectedLocalDate =
                    LocalDate.parse(selectedDate, DateTimeFormatter.BASIC_ISO_DATE)
                if (datePickerType == "start") {
                    startDate = selectedLocalDate
                } else {
                    endDate = selectedLocalDate
                }
                showDatePicker = false
            }
        )
    }
}

@Composable
private fun DateRow(
    date: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = date,
            modifier = Modifier
                .padding(start = 8.dp, end = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.grey6)
}

@Composable
private fun OptionBox(
    option: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val isSelected = option == selectedOption
    val borderColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.grey1
    val textColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.grey6

    Box(
        modifier = Modifier
            .height(32.dp)
            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(16.dp))
            .clickable(onClick = {
                onOptionSelected(option)
            }),
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
private fun CustomDatePickerDialog(
    datePickerType: String,
    onClickCancel: () -> Unit,
    onClickConfirm: (yyyyMMdd: String) -> Unit
) {
    val startDate = remember { mutableStateOf(LocalDate.now()) }
    val endDate = remember { mutableStateOf(LocalDate.now()) }

    val datePickerState = rememberDatePickerState(
        yearRange = IntRange(2000, 2050),
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }
        }
    )

    val initialSelectedDate = remember { datePickerState.selectedDateMillis }
    val context = LocalContext.current

    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (initialSelectedDate != datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                val date = Date(selectedDateMillis)
                val formatter = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
                val formattedDate = formatter.format(date)

                if (datePickerType == "start") {
                    val selectedStartDate =
                        LocalDate.parse(formattedDate, DateTimeFormatter.BASIC_ISO_DATE)
                    if (selectedStartDate.isAfter(endDate.value) || selectedStartDate == endDate.value) {
                        Toast.makeText(
                            context,
                            "시작일이 종료일보다 이후입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        onClickCancel()
                        return@LaunchedEffect
                    }
                    startDate.value = selectedStartDate
                } else {
                    val selectedEndDate =
                        LocalDate.parse(formattedDate, DateTimeFormatter.BASIC_ISO_DATE)
                    if (selectedEndDate.isBefore(startDate.value) || selectedEndDate == startDate.value) {
                        Toast.makeText(
                            context,
                            "종료일이 시작일보다 이전입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        onClickCancel()
                        return@LaunchedEffect
                    }
                    endDate.value = selectedEndDate
                }

                onClickConfirm(formattedDate)
            }
        }
    }

    DatePickerDialog(
        onDismissRequest = { onClickCancel() },
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
            selectedDayContentColor = MaterialTheme.colors.primary,
            selectedDayContainerColor = MaterialTheme.colors.primary,
            dayInSelectionRangeContentColor = MaterialTheme.colors.primary,
        ),
        shape = RoundedCornerShape(6.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}