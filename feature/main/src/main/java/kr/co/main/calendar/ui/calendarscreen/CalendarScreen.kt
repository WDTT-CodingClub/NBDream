package kr.co.main.calendar.ui.calendarscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.co.main.calendar.ui.calendarscreen.adddiaryscreen.AddDiaryScreen
import kr.co.main.calendar.ui.calendarscreen.addschedulescreen.AddScheduleScreen
import kr.co.main.calendar.ui.calendartab.diarytab.DiaryTab
import kr.co.main.calendar.ui.calendartab.scheduletab.ScheduleTab
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamTopAppBar

internal data class CalendarRouteNavState(
    val navController: NavHostController,
    val startDestination: String,
    val navActions: CalendarRouteNavActions
)

@Composable
internal fun CalendarRoute(
    calendarRouteNavState: CalendarRouteNavState =
        rememberCalendarRouteNavState()
) {
    NavHost(
        navController = calendarRouteNavState.navController,
        startDestination = calendarRouteNavState.startDestination
    ) {
        composable(
            route = CalendarRouteNavDest.CALENDAR_SCREEN.route
        ) {
            CalendarScreen(
                modifier = Modifier.fillMaxSize(),
                navToAddSchedule = calendarRouteNavState.navActions::navigateToAddScheduleScreen,
                navToAddDiary = calendarRouteNavState.navActions::navigateToAddDiaryScreen
            )
        }
        composable(
            route = CalendarRouteNavDest.ADD_SCHEDULE_SCREEN.route
        ) {
            AddScheduleScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(
            route = CalendarRouteNavDest.ADD_DIARY_SCREEN.route
        ) {
            AddDiaryScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
internal fun CalendarScreen(
    navToAddSchedule: () -> Unit,
    navToAddDiary: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event

    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarScreenTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                selectedTab = state.value.selectedTab,
                onSelectTab = event::onSelectTab,
                onNavToAddSchedule = navToAddSchedule,
                onNavToAddDiary = navToAddDiary
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (state.value.selectedTab) {
                CalendarScreenViewModel.CalendarScreenState.CalendarTab.SCHEDULE ->
                    ScheduleTab()

                CalendarScreenViewModel.CalendarScreenState.CalendarTab.DIARY ->
                    DiaryTab()
            }
        }
    }
}

@Composable
private fun rememberCalendarRouteNavState(
    navController: NavHostController = rememberNavController(),
    startDestination: String = CalendarRouteNavDest.CALENDAR_SCREEN.route,
    navActions: CalendarRouteNavActions = remember(navController) {
        CalendarRouteNavActions(navController)
    }
) = remember(navController, startDestination, navActions) {
    CalendarRouteNavState(navController, startDestination, navActions)
}

@Composable
private fun CalendarScreenTopAppBar(
    selectedTab: CalendarScreenViewModel.CalendarScreenState.CalendarTab,
    onSelectTab: (CalendarScreenViewModel.CalendarScreenState.CalendarTab) -> Unit,
    onNavToAddSchedule: () -> Unit,
    onNavToAddDiary: () -> Unit,
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
                modifier = Modifier
                    .clickable {
                        when (selectedTab) {
                            CalendarScreenViewModel.CalendarScreenState.CalendarTab.SCHEDULE -> onNavToAddSchedule()
                            CalendarScreenViewModel.CalendarScreenState.CalendarTab.DIARY -> onNavToAddDiary()
                        }
                    }
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
        onNavToAddSchedule = {},
        onNavToAddDiary = {}
    )
}

@Composable
private fun CalendarScreenTopAppBarActions(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier,
        imageVector = DreamIcon.Edit,
        contentDescription = ""
    )
}

