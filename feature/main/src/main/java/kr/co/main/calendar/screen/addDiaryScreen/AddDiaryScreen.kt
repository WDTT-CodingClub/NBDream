package kr.co.main.calendar.screen.addDiaryScreen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.calendar.common.AddScreenCenterTopAppBar
import kr.co.main.calendar.common.CalendarContainerTextField
import kr.co.main.calendar.common.CalendarDatePicker
import kr.co.main.calendar.common.CalendarlImagePicker
import kr.co.main.calendar.common.TEXT_FIELD_LIMIT_MULTI
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.WorkDescriptionModelType
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Delete
import kr.co.ui.icon.dreamicon.DropDown
import kr.co.ui.icon.dreamicon.GreenIcon
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import timber.log.Timber
import java.time.LocalDate

private const val ADD_WORK_DESCRIPTION_BUTTON_WIDTH = 80

private data class DiaryWorkInputData(
    val description: String = "",
    val workType: WorkDescriptionModelType? = null,
)

@Composable
internal fun AddDiaryRoute(
    viewModel: AddDiaryViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    navToCalendar: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.showPreviousScreen.collect {
            navToCalendar()
        }
    }

    AddDiaryScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        event = viewModel.event,
        popBackStack = popBackStack,
        navToCalendar = navToCalendar
    )
}

@Composable
private fun AddDiaryScreen(
    state: AddDiaryViewModel.AddDiaryScreenState,
    event: AddDiaryScreenEvent,
    popBackStack: () -> Unit,
    navToCalendar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("state: $state")

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = event::onAddImage
    )

    val enableAction = true

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        containerColor = Color.White,
        topBar = {
            AddScreenCenterTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                screenMode = state.screenMode,
                postModeTitleId = R.string.feature_main_calendar_top_app_bar_add_diary,
                editModeTitleId = R.string.feature_main_calendar_top_app_bar_edit_diary,
                actionHintId = R.string.feature_main_calendar_add_diary_input_hint_memo,
                enableAction = enableAction,
                popBackStack = popBackStack,
                onPostClick = {
                    event.onPostClick()
                    navToCalendar()
                },
                onEditClick = {
                    event.onEditClick()
                    navToCalendar()
                },
                onDeleteClick = {
                    event.onDeleteClick()
                    navToCalendar()
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(Paddings.extra)
                    .verticalScroll(rememberScrollState())
            ) {
                DiaryDatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    date = state.date,
                    onDateSelect = event::onDateSelect
                )
                GuidText(text = state.dateGuid)
                Spacer(modifier = Modifier.height(Paddings.extra))
                DiaryImagePicker(
                    modifier = Modifier.fillMaxWidth(),
                    multiplePhotoPickerLauncher = multiplePhotoPickerLauncher,
                    images = state.images,
                    onDeleteImage = event::onDeleteImage
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                DiaryMemoInput(
                    modifier = Modifier.fillMaxWidth(),
                    memo = state.memo,
                    onMemoInput = event::onMemoInput
                )
                GuidText(text = state.memoGuid)
                Spacer(modifier = Modifier.height(Paddings.extra))
                DiaryWorkInput(
                    modifier = Modifier.fillMaxWidth(),
                    workDescriptions = state.workDescriptions,
                    onAddWorkDescription = event::onAddWorkDescription,
                    onDeleteDescription = event::onDeleteWorkDescription
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                DiaryWorkLaborerInput(
                    modifier = Modifier.fillMaxWidth(),
                    workLaborer = state.workLaborer,
                    onWorkLaborerInput = event::onWorkLaborerInput
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                DiaryWorkHourInput(
                    modifier = Modifier.fillMaxWidth(),
                    workHour = state.workHour,
                    onWorkHourInput = event::onWorkHourInput
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                DiaryWorkAreaInput(
                    modifier = Modifier.fillMaxWidth(),
                    workArea = state.workArea,
                    onWorkAreaInput = event::onWorkAreaInput
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
            }
        }
    }
}

@Composable
private fun DiaryDatePicker(
    date: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_date),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarDatePicker(
            modifier = Modifier.fillMaxWidth(),
            date = date,
            onDateSelect = onDateSelect,
            maxDate = LocalDate.now()
        )
    }
}

