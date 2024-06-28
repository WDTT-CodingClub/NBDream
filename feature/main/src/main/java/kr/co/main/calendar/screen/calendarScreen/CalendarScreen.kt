package kr.co.main.calendar.screen.calendarScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kr.co.main.R
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.type.CalendarTabType
import kr.co.main.model.calendar.type.CropModelColorType
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScreenModeType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.icon.dreamicon.Search
import kr.co.ui.icon.dreamicon.Spinner
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamDialog
import kr.co.ui.widget.DreamTopAppBar
import timber.log.Timber


@Composable
internal fun CalendarRoute(
    navController: NavController,
    navToMyPage: () -> Unit,
    navToAddSchedule: (Int?, Int?, Long?) -> Unit,
    navToAddDiary: (Int?, Int?, Long?) -> Unit,
    navToSearchDiary: (Int?) -> Unit,
    viewModel: CalendarScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Timber.d("state: $state")

    val reinitialize =
        navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(CalendarNavGraph.ARG_REINITIALIZE)

    LaunchedEffect(reinitialize) {
        Timber.d("reinitialize: $reinitialize")
        if (reinitialize == true) viewModel.reinitialize()

        navController.currentBackStackEntry?.savedStateHandle?.set(
            CalendarNavGraph.ARG_REINITIALIZE,
            false
        )
    }

    CalendarScreen(
        modifier = Modifier.fillMaxSize(),
        navToMyPage = navToMyPage,
        navToAddSchedule = navToAddSchedule,
        navToAddDiary = navToAddDiary,
        navToSearchDiary = navToSearchDiary,
        state = state,
        event = viewModel.event
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarScreen(
    navToMyPage: () -> Unit,
    navToAddSchedule: (Int?, Int?, Long?) -> Unit,
    navToAddDiary: (Int?, Int?, Long?) -> Unit,
    navToSearchDiary: (Int?) -> Unit,
    state: CalendarScreenViewModel.CalendarScreenState,
    event: CalendarScreenEvent,
    modifier: Modifier = Modifier,
) {
    Timber.d("state: $state")

    var showNavToMyPageDialog by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState {
        CalendarTabType.entries.size
    }
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colors.gray9,
        topBar = {
            CalendarScreenTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.large),
                pagerState = pagerState,
                navToAddSchedule = {
                    navToAddSchedule(
                        state.crop?.type?.nameId,
                        ScreenModeType.POST_MODE.id,
                        null
                    )
                },
                navToAddDiary = {
                    if (state.crop == null) {
                        showNavToMyPageDialog = true
                    } else {
                        navToAddDiary(
                            state.crop.type.nameId,
                            ScreenModeType.POST_MODE.id,
                            null
                        )
                    }
                },
                navToSearchDiary = {
                    navToSearchDiary(
                        state.crop?.type?.nameId
                    )
                },
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavToMyPageDialog(
                showDialog = showNavToMyPageDialog,
                onConfirm = {
                    showNavToMyPageDialog = false
                    navToMyPage()
                },
                onDismiss = {
                    showNavToMyPageDialog = false
                }
            )

            Column {
                CalendarInfoPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Paddings.large)
                        .background(MaterialTheme.colors.gray9),
                    userCrops = state.userCrops,
                    calendarYear = state.year,
                    calendarMonth = state.month,
                    calendarCrop = state.crop,
                    onSelectYear = event::onYearSelect,
                    onSelectMonth = event::onMonthSelect,
                    onSelectCrop = event::onCropSelect
                )

                HorizontalPager(
                    modifier = Modifier.background(MaterialTheme.colors.gray9),
                    state = pagerState
                ) {
                    when (pagerState.currentPage) {
                        CalendarTabType.SCHEDULE.pagerIndex ->
                            ScheduleTab(
                                calendarCrop = state.crop,
                                calendarYear = state.year,
                                calendarMonth = state.month,
                                selectedDate = state.selectedDate,
                                onDateSelect = event::onDateSelect,
                                farmWorks = state.farmWorks,
                                holidays = state.holidays,
                                allSchedules = state.allSchedules,
                                cropSchedules = state.cropSchedules,
                                onEditClick = { scheduleId ->
                                    navToAddSchedule(
                                        state.crop?.type?.nameId,
                                        ScreenModeType.EDIT_MODE.id,
                                        scheduleId
                                    )
                                }
                            )

                        CalendarTabType.DIARY.pagerIndex ->
                            DiaryTab(
                                calendarCrop = state.crop,
                                calendarYear = state.year,
                                calendarMonth = state.month,
                                selectedDate = state.selectedDate,
                                onDateSelect = event::onDateSelect,
                                holidays = state.holidays,
                                diaries = state.diaries,
                            )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarScreenTopAppBar(
    pagerState: PagerState,
    navToAddSchedule: () -> Unit,
    navToAddDiary: () -> Unit,
    navToSearchDiary: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    DreamTopAppBar(
        modifier = modifier,
        title = {
            CalendarScreenTopAppBarTitle(
                modifier = Modifier.wrapContentSize(),
                selectedTab = CalendarTabType.ofIndex(pagerState.currentPage),
                onSelectTab = { calendarTabType ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = calendarTabType.pagerIndex)
                    }
                }
            )
        },
        actions = {
            CalendarScreenTopAppBarActions(
                navToAddScreen = when (CalendarTabType.ofIndex(pagerState.currentPage)) {
                    CalendarTabType.SCHEDULE -> navToAddSchedule
                    CalendarTabType.DIARY -> navToAddDiary
                },
                navToSearchScreen = when (CalendarTabType.ofIndex(pagerState.currentPage)) {
                    CalendarTabType.SCHEDULE -> null
                    CalendarTabType.DIARY -> navToSearchDiary
                },
                modifier = Modifier
            )
        }
    )
}

@Composable
private fun CalendarScreenTopAppBarTitle(
    selectedTab: CalendarTabType,
    onSelectTab: (CalendarTabType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
    ) {
        CalendarTabType.entries.forEach {
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
    navToSearchScreen: (() -> Unit)?,
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
        navToSearchScreen?.let {
            IconButton(onClick = navToSearchScreen) {
                Icon(
                    modifier = Modifier,
                    imageVector = DreamIcon.Search,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun NavToMyPageDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(showDialog) {
        DreamDialog(
            header = stringResource(id = R.string.feature_main_calendar_nav_to_my_page_dialog_title),
            description = stringResource(id = R.string.feature_main_calendar_nav_to_my_page_dialog_description),
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun CalendarInfoPicker(
    userCrops: List<CropModel>,
    calendarYear: Int,
    calendarMonth: Int,
    calendarCrop: CropModel?,
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
        calendarCrop?.let {
            CalendarCropPicker(
                userCrops = userCrops,
                calendarCrop = it,
                onSelectCrop = onSelectCrop
            )
        }
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
    var expandDropDown by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = modifier
                .clickable {
                    expandDropDown = true
                },
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
                imageVector = DreamIcon.Spinner,
                contentDescription = ""
            )
        }
        CalendarYearMonthPickerDropDown(
            modifier = Modifier
                .height(CalendarDesignToken.YEAR_MONTH_PICKER_DROP_DOWN_HEIGHT.dp),
            expandDropDown = expandDropDown,
            calendarYear = calendarYear,
            calendarMonth = calendarMonth,
            onYearSelect = onSelectYear,
            onMonthSelect = onSelectMonth,
            onDismissDropDown = { expandDropDown = false },
        )
    }
}

@Composable
private fun CalendarYearMonthPickerDropDown(
    expandDropDown: Boolean,
    calendarYear: Int,
    calendarMonth: Int,
    onYearSelect: (Int) -> Unit,
    onMonthSelect: (Int) -> Unit,
    onDismissDropDown: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState(
        initial = CalendarDesignToken.YEAR_MONTH_PICKER_DROP_DOWN_ITEM_HEIGHT * ((calendarYear - 2000) * 12)
    )

    DropdownMenu(
        modifier = modifier,
        expanded = expandDropDown,
        onDismissRequest = onDismissDropDown,
        scrollState = scrollState
    ) {
        for (year in 2000..2050) {
            for (month in 1..12) {
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CalendarDesignToken.YEAR_MONTH_PICKER_DROP_DOWN_ITEM_HEIGHT.dp),
                            text = "${year}년 ${month}월",
                            style = MaterialTheme.typo.body1,
                            color =
                            if (year == calendarYear && month == calendarMonth) MaterialTheme.colors.primary
                            else MaterialTheme.colors.text2,
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        onYearSelect(year)
                        onMonthSelect(month)
                        onDismissDropDown()
                    }
                )
            }
        }
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
                .width(CalendarDesignToken.CALENDAR_CROP_PICKER_WIDTH.dp)
                .clickable {
                    showCropPickerDropDown = true
                },
            calendarCrop = calendarCrop
        )
        CalendarCropPickerDropDown(
            modifier = Modifier
                .background(Color.White)
                .width(CalendarDesignToken.CALENDAR_CROP_PICKER_WIDTH.dp),
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
                shape = RoundedCornerShape(CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp)
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
            CropModel(type = CropModelType.POTATO, color = CropModelColorType.POTATO)
        )
    }

    CalendarCropPicker(
        userCrops = listOf(
            CropModel(type = CropModelType.POTATO, color = CropModelColorType.POTATO),
            CropModel(
                type = CropModelType.SWEET_POTATO,
                color = CropModelColorType.SWEET_POTATO
            ),
            CropModel(type = CropModelType.APPLE, color = CropModelColorType.APPLE)
        ),
        calendarCrop = calendarCrop.value,
        onSelectCrop = {
            calendarCrop.value = it
        }
    )
}