package kr.co.main.calendar.adddiary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.calendar.adddiary.diaryinput.DiaryInputContent
import kr.co.main.calendar.adddiary.diaryinput.DiaryInputWrapper
import kr.co.main.calendar.common.CalendarBaseTopBar
import kr.co.main.calendar.common.CalendarHorizontalDivider


@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun AddDiaryScreen(
    modifier: Modifier = Modifier,
    viewModel: AddDiaryViewModel = hiltViewModel()
) {
    val addDiaryScreenState = viewModel.state.collectAsState()
    val addDiaryScreenInput = viewModel.input

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AddDiaryTopBar(
                onBackClick = { /* TODO 전 화면으로 이동*/ },
                onPostClick = addDiaryScreenInput::onPostClick
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                DiaryInputWrapper(
                    modifier = Modifier.weight(1f),
                    diaryInputContent = DiaryInputContent.DateInputContent(
                        headerId = R.string.feature_main_calendar_add_diary_header_date,
                        registerDate = addDiaryScreenState.value.registerDate,
                        weatherForecast = addDiaryScreenState.value.weatherForecast,
                        onRegisterDateInput = addDiaryScreenInput::onRegisterDateInput
                    )
                )
                DiaryInputWrapper(
                    modifier = Modifier.weight(1f),
                    diaryInputContent = DiaryInputContent.OverviewInputContent(
                        workLaborer = addDiaryScreenState.value.workLaborer,
                        workHour = addDiaryScreenState.value.workHour,
                        workArea = addDiaryScreenState.value.workArea,
                        onWorkLaborerInput = addDiaryScreenInput::onWorkLaborerInput,
                        onWorkHourInput = addDiaryScreenInput::onWorkHourInput,
                        onWorkAreaInput = addDiaryScreenInput::onWorkAreaInput
                    )
                )
                CalendarHorizontalDivider()
                DiaryInputWrapper(
                    diaryInputContent = DiaryInputContent.WorkInputContent(
                        headerId = R.string.feature_main_calendar_add_diary_header_work,
                        workDescriptions = addDiaryScreenState.value.workDescriptions,
                        onAddWorkDescription = addDiaryScreenInput::onAddWorkDescription,
                        onDeleteDescription = addDiaryScreenInput::onDeleteWorkDescription
                    )
                )
                DiaryInputWrapper(
                    diaryInputContent = DiaryInputContent.ImageInputContent(
                        headerId = R.string.feature_main_calendar_add_diary_header_image,
                        images = addDiaryScreenState.value.images,
                        onAddImage = addDiaryScreenInput::onAddImage,
                        onDeleteImage = addDiaryScreenInput::onDeleteImage
                    )
                )
                DiaryInputWrapper(
                    diaryInputContent = DiaryInputContent.MemoInputContent(
                        headerId = R.string.feature_main_calendar_add_diary_header_memo,
                        memo = addDiaryScreenState.value.memo,
                        onMemoInput = addDiaryScreenInput::onMemoInput
                    )
                )
            }
        }
    }
}

@Composable
private fun AddDiaryTopBar(
    onBackClick: () -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarBaseTopBar(
        titleId = R.string.feature_main_calendar_add_diary_title,
        rightLabelId = R.string.feature_main_calendar_add_diary_post,
        onBackClick = { /*TODO 전 화면으로 이동*/ },
        onRightLabelClick = onPostClick
    )
}