@Composable
private fun DiaryImagePicker(
    multiplePhotoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
    images: List<String>,
    onDeleteImage: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_image),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarlImagePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.large),
            imagePickerLauncher = multiplePhotoPickerLauncher,
            images = images,
            onDeleteImage = onDeleteImage
        )
    }
}

@Composable
private fun DiaryMemoInput(
    memo: String,
    onMemoInput: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_memo),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarContainerTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(327f / 140f),
            value = memo,
            maxLines = Int.MAX_VALUE,
            onValueChange = {
                if (it.length >= TEXT_FIELD_LIMIT_MULTI) {
                    Toast.makeText(
                        context,
                        context.getString(kr.co.nbdream.core.ui.R.string.core_ui_text_field_limit_multi_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onMemoInput(it)
                }
            },
            placeHolder = {
                Text(
                    text = stringResource(id = R.string.feature_main_calendar_add_diary_input_hint_memo),
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray4
                )
            }
        )
    }
}

@Composable
internal fun DiaryWorkInput(
    workDescriptions: List<DiaryModel.WorkDescriptionModel>,
    onAddWorkDescription: (DiaryModel.WorkDescriptionModel) -> Unit,
    onDeleteDescription: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        WorkDescriptionInput(
            modifier = Modifier,
            onAddWorkDescription = onAddWorkDescription
        )
        Spacer(modifier = Modifier.height(Paddings.extra))
        WorkDescriptionList(
            modifier = Modifier.padding(top = Paddings.medium),
            workDescriptions = workDescriptions,
            onDeleteDescription = onDeleteDescription
        )
    }
}

@Composable
private fun WorkDescriptionInput(
    onAddWorkDescription: (DiaryModel.WorkDescriptionModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var inputData by remember { mutableStateOf(DiaryWorkInputData()) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_work_description),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        DescriptionTextField(
            description = inputData.description,
            onDescriptionInput = {
                inputData = inputData.copy(description = it)
            }
        )
        Spacer(modifier = Modifier.height(Paddings.extra))
        Row(modifier = Modifier.fillMaxWidth()) {
            WorkTypePicker(
                modifier = Modifier.weight(1f),
                workType = inputData.workType,
                onWorkTypeSelect = {
                    inputData = inputData.copy(workType = it)
                }
            )
            Spacer(modifier = Modifier.width(Paddings.xlarge))
            AddWorkDescriptionButton(
                modifier = Modifier.width(ADD_WORK_DESCRIPTION_BUTTON_WIDTH.dp),
                onClick = {
                    if (inputData.description.isBlank()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.feature_main_calendar_add_diary_input_hint_work_description),
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (inputData.workType == null) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.feature_main_calendar_add_diary_input_hint_work_type),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        onAddWorkDescription(
                            DiaryModel.WorkDescriptionModel(
                                inputData.workType!!,
                                inputData.description
                            )
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun WorkTypePicker(
    workType: WorkDescriptionModelType?,
    onWorkTypeSelect: (WorkDescriptionModelType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var workTypePickerButtonWidth by remember { mutableStateOf(0.dp) }

    var expandDropDown by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        WorkTypePickerButton(
            modifier = Modifier.onGloballyPositioned {
                workTypePickerButtonWidth = with(density) { it.size.width.toDp() }
            },
            workType = workType,
            onClick = { expandDropDown = true }
        )
        WorkTypePickerDropDown(
            modifier = Modifier.width(workTypePickerButtonWidth),
            expanded = expandDropDown,
            workType = workType,
            onWorkTypeSelect = onWorkTypeSelect,
            onDismiss = { expandDropDown = false }
        )
    }
}

@Composable
private fun WorkTypePickerButton(
    workType: WorkDescriptionModelType?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp))
            .background(MaterialTheme.colors.gray9)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(Paddings.xlarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(end = Paddings.small)
                    .weight(1f),
                text = stringResource(
                    id = workType?.id
                        ?: R.string.feature_main_calendar_add_diary_input_hint_work_type
                ),
                style = MaterialTheme.typo.labelM,
                color =
                if (workType == null) MaterialTheme.colors.gray4
                else MaterialTheme.colors.gray1
            )
            Spacer(modifier = Modifier.width(Paddings.medium))
            Icon(
                modifier = Modifier.size(CalendarDesignToken.WORK_TYPE_DROP_DOWN_ICON_SIZE.dp),
                imageVector = DreamIcon.DropDown,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun WorkTypePickerDropDown(
    expanded: Boolean,
    workType: WorkDescriptionModelType?,
    onWorkTypeSelect: (WorkDescriptionModelType) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        modifier = modifier
            .height(CalendarDesignToken.WORK_TYPE_PICKER_DROP_DOWN_HEIGHT.dp)
            .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp)),
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        WorkDescriptionModelType.entries
            .toList()
            .forEach {
                WorkTypeSpinnerDropDownItem(
                    isSelected = (it == workType),
                    workType = it,
                    onClick = { workType ->
                        onWorkTypeSelect(workType)
                        onDismiss()
                    }
                )
            }
    }
}

@Composable
private fun WorkTypeSpinnerDropDownItem(
    isSelected: Boolean,
    workType: WorkDescriptionModelType,
    onClick: (WorkDescriptionModelType) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(Paddings.medium),
        text = {
            Text(
                modifier = Modifier.padding(Paddings.small),
                text = stringResource(id = workType.id),
                style = MaterialTheme.typo.labelM,
                color =
                if (isSelected) MaterialTheme.colors.primary
                else MaterialTheme.colors.gray1
            )
        },
        onClick = { onClick(workType) }
    )
}

@Composable
private fun DescriptionTextField(
    description: String,
    onDescriptionInput: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CalendarContainerTextField(
        modifier = modifier,
        value = description,
        onValueChange = onDescriptionInput,
        placeHolder = {
            Text(
                text = stringResource(id = R.string.feature_main_calendar_add_diary_input_hint_work_description),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.gray4
            )
        }
    )
}

@Composable
private fun AddWorkDescriptionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier
            .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp)),
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(Paddings.xlarge)
    ) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_add_work),
            style = MaterialTheme.typo.bodyM
        )
    }
}

@Composable
private fun WorkDescriptionList(
    workDescriptions: List<DiaryModel.WorkDescriptionModel>,
    onDeleteDescription: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.xlarge)
    ) {
        workDescriptions.forEachIndexed { index, workDescription ->
            WorkDescriptionItem(
                workDescription = workDescription,
                onDeleteDescription = { onDeleteDescription(index) }
            )
        }
    }
}

@Composable
private fun WorkDescriptionItem(
    workDescription: DiaryModel.WorkDescriptionModel,
    onDeleteDescription: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(Paddings.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.scale(1.2f),
                imageVector = DreamIcon.GreenIcon,
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = workDescription.description,
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray1
            )
            Text(
                text = stringResource(id = workDescription.type.id),
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray5
            )
        }
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    onDeleteDescription()
                },
            imageVector = DreamIcon.Delete,
            tint = MaterialTheme.colors.gray4,
            contentDescription = ""
        )
    }
}

@Composable
private fun DiaryWorkLaborerInput(
    workLaborer: Int,
    onWorkLaborerInput: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_work_laborer),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        DiaryInputWithDigit(
            digitId = R.string.feature_main_calendar_add_diary_work_laborer_digit,
            value = workLaborer,
            onValueInput = onWorkLaborerInput
        )
    }
}

@Composable
private fun DiaryWorkHourInput(
    workHour: Int,
    onWorkHourInput: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_work_hour),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        DiaryInputWithDigit(
            digitId = R.string.feature_main_calendar_add_diary_work_hour_digit,
            value = workHour,
            onValueInput = onWorkHourInput
        )
    }
}

@Composable
private fun DiaryWorkAreaInput(
    workArea: Int,
    onWorkAreaInput: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_header_work_area),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        DiaryInputWithDigit(
            digitId = R.string.feature_main_calendar_add_diary_work_area_digit,
            value = workArea,
            onValueInput = onWorkAreaInput
        )
    }
}

@Composable
private fun DiaryInputWithDigit(
    @StringRes digitId: Int,
    value: Int,
    onValueInput: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        modifier = modifier,
        value = value.toString(),
        onValueChange = {
            it.toIntOrNull()?.let { onValueInput(it) }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp))
                .background(MaterialTheme.colors.gray9),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Paddings.xlarge)
            ) {
                innerTextField()
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(Paddings.xlarge),
                text = stringResource(id = digitId),
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray4
            )
        }
    }
}

@Composable
private fun GuidText(
    text: String?,
) {
    text?.let {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = it,
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.error
        )
    }
}