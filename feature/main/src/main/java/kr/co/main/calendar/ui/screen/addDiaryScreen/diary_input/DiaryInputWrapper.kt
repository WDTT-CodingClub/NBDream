package kr.co.main.calendar.ui.screen.addDiaryScreen.diary_input

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kr.co.main.calendar.adddiary.diaryinput.DiaryWorkInput
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.type.WorkDescriptionModelType
import kr.co.ui.theme.typo
import java.time.LocalDate

internal sealed class DiaryInputContent(
    @StringRes open val headerId: Int? = null
) {
    data class DateInputContent(
        @StringRes override val headerId: Int,
        val registerDate: LocalDate,
        val weatherInfo: String,
        val onRegisterDateInput: (LocalDate) -> Unit,
    ) : DiaryInputContent()

    data class OverviewInputContent(
        val workLaborer: Int,
        val workHour: Int,
        val workArea: Int,
        val onWorkLaborerInput: (Int) -> Unit,
        val onWorkHourInput: (Int) -> Unit,
        val onWorkAreaInput: (Int) -> Unit,
    ) : DiaryInputContent()

    data class WorkInputContent(
        @StringRes override val headerId: Int,
        val workDescriptions: List<DiaryModel.WorkDescriptionModel>,
        val onAddWorkDescription: (WorkDescriptionModelType, String) -> Unit,
        val onDeleteDescription: (Int) -> Unit
    ) : DiaryInputContent()

    data class ImageInputContent(
        @StringRes override val headerId: Int,
        val images: List<String>,
        val onAddImage: (String) -> Unit,
        val onDeleteImage: (String) -> Unit,
    ) : DiaryInputContent()

    data class MemoInputContent(
        @StringRes override val headerId: Int,
        val memo: String,
        val onMemoInput: (String) -> Unit,
        val modifier: Modifier = Modifier
    ) : DiaryInputContent()
}

@Composable
internal fun DiaryInputWrapper(
    diaryInputContent: DiaryInputContent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        diaryInputContent.headerId?.let {
            InputHeader(headerId = it)
        }
        when (diaryInputContent) {
            is DiaryInputContent.DateInputContent -> DiaryDateInput(
                registerDate = diaryInputContent.registerDate,
                weatherInfo = diaryInputContent.weatherInfo,
                onRegisterDateInput = diaryInputContent.onRegisterDateInput
            )

            is DiaryInputContent.OverviewInputContent -> DiaryOverviewInput(
                workLaborer = diaryInputContent.workLaborer,
                workHour = diaryInputContent.workHour,
                workArea = diaryInputContent.workArea,
                onWorkLaborerInput = diaryInputContent.onWorkLaborerInput,
                onWorkHourInput = diaryInputContent.onWorkHourInput,
                onWorkAreaInput = diaryInputContent.onWorkAreaInput
            )

            is DiaryInputContent.WorkInputContent -> DiaryWorkInput(
                workDescriptions = diaryInputContent.workDescriptions,
                onAddWorkDescription = diaryInputContent.onAddWorkDescription,
                onDeleteDescription = diaryInputContent.onDeleteDescription
            )

            is DiaryInputContent.ImageInputContent -> DiaryImageInput(
                images = diaryInputContent.images,
                onAddImage = diaryInputContent.onAddImage,
                onDeleteImage = diaryInputContent.onDeleteImage
            )

            is DiaryInputContent.MemoInputContent -> DiaryMemoInput(
                memo = diaryInputContent.memo,
                onMemoInput = diaryInputContent.onMemoInput,
                modifier = diaryInputContent.modifier
            )
        }
    }
}

@Composable
internal fun InputHeader(
    @StringRes headerId: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = headerId),
        style = MaterialTheme.typo.titleSB
    )
}