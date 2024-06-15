import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.calendar.calendar.CalendarViewModel
import kr.co.main.calendar.calendar.maincalendar.MainCalendar
import kr.co.main.calendar.common.CalendarBaseFab
import kr.co.main.calendar.common.CalendarHorizontalDivider
import kr.co.main.calendar.common.content.CalendarContent
import kr.co.main.calendar.common.content.CalendarContentWrapper
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.providers.FakeDiaryModelProvider
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Add
import kr.co.ui.icon.dreamicon.ArrowLeft
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.icon.dreamicon.Spinner
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar


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
                    modifier = Modifier.fillMaxWidth(),
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

                    DiaryList(
                        modifier = Modifier.fillMaxWidth(),
                        diaries = calendarScreenState.diaries
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarTopBar(
    userCrops: List<CropModel>,
    selectedCrop: CropModel?,
    onSelectCrop: (CropModel) -> Unit,
    modifier: Modifier = Modifier
) {
    DreamCenterTopAppBar(
        modifier = modifier,
        title = {
            CalendarTitle(
                userCrops = userCrops,
                selectedCrop = selectedCrop,
                onSelectCrop = onSelectCrop
            )
        }
    )
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
                modifier = Modifier.clickable { expandSpinner = true },
                crop = selectedCrop
            )
            CalendarTitleDropDownMenu(
                modifier = Modifier.align(Alignment.Start),
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
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
        modifier = modifier,
        enabled = isSelected,
        colors = MenuDefaults.itemColors().copy(
            textColor = MaterialTheme.colors.primary,
            disabledTextColor = MaterialTheme.colors.text1,
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
    Box(modifier = modifier) {
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

@Composable
private fun DiaryList(
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.LightGray
    ) {
        LazyColumn(
            contentPadding = PaddingValues(Paddings.xlarge),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium)
        ) {
            items(diaries, key = { it.id }) {
                DiaryItem(diary = it)
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
private fun CalendarTopBarPreview() {
    var selectedCrop by remember { mutableStateOf<CropModel?>(null) }
    CalendarTopBar(
        userCrops = listOf(
            CropModel.POTATO,
            CropModel.SWEET_POTATO,
            CropModel.TOMATO,
            CropModel.APPLE
        ).sortedBy { it.ranking },
        selectedCrop = CropModel.POTATO,
        onSelectCrop = { selectedCrop = it }
    )
}

@Preview(showBackground = true)
@Composable
private fun CalendarYearMonthPreview() {
    CalendarYearMonth(
        modifier = Modifier.fillMaxWidth(),
        year = 2024,
        month = 6,
        onMonthSelect = { _ -> }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun DiaryListPreview(
    @PreviewParameter(FakeDiaryModelProvider::class) diary: DiaryModel
) {
    DiaryList(
        modifier = Modifier.fillMaxWidth(),
        diaries = listOf(
            diary.copy(id = "1"),
            diary.copy(id = "2"),
            diary.copy(id = "3"),
            diary.copy(id = "4"),
            diary.copy(id = "5")
        )
    )
}

@Preview
@Composable
private fun CalendarFabPreview() {
    CalendarFab(
        onAddScheduleClick = {},
        onAddDiaryClick = {}
    )
}