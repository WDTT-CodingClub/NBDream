package kr.co.main.calendar.screen.calendarScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kr.co.main.R
import kr.co.main.calendar.common.content.DiaryContent
import kr.co.main.calendar.screen.calendarScreen.calendar.DiaryCalendar
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate

@Composable
internal fun DiaryTab(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    diaries: List<DiaryModel>,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                DiaryCalendar(
                    modifier = Modifier.padding(Paddings.xlarge),
                    calendarYear = calendarYear,
                    calendarMonth = calendarMonth,
                    selectedDate = selectedDate,
                    onDateSelect = onDateSelect,
                    holidays = holidays,
                    diaries = diaries
                )
            }

            if (calendarCrop == null) {
                DiaryCropEmptyCard(
                    modifier = Modifier.padding(Paddings.large)
                )
            }

            diaries.firstOrNull { it.date == selectedDate }?.let { diary ->
                Card(
                    modifier = Modifier.padding(Paddings.large),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    DiaryContent(
                        modifier = Modifier.padding(Paddings.xlarge),
                        diary = diary,
                        onEditClick = { onEditClick(diary.id) },
                        onDeleteClick = { onDeleteClick(diary.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DiaryCropEmptyCard(
    modifier: Modifier = Modifier
) {
    // TODO 클릭 시 온보딩 작물 선택 화면으로 이동
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xxlarge),
            text = stringResource(id = R.string.feature_main_calendar_diary_crop_empty),
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.gray4,
            textAlign = TextAlign.Center
        )
    }
}
