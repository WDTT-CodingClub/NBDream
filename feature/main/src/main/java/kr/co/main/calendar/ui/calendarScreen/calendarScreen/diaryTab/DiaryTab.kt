package kr.co.main.calendar.ui.calendarScreen.calendarScreen.diaryTab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.calendar.providers.FakeDiaryModelProvider
import kr.co.main.calendar.ui.common.content.CalendarContent
import kr.co.main.calendar.ui.common.content.CalendarContentWrapper
import kr.co.main.calendar.ui.common.innerCalendar.InnerCalendar
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import java.time.LocalDate

@Composable
internal fun DiaryTab(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    navToSearchDiary: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiaryTabViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    val event = viewModel.event

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.gray9
    ) {
        Column {
            Card(
                modifier = Modifier.padding(Paddings.large),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                InnerCalendar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Paddings.xlarge),
                    selectedDate = LocalDate.now(), //TODO use state
                    calendarYear = LocalDate.now().year, //TODO use state
                    calendarMonth = LocalDate.now().monthValue, //TODO use state
                    onSelectDate = event::onSelectDate
                )
            }
        }
    }
}

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
private fun DiaryListPreview(
    @PreviewParameter(FakeDiaryModelProvider::class) diary: DiaryModel
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        DiaryList(
            diaries = listOf(
                diary.copy(id = 0),
                diary.copy(id = 1),
                diary.copy(id = 2),
                diary.copy(id = 3),
                diary.copy(id = 4)
            )
        )
    }
}