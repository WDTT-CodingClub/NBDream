package kr.co.main.calendar.screen.addDiaryScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.calendar.common.AddScreenCenterTopAppBar

@Composable
internal fun AddDiaryRoute(
    popBackStack: () -> Unit,
    viewModel: AddDiaryViewModel = hiltViewModel()
) {
    AddDiaryScreen(
        modifier = Modifier.fillMaxSize(),
        state = viewModel.state.collectAsState(),
        event = viewModel.event,
        popBackStack = popBackStack
    )
}

@Composable
private fun AddDiaryScreen(
    state: State<AddDiaryViewModel.AddDiaryScreenState>,
    event: AddDiaryScreenEvent,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = event::onAddImage
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AddScreenCenterTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                screenMode = state.value.screenMode,
                postModeTitleId = R.string.feature_main_calendar_top_app_bar_add_diary,
                editModeTitleId = R.string.feature_main_calendar_top_app_bar_edit_diary,
                actionHintId = R.string.feature_main_calendar_add_diary_input_hint_memo,
                popBackStack = popBackStack,
                onPostClick = event::onPostClick,
                onEditClick = event::onEditClick,
                onDeleteClick = event::onDeleteClick
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
//            Row(modifier = Modifier.fillMaxWidth()) {
//                DiaryInputWrapper(
//                    modifier = Modifier.weight(1f),
//                    diaryInputContent = DiaryInputContent.DateInputContent(
//                        headerId = R.string.feature_main_calendar_add_diary_header_date,
//                        registerDate = addDiaryScreenState.value.registerDate,
//                        weatherForecast = addDiaryScreenState.value.weatherForecast,
//                        onRegisterDateInput = addDiaryScreenInput::onRegisterDateInput
//                    )
//                )
//                DiaryInputWrapper(
//                    modifier = Modifier.weight(1f),
//                    diaryInputContent = DiaryInputContent.OverviewInputContent(
//                        workLaborer = addDiaryScreenState.value.workLaborer,
//                        workHour = addDiaryScreenState.value.workHour,
//                        workArea = addDiaryScreenState.value.workArea,
//                        onWorkLaborerInput = addDiaryScreenInput::onWorkLaborerInput,
//                        onWorkHourInput = addDiaryScreenInput::onWorkHourInput,
//                        onWorkAreaInput = addDiaryScreenInput::onWorkAreaInput
//                    )
//                )
//                CalendarHorizontalDivider()
//                DiaryInputWrapper(
//                    diaryInputContent = DiaryInputContent.WorkInputContent(
//                        headerId = R.string.feature_main_calendar_add_diary_header_work,
//                        workDescriptions = addDiaryScreenState.value.workDescriptions,
//                        onAddWorkDescription = addDiaryScreenInput::onAddWorkDescription,
//                        onDeleteDescription = addDiaryScreenInput::onDeleteWorkDescription
//                    )
//                )
//                DiaryInputWrapper(
//                    diaryInputContent = DiaryInputContent.ImageInputContent(
//                        headerId = R.string.feature_main_calendar_add_diary_header_image,
//                        images = addDiaryScreenState.value.images,
//                        onAddImage = addDiaryScreenInput::onAddImage,
//                        onDeleteImage = addDiaryScreenInput::onDeleteImage
//                    )
//                )
//                DiaryInputWrapper(
//                    diaryInputContent = DiaryInputContent.MemoInputContent(
//                        headerId = R.string.feature_main_calendar_add_diary_header_memo,
//                        memo = addDiaryScreenState.value.memo,
//                        onMemoInput = addDiaryScreenInput::onMemoInput
//                    )
//                )
//            }
        }
    }
}