/*
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val calendarScreenState by viewModel.state.collectAsState()
    val calendarScreenInput = viewModel.input

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CalendarTopBar(
                userCrops = calendarScreenState.userCrops,
                selectedCrop = calendarScreenState.selectedCrop,
                onSelectCrop = calendarScreenInput::onSelectCrop
            )
        },
        floatingActionButton = {
            CalendarFab(
                onAddScheduleClick = calendarScreenInput::onAddScheduleClick,
                onAddDiaryClick = calendarScreenInput::onAddDiaryClick
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CalendarYearMonth(
                    year = calendarScreenState.year,
                    month = calendarScreenState.month,
                    onMonthSelect = { month: Int ->
                        calendarScreenInput.onSelectMonth(month)
                    }
                )
                CalendarHorizontalDivider()

                calendarScreenState.selectedCrop?.let {
                    FarmWorkCalendar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Paddings.xlarge),
                        farmWorks = calendarScreenState.farmWorks
                    )
                    CalendarHorizontalDivider()

                    MainCalendar(
                        year = calendarScreenState.year,
                        month = calendarScreenState.month,
                        crop = calendarScreenState.selectedCrop!!,
                        holidays = calendarScreenState.holidays,
                        schedules = calendarScreenState.schedules,
                        diaries = calendarScreenState.diaries
                    )

                    DiaryList(diaries = calendarScreenState.diaries)
                }
            }
        }
    }
}

@Composable
private fun CalendarTopBar(
    userCrops: List<CropModel>,
    selectedCrop: CropModel?,
    onSelectCrop: (CropModel) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.medium)
    ) {
        CalendarTitle(
            modifier = Modifier.align(Alignment.Center),
            userCrops = userCrops,
            selectedCrop = selectedCrop,
            onSelectCrop = onSelectCrop
        )
    }
}

@Composable
private fun CalendarTitle(
    userCrops: List<CropModel>,
    selectedCrop: CropModel?,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandSpinner by remember { mutableStateOf(false) }

    if (selectedCrop == null) {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.feature_main_calendar_no_title),
            style = MaterialTheme.typo.headerB
        )
    } else {
        Column(
            modifier = modifier
        ) {
            CalendarTitleSpinner(
                crop = selectedCrop,
                onClick = { expandSpinner = true }
            )
            CalendarTitleDropDownMenu(
                expandSpinner = expandSpinner,
                userCrops = userCrops,
                selectedCrop = selectedCrop,
                onSelectCrop = onSelectCrop,
                onDismiss = { expandSpinner = false }
            )
        }
    }
}

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


//TODO 캘린더 항목 추가 플로팅 액션 버튼
@Composable
private fun CalendarFab(
    onAddScheduleClick: () -> Unit,
    onAddDiaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showChildFab by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        AnimatedVisibility(visible = showChildFab) {
            Column(modifier = Modifier) {
                AddDiaryFab(
                    modifier = Modifier.padding(bottom = Paddings.medium),
                    onAddDiaryClick = onAddDiaryClick,
                )
                AddScheduleFab(
                    modifier = Modifier.padding(bottom = Paddings.medium),
                    onAddScheduleClick = onAddScheduleClick
                )
            }
        }

        CalendarBaseFab(
            modifier = Modifier,
            imageVector = DreamIcon.Add,
            onClick = { showChildFab = !showChildFab }
        )
    }
}

@Composable
private fun AddScheduleFab(
    onAddScheduleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarBaseFab(
        modifier = modifier,
        imageVector = DreamIcon.Edit, //TODO 일정 추가 아이콘으로 변경
        onClick = onAddScheduleClick,
        containerColor = Color.White,
        contentColor = Color.Gray
    )
}

@Composable
private fun AddDiaryFab(
    onAddDiaryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarBaseFab(
        modifier = modifier,
        imageVector = DreamIcon.Edit, //TODO 영농일지 추가 아이콘으로 변경
        onClick = onAddDiaryClick,
        containerColor = Color.White,
        contentColor = Color.Gray
    )
}

@Composable
private fun CalendarYearMonth(
    year: Int,
    month: Int,
    onMonthSelect: (month: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onMonthSelect(month - 1) },
            imageVector = DreamIcon.ArrowLeft,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.feature_main_calendar_year_month, year, month),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onMonthSelect(month + 1) },
            imageVector = DreamIcon.ArrowLeft,
            contentDescription = ""
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DiaryList(
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.LightGray
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(Paddings.xlarge),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium)
        ) {
            items(diaries, key = { it.id }) {
                DiaryItem(diary = it)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DiaryItem(
    diary: DiaryModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        CalendarContentWrapper(
            modifier = Modifier.padding(Paddings.xlarge),
            calendarContent = CalendarContent.create(diary)
        )
    }
}

@Preview
@Composable
private fun CalendarTopBarPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        CalendarTopBar(
            userCrops = listOf(
                CropModel.POTATO,
                CropModel.SWEET_POTATO,
                CropModel.TOMATO
            ).sortedBy { it.ranking },
            selectedCrop = CropModel.POTATO
        )
    }
}

@Preview
@Composable
private fun CalendarYearMonthPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        CalendarYearMonth(
            year = 2024,
            month = 6,
            onMonthSelect = { _ -> }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DiaryListPreview(
    @PreviewParameter(FakeDiaryModelProvider::class) diary: DiaryModel
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        DiaryList(
            diaries = listOf(
                diary.copy(id = "1"),
                diary.copy(id = "2"),
                diary.copy(id = "3"),
                diary.copy(id = "4"),
                diary.copy(id = "5")
            )
        )
    }
}

@Preview
@Composable
private fun CalendarFabPreview() {
    CalendarFab(
        onAddScheduleClick = {},
        onAddDiaryClick = {}
    )
}
*/