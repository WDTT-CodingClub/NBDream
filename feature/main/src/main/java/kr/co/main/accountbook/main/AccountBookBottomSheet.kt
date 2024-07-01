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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.co.domain.entity.AccountBookEntity
import kr.co.main.accountbook.model.CustomDatePickerDialog
import kr.co.main.accountbook.model.DatePickerType
import kr.co.main.accountbook.model.DateRangeOption
import kr.co.main.accountbook.model.getDisplay
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.DatePicker
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountBookCategoryBottomSheet(
    categories: List<AccountBookEntity.Category?>? = null,
    onSelectedListener: (AccountBookEntity.Category?) -> Unit,
    dismissBottomSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val items = categories ?: AccountBookEntity.Category.entries

    ModalBottomSheet(
        onDismissRequest = dismissBottomSheet,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentColor = MaterialTheme.colors.white,
        dragHandle = null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.white)
                .padding(horizontal = Paddings.xlarge, vertical = Paddings.xlarge)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "카테고리",
                style = MaterialTheme.typo.h4,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = Paddings.large),
                color = MaterialTheme.colors.gray1
            )
            if (categories != null) {
                Text(
                    text = "전체",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectedListener(null)
                            dismissBottomSheet()
                        }
                        .padding(vertical = Paddings.xlarge),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.gray1

                )
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.gray8)
            }
            items.forEach { category ->
                Text(
                    text = category.getDisplay(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectedListener(category)
                            dismissBottomSheet()
                        }
                        .padding(vertical = Paddings.xlarge),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.gray1,
                )
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colors.gray8)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountBookCalendarBottomSheet(
    startDate: LocalDate? = null,
    endDate: LocalDate? = null,
    selectedOption: DateRangeOption,
    onSelectedListener: (String, String, DateRangeOption) -> Unit,
    dismissBottomSheet: () -> Unit,
) {
    var currentStartDate by remember { mutableStateOf(startDate ?: selectedOption.getDate().first) }
    var currentEndDate by remember { mutableStateOf(endDate ?: selectedOption.getDate().second) }
    var tempSelectedOption by remember { mutableStateOf(selectedOption) }

    val sheetState = rememberModalBottomSheetState()
    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerType by remember { mutableStateOf(DatePickerType.START) }

    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = dismissBottomSheet,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentColor = MaterialTheme.colors.white,
        dragHandle = null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.white)
                .padding(Paddings.xlarge)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "조회기간",
                color = MaterialTheme.colors.gray1,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.xlarge),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DateRangeOption.entries
                    .filter { it != DateRangeOption.OTHER }
                    .forEach { option ->
                        OptionBox(
                            option = option.label,
                            selectedOption = tempSelectedOption.label,
                            onOptionSelected = { newOptionLabel ->
                                DateRangeOption.entries.find { it.label == newOptionLabel }?.let {
                                    tempSelectedOption = it
                                    if (it != DateRangeOption.OTHER) {
                                        val newDates = it.getDate()
                                        currentStartDate = newDates.first
                                        currentEndDate = newDates.second
                                    }
                                }
                            }
                        )
                    }
            }
            DateSelectionRow(
                currentStartDate = currentStartDate,
                currentEndDate = currentEndDate,
                onStartDateClick = {
                    datePickerType = DatePickerType.START
                    showDatePicker = true
                },
                onEndDateClick = {
                    datePickerType = DatePickerType.END
                    showDatePicker = true
                }
            )
            ConfirmButton(
                onClick = {
                    onSelectedListener(
                        currentStartDate.format(DateTimeFormatter.ISO_DATE),
                        currentEndDate.format(DateTimeFormatter.ISO_DATE),
                        tempSelectedOption
                    )
                }
            )
        }
    }
    if (showDatePicker) {
        CustomDatePickerDialog(
            date = if (datePickerType == DatePickerType.START) currentStartDate else currentEndDate,
            onClickCancel = { showDatePicker = false },
            onClickConfirm = { selectedDate ->
                val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
                val selectedLocalDate = LocalDate.parse(selectedDate, formatter)
                if (datePickerType == DatePickerType.START) {
                    if (selectedLocalDate.isAfter(currentEndDate) || selectedLocalDate == currentEndDate) {
                        Toast.makeText(
                            context,
                            "시작일이 종료일보다 이후입니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        currentStartDate = selectedLocalDate
                    }
                } else {
                    if (selectedLocalDate.isBefore(currentStartDate) || selectedLocalDate == currentStartDate) {
                        Toast.makeText(
                            context,
                            "종료일이 시작일보다 이전입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        currentEndDate = selectedLocalDate
                    }
                }
                tempSelectedOption = DateRangeOption.OTHER
                showDatePicker = false
            }
        )
    }
}

@Composable
private fun DateSelectionRow(
    currentStartDate: LocalDate,
    currentEndDate: LocalDate,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.xlarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(2f)) {
            DateRow(
                date = currentStartDate.format(DateTimeFormatter.ISO_DATE),
                onClick = onStartDateClick
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.medium)
                .weight(1f)
        ) {
            Text(
                text = "ㅡ",
                modifier = Modifier.padding(horizontal = Paddings.medium),
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
        }
        Column(modifier = Modifier.weight(2f)) {
            DateRow(
                date = currentEndDate.format(DateTimeFormatter.ISO_DATE),
                onClick = onEndDateClick
            )
        }
    }
}

@Composable
private fun ConfirmButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = Paddings.xlarge)
            .fillMaxWidth()
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = "조회",
            color = MaterialTheme.colors.white,
            style = MaterialTheme.typography.bodyMedium
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
            .padding(vertical = Paddings.medium)
            .fillMaxWidth()
    ) {
        Text(
            text = date,
            modifier = Modifier
                .padding(start = Paddings.medium, end = Paddings.small),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Icon(
            imageVector = DreamIcon.DatePicker,
            contentDescription = null,
            tint = MaterialTheme.colors.gray4,
            modifier = Modifier.padding(end = Paddings.medium)
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