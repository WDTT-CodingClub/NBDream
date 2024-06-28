package kr.co.main.calendar.screen.searchDiaryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.common.util.format
import kr.co.main.R
import kr.co.main.accountbook.model.CustomDatePickerDialog
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.CalendarSortType
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.ext.shadow
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.DatePicker
import kr.co.ui.icon.dreamicon.GreenIcon
import kr.co.ui.icon.dreamicon.Search
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
internal fun SearchDiaryRoute(
    popBackStack: () -> Unit,
    viewModel: SearchDiaryScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val (isDatePickerVisible, setDatePickerVisible) = remember { mutableStateOf<Boolean?>(null) }

    SearchDiaryScreen(
        state = state,
        onQueryChange = viewModel.event::onQueryInput,
        onSortChange = viewModel.event::onSortChange,
        onSearchClick = viewModel.event::onSearchClick,
        onStartDateClick = { setDatePickerVisible(true) },
        onEndDateClick = { setDatePickerVisible(false) },
        popBackStack = popBackStack
    )

    isDatePickerVisible?.let { isStartDate ->
        CustomDatePickerDialog(
            date = if (isStartDate) state.startDate else state.endDate,
            onClickCancel = { setDatePickerVisible(null) }
        ) { selected ->
            if (isStartDate) {
                viewModel.event.onStartDateInput(LocalDate.parse(selected))
            } else {
                viewModel.event.onEndDateInput(LocalDate.parse(selected))
            }
            setDatePickerVisible(null)
        }
    }
}

@Composable
private fun SearchDiaryScreen(
    state: SearchDiaryScreenViewModel.SearchDiaryScreenState = SearchDiaryScreenViewModel.SearchDiaryScreenState(),
    onQueryChange: (String) -> Unit = {},
    onSortChange: (CalendarSortType) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onStartDateClick: () -> Unit = {},
    onEndDateClick: () -> Unit = {},
    popBackStack: () -> Unit = {},
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = "영농일지 검색",
                colorBackground = true,
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.feature_main_pop_back_stack)
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(48.dp))

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF8F8F8),
                            shape = CircleShape
                        )
                        .padding(
                            horizontal = 20.dp,
                            vertical = 8.dp
                        )
                        .semantics {
                            contentDescription = "영농일지 검색"
                        },
                    value = state.query,
                    onValueChange = onQueryChange,
                    textStyle = MaterialTheme.typo.body1.copy(color = MaterialTheme.colors.gray1),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { onSearchClick() }
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            it()
                        }
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clearAndSetSemantics { },
                            imageVector = DreamIcon.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colors.gray6
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "일자 선택",
                        style = MaterialTheme.typo.h4,
                        color = MaterialTheme.colors.gray1
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DateTab(
                            state.startDate,
                            onDateClick = onStartDateClick
                        )

                        DateTab(
                            state.endDate,
                            onDateClick = onEndDateClick
                        )
                    }
                }
            }

            itemsIndexed(state.diaries) { index, diary ->
                if (index == 0) TextSort(
                    selected = state.sortType,
                    onSortChange = onSortChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                ScheduleCard(
                    isToday = diary.date == LocalDate.now(),
                    diary = diary
                )
            }
        }
    }
}

@Composable
private fun RowScope.DateTab(
    date: LocalDate,
    onDateClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onDateClick)
            .background(
                color = MaterialTheme.colors.gray10,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                vertical = 11.dp,
                horizontal = 16.dp
            )
            .semantics {
                contentDescription = "일자 선택 하기"
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = date.format("yyyy.MM.dd"),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            modifier = Modifier
                .size(20.dp)
                .clearAndSetSemantics { },
            imageVector = DreamIcon.DatePicker,
            contentDescription = null,
            tint = MaterialTheme.colors.gray4
        )
    }
}

@Composable
private fun TextSort(
    selected: CalendarSortType = CalendarSortType.RECENCY,
    onSortChange: (CalendarSortType) -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        CalendarSortType.entries.forEach {
            Text(
                modifier = Modifier
                    .noRippleClickable { onSortChange(it) }
                    .padding(vertical = 8.dp)
                    .padding(start = 16.dp),
                text = it.koreanName,
                style = MaterialTheme.typo.body2,
                color = if (selected == it) MaterialTheme.colors.primary else MaterialTheme.colors.gray4
            )
        }
    }
}

@Composable
private fun ScheduleCard(
    isToday: Boolean = false,
    diary: DiaryModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                color = MaterialTheme.colors.black.copy(0.12f),
                blurRadius = 8.dp,
                borerRadius = 12.dp,
                offsetY = 4.dp
            )
            .background(
                color = MaterialTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = MaterialTheme.typo.h4.fontFamily,
                            fontSize = MaterialTheme.typo.h4.fontSize,
                            fontStyle = MaterialTheme.typo.h4.fontStyle,
                            fontWeight = MaterialTheme.typo.h4.fontWeight,
                            letterSpacing = MaterialTheme.typo.h4.letterSpacing,
                            color = MaterialTheme.colors.black
                        )
                    ) {
                        append(
                            diary.date.format("MM월dd일 ")
                                    + LocalDate.now().dayOfWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.KOREAN
                            )
                        )
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF27A853)
                        )
                    ) {
                        if (!isToday) append("  오늘")
                    }
                    append("  " + diary.holidays)
                },
                style = MaterialTheme.typo.body1.copy(
                    color = MaterialTheme.colors.black.copy(0.4f)
                )
            )

            Text(
                text = diary.weatherInfo,
                fontFamily = MaterialTheme.typo.body1.fontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp,
                letterSpacing = (-0.011).em,
                color = MaterialTheme.colors.gray4
            )
        }

        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = "${diary.workLaborer}명 ㅣ ${diary.workHours}시간 ㅣ ${diary.workArea}평",
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray5
            )

        Spacer(modifier = Modifier.height(18.dp))

        diary.workDescriptions.forEach {
            DiaryContent(
                modifier = Modifier.padding(vertical = 2.dp),
                description = it
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            diary.images.forEach {
                AsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    model = "",
                    contentDescription = "영농일지 이미지"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = diary.memo,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )
    }
}

@Composable
private fun DiaryContent(
    modifier: Modifier = Modifier,
    description: DiaryModel.WorkDescriptionModel
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = DreamIcon.GreenIcon,
            contentDescription = "",
            tint = Color.Unspecified
        )
        Text(
            text = description.description,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )
        Text(
            text = stringResource(description.type.id),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray5
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        SearchDiaryScreen()
    }
}