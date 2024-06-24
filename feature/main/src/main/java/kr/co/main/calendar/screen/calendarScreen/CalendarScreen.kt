package kr.co.main.calendar.screen.calendarScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.model.calendar.CropModel
import kr.co.main.calendar.ui.screen.calendarScreen.diaryTab.DiaryTab
import kr.co.main.calendar.ui.screen.calendarScreen.scheduleTab.ScheduleTab
import kr.co.main.model.calendar.type.CropModelColorType
import kr.co.main.model.calendar.type.CropModelType
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Bell
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.icon.dreamicon.Spinner
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

@Composable
internal fun CalendarRoute(
    navToAddSchedule: (Int) -> Unit,
    navToAddDiary: (Int) -> Unit,
    navToSearchDiary: (Int, Int, Int) -> Unit,
    navToNotification: () -> Unit,
    viewModel: CalendarScreenViewModel = hiltViewModel()
) {
    CalendarScreen(
        modifier = Modifier.fillMaxSize(),
        navToAddSchedule = navToAddSchedule,
        navToAddDiary = navToAddDiary,
        navToSearchDiary = navToSearchDiary,
        navToNotification = navToNotification,
        state = viewModel.state.collectAsState(),
        event = viewModel.event
    )
}

@Composable
private fun CalendarScreen(
    navToAddSchedule: (Int) -> Unit,
    navToAddDiary: (Int) -> Unit,
    navToSearchDiary: (Int, Int, Int) -> Unit,
    navToNotification: () -> Unit,
    state: State<CalendarScreenViewModel.CalendarScreenState>,
    event: CalendarScreenEvent,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colors.gray9,
        topBar = {
            CalendarScreenTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.large),
                selectedTab = state.value.selectedTab,
                onSelectTab = event::onSelectTab,
                navToAddSchedule = { navToAddSchedule(state.value.calendarCrop!!.type.nameId) },
                navToAddDiary = { navToAddDiary(state.value.calendarCrop!!.type.nameId) },
                navToNotification = navToNotification
            )
        }
    ) { innerPadding ->
        val scaffoldScrollState = rememberScrollState()

        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(
                    state = scaffoldScrollState
                )
        ) {
            if (state.value.calendarCrop != null) {
                Column {
                    CalendarInfoPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Paddings.large)
                            .background(MaterialTheme.colors.gray9),
                        userCrops = state.value.userCrops,
                        calendarYear = state.value.calendarYear,
                        calendarMonth = state.value.calendarMonth,
                        calendarCrop = state.value.calendarCrop!!,
                        onSelectYear = event::onSelectYear,
                        onSelectMonth = event::onSelectMonth,
                        onSelectCrop = event::onSelectCrop
                    )
                    when (state.value.selectedTab) {
                        CalendarScreenViewModel.CalendarScreenState.CalendarTab.SCHEDULE ->
                            ScheduleTab(
                                calendarCrop = state.value.calendarCrop,
                                calendarYear = state.value.calendarYear,
                                calendarMonth = state.value.calendarMonth,
                                navToSearchDiary = navToSearchDiary
                            )

                        CalendarScreenViewModel.CalendarScreenState.CalendarTab.DIARY ->
                            DiaryTab(
                                calendarCrop = state.value.calendarCrop,
                                calendarYear = state.value.calendarYear,
                                calendarMonth = state.value.calendarMonth,
                                navToSearchDiary = {
                                    navToSearchDiary(
                                        state.value.calendarCrop!!.type.nameId,
                                        state.value.calendarYear,
                                        state.value.calendarMonth
                                    )
                                }
                            )
                    }
                }
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
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
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
    val textWidth = measureTextWidth(
        text = LocalContext.current.getString(titleId),
        style = MaterialTheme.typo.h2
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.width(textWidth),
            text = stringResource(id = titleId),
            style = MaterialTheme.typo.h2,
            color = if (isSelected) MaterialTheme.colors.text1 else MaterialTheme.colors.text2,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier.width(textWidth),
            thickness = 2.dp,
            color = if (isSelected) Color.Black else Color.Transparent
        )
    }
}

@Composable
private fun measureTextWidth(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    return with(LocalDensity.current) { widthInPixels.toDp() }
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
    userCrops: List<CropModel>,
    calendarYear: Int,
    calendarMonth: Int,
    calendarCrop: CropModel,
    onSelectYear: (Int) -> Unit,
    onSelectMonth: (Int) -> Unit,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarYearMonthPicker(
            calendarYear = calendarYear,
            calendarMonth = calendarMonth,
            onSelectYear = onSelectYear,
            onSelectMonth = onSelectMonth
        )
        CalendarCropPicker(
            userCrops = userCrops,
            calendarCrop = calendarCrop,
            onSelectCrop = onSelectCrop
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
            modifier = Modifier.padding(end = Paddings.medium),
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
    userCrops: List<CropModel>,
    calendarCrop: CropModel,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCropPickerDropDown by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        CalendarCropPickerButton(
            modifier = Modifier
                .width(kr.co.main.calendar.CalendarDesignToken.CALENDAR_CROP_PICKER_WIDTH.dp)
                .clickable {
                    showCropPickerDropDown = true
                },
            calendarCrop = calendarCrop
        )
        CalendarCropPickerDropDown(
            modifier = Modifier
                .background(Color.White)
                .width(kr.co.main.calendar.CalendarDesignToken.CALENDAR_CROP_PICKER_WIDTH.dp),
            expanded = showCropPickerDropDown,
            onDismissRequest = { showCropPickerDropDown = false },
            userCrops = userCrops,
            calendarCrop = calendarCrop,
            onSelectCrop = onSelectCrop
        )
    }
}

@Composable
private fun CalendarCropPickerButton(
    calendarCrop: CropModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.text2,
                shape = RoundedCornerShape(kr.co.main.calendar.CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = Paddings.medium)
                .padding(start = Paddings.large)
                .align(Alignment.CenterStart),
            text = stringResource(id = calendarCrop.type.nameId),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.text2
        )
        Icon(
            modifier = Modifier
                .padding(vertical = Paddings.medium)
                .padding(end = Paddings.large)
                .align(Alignment.CenterEnd),
            imageVector = DreamIcon.Spinner,
            contentDescription = ""
        )
    }
}

@Composable
private fun CalendarCropPickerDropDown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    userCrops: List<CropModel>,
    calendarCrop: CropModel,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        for (crop in userCrops) {
            CalendarCropPickerDropDownItem(
                modifier = Modifier
                    .padding(Paddings.medium)
                    .clickable {
                        onSelectCrop(crop)
                        onDismissRequest()
                    },
                crop = crop,
                isSelected = (crop == calendarCrop)
            )
        }
    }
}

@Composable
private fun CalendarCropPickerDropDownItem(
    crop: CropModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = crop.type.nameId),
        style = MaterialTheme.typo.body1,
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.text2
    )
}

@Preview(showBackground = true)
@Composable
private fun CalendarCropPickerPreview() {
    val calendarCrop = remember {
        mutableStateOf(
            CropModel(type = CropModelType.POTATO, color = CropModelColorType.POTATO )
        )
    }

    CalendarCropPicker(
        userCrops = listOf(
            CropModel(type = CropModelType.POTATO, color = CropModelColorType.POTATO ),
            CropModel(type = CropModelType.SWEET_POTATO, color = CropModelColorType.SWEET_POTATO ),
            CropModel(type = CropModelType.APPLE, color = CropModelColorType.APPLE )
        ),
        calendarCrop = calendarCrop.value,
        onSelectCrop = {
            calendarCrop.value = it
        }
    )
}