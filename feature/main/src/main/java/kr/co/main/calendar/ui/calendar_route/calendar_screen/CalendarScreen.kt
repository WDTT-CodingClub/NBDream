package kr.co.main.calendar.ui.calendar_route.calendar_screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.calendar.common.CalendarDesignToken
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.ui.calendar_route.calendar_screen.diary_tab.DiaryTab
import kr.co.main.calendar.ui.calendar_route.calendar_screen.schedule_tab.ScheduleTab
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.icon.dreamicon.Spinner
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun CalendarScreen(
    navToAddSchedule: () -> Unit,
    navToAddDiary: () -> Unit,
    navToNotification: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colors.gray9,
        topBar = {
            CalendarScreenTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                selectedTab = state.value.selectedTab,
                onSelectTab = event::onSelectTab,
                navToAddSchedule = navToAddSchedule,
                navToAddDiary = navToAddDiary,
                navToNotification = navToNotification
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {

            when (state.value.selectedTab) {
                CalendarScreenViewModel.CalendarScreenState.CalendarTab.SCHEDULE -> ScheduleTab()
                CalendarScreenViewModel.CalendarScreenState.CalendarTab.DIARY -> DiaryTab()
            }
        }
    }
}

@Composable
private fun CalendarScreenTopAppBar(
    selectedTab: CalendarScreenViewModel.CalendarScreenState.CalendarTab,
    onSelectTab: (CalendarScreenViewModel.CalendarScreenState.CalendarTab) -> Unit,
    navToAddSchedule: () -> Unit,
    navToAddDiary: () -> Unit,
    navToNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    DreamTopAppBar(
        modifier = modifier,
        title = {
            CalendarScreenTopAppBarTitle(
                modifier = Modifier.wrapContentSize(),
                selectedTab = selectedTab,
                onSelectTab = onSelectTab
            )
        },
        actions = {
            CalendarScreenTopAppBarActions(
                navToAddScreen = when (selectedTab) {
                    CalendarScreenViewModel.CalendarScreenState.CalendarTab.SCHEDULE -> navToAddSchedule
                    CalendarScreenViewModel.CalendarScreenState.CalendarTab.DIARY -> navToAddDiary
                },
                navToNotification = navToNotification,
                modifier = Modifier
            )
        }
    )
}

@Composable
private fun CalendarScreenTopAppBarTitle(
    selectedTab: CalendarScreenViewModel.CalendarScreenState.CalendarTab,
    onSelectTab: (CalendarScreenViewModel.CalendarScreenState.CalendarTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
    ) {
        CalendarScreenViewModel.CalendarScreenState.CalendarTab.entries.forEach {
            CalendarScreenTopAppBarTitleItem(
                modifier = Modifier.clickable {
                    onSelectTab(it)
                },
                titleId = it.titleId,
                isSelected = (it == selectedTab)
            )
        }
    }
}

@Composable
private fun CalendarScreenTopAppBarTitleItem(
    @StringRes titleId: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val textWidth = remember { mutableStateOf(0.dp) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .onGloballyPositioned {
                    textWidth.value = it.size.width.dp
                },
            text = stringResource(id = titleId),
            style = MaterialTheme.typo.h2,
            color = if (isSelected) MaterialTheme.colors.text1 else MaterialTheme.colors.text2,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier.width(textWidth.value),
            thickness = 2.dp,
            color = if (isSelected) Color.Black else Color.Transparent
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenTopAppBarPreview() {
    val selectedTab = remember {
        mutableStateOf(CalendarScreenViewModel.CalendarScreenState.CalendarTab.SCHEDULE)
    }
    CalendarScreenTopAppBar(
        selectedTab = selectedTab.value,
        onSelectTab = { selectedTab.value = it },
        navToAddSchedule = {},
        navToAddDiary = {},
        navToNotification = {}
    )
}

@Composable
private fun CalendarScreenTopAppBarActions(
    navToAddScreen: () -> Unit,
    navToNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconButton(onClick = navToAddScreen) {
            Icon(
                modifier = Modifier,
                imageVector = DreamIcon.Edit,
                contentDescription = ""
            )
        }
        IconButton(onClick = navToNotification) {
            Icon(
                modifier = Modifier,
                imageVector = DreamIcon.Bell,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun CalendarInfoPicker(
    calendarYear: Int,
    calendarMonth: Int,
    calendarCrop: CropModel,
    onSelectYear: (Int) -> Unit,
    onSelectMonth: (Int) -> Unit,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        CalendarYearMonthPicker(
            calendarYear = calendarYear,
            calendarMonth = calendarMonth,
            onSelectYear = onSelectYear,
            onSelectMonth = onSelectMonth
        )
    }
}

@Composable
private fun CalendarYearMonthPicker(
    calendarYear: Int,
    calendarMonth: Int,
    onSelectYear: (Int) -> Unit,
    onSelectMonth: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                id = R.string.feature_main_calendar_year_month,
                calendarYear,
                calendarMonth
            ),
            style = MaterialTheme.typo.h4
        )
        Icon(
            modifier = Modifier.clickable {
                // TODO Date Picker Dialog
            },
            imageVector = DreamIcon.Spinner,
            contentDescription = ""
        )
    }
}

@Composable
private fun CalendarCropPicker(
    selectedCrop: CropModel,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCropPickerSpinner by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp))
                .clickable {
                    showCropPickerSpinner = true
                }
        )
        Row(
            modifier = Modifier.clickable {
                showCropPickerSpinner = true
            }
        ) {
            Text(
                text = stringResource(id = selectedCrop.nameId),
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.text2
            )
            Icon(
                imageVector = DreamIcon.Spinner,
                contentDescription = ""
            )
        }
    }
}

/*
@Composable
private fun CalendarTitleSpinner(
    crop: CropModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(
                id = R.string.feature_main_calendar_title,
                stringResource(id = crop.nameId)
            ),
            style = MaterialTheme.typo.headerB
        )
        Icon(
            modifier = Modifier.padding(start = Paddings.medium),
            imageVector = DreamIcon.Spinner,
            contentDescription = ""
        )
    }
}

@Composable
private fun CalendarTitleDropDownMenu(
    expandSpinner: Boolean,
    userCrops: List<CropModel>,
    selectedCrop: CropModel,
    onSelectCrop: (CropModel) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expandSpinner,
        onDismissRequest = onDismiss
    ) {
        for (crop in userCrops) {
            CalendarTitleDropDownMenuItem(
                crop = crop,
                isSelected = (crop == selectedCrop),
                onClick = {
                    onSelectCrop(crop)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
private fun CalendarTitleDropDownMenuItem(
    crop: CropModel,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier
            .background(
                if (isSelected) MaterialTheme.colors.green1
                else Color.Transparent
            ),
        text = {
            Text(
                text = stringResource(id = crop.nameId),
                style = MaterialTheme.typo.bodyM,
            )
        },
        onClick = onClick
    )
}

*/