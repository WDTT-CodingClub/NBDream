package kr.co.main.calendar.adddiary.diaryinput

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kr.co.main.R
import kr.co.main.calendar.providers.FakeWorkDescriptionModelListProvider
import kr.co.main.calendar.ui.CalendarDesignToken
import kr.co.main.calendar.ui.common.CalendarUnderLineTextField
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.WorkDescriptionModelType
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Delete
import kr.co.ui.icon.dreamicon.GreenIcon
import kr.co.ui.icon.dreamicon.Spinner
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

internal class WorkInputStateHolder {
    var type: WorkDescriptionModelType? = null
        private set
    var description = ""
        private set

    fun onWorkTypeInput(input: WorkDescriptionModelType) {
        type = input
    }

    fun onDescriptionInput(input: String) {
        description = input
    }
}

@Composable
internal fun DiaryWorkInput(
    workDescriptions: List<DiaryModel.WorkDescriptionModel>,
    onAddWorkDescription: (WorkDescriptionModelType, String) -> Unit,
    onDeleteDescription: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val workInputStateHolder by rememberWorkInputStateHolder()

    Column(modifier = modifier.fillMaxWidth()) {
        WorkDescriptionInput(
            type = workInputStateHolder.type,
            description = workInputStateHolder.description,
            onWorkTypeInput = { workInputStateHolder.onWorkTypeInput(it) },
            onDescriptionInput = { workInputStateHolder.onDescriptionInput(it) },
            onAddWorkDescription = onAddWorkDescription
        )
        WorkDescriptionList(
            modifier = Modifier.padding(top = Paddings.medium),
            workDescriptions = workDescriptions,
            onDeleteDescription = onDeleteDescription
        )
    }
}

@Composable
private fun rememberWorkInputStateHolder() = remember {
    mutableStateOf(WorkInputStateHolder())
}

@Composable
private fun WorkDescriptionInput(
    type: WorkDescriptionModelType?,
    description: String,
    onWorkTypeInput: (WorkDescriptionModelType) -> Unit,
    onDescriptionInput: (String) -> Unit,
    onAddWorkDescription: (WorkDescriptionModelType, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WorkTypeSpinner(
            modifier = Modifier.weight(1f),
            workTypeId = type,
            onWorkTypeInput = onWorkTypeInput
        )
        WorkDescriptionTextField(
            modifier = Modifier.weight(3f),
            description = description,
            onDescriptionInput = onDescriptionInput
        )
        AddWorkDescriptionButton(
            modifier = Modifier.weight(1f),
            onClick = {
                checkNotNull(type)
                onAddWorkDescription(
                    type,
                    description
                )
            }
        )
    }
}

@Composable
private fun WorkTypeSpinner(
    workTypeId: WorkDescriptionModelType?,
    onWorkTypeInput: (WorkDescriptionModelType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandSpinner by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        WorkTypeSpinnerButton(
            workTypeId = workTypeId,
            onClick = { expandSpinner = true }
        )
        Box {
            WorkTypeSpinnerDropDown(
                modifier = Modifier,
                expanded = expandSpinner,
                workType = workTypeId,
                onWorkTypeInput = onWorkTypeInput,
                onDismiss = { expandSpinner = false }
            )
        }
    }
}

@Composable
private fun WorkTypeSpinnerButton(
    workTypeId: WorkDescriptionModelType?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp))
            .background(Color.LightGray)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(Paddings.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(end = Paddings.small),
                text = stringResource(
                    id = workTypeId?.id
                        ?: R.string.feature_main_calendar_add_diary_input_work_category
                ),
                style = MaterialTheme.typo.labelM,
                color = MaterialTheme.colors.text1
            )
            Icon(
                modifier = Modifier.size(CalendarDesignToken.WORK_TYPE_SPINNER_ICON_SIZE.dp),
                imageVector = DreamIcon.Spinner,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun WorkTypeSpinnerDropDown(
    expanded: Boolean,
    workType: WorkDescriptionModelType?,
    onWorkTypeInput: (WorkDescriptionModelType) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        modifier = modifier
            .height(CalendarDesignToken.WORK_TYPE_SPINNER_DROP_DOWN_HEIGHT.dp)
            .clip(RoundedCornerShape(CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp)),
        offset = DpOffset(0.dp, CalendarDesignToken.WORK_TYPE_SPINNER_DROP_DOWN_OFFSET.dp),
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        WorkDescriptionModelType.entries
            .toList()
            .forEach {
                WorkTypeSpinnerDropDownItem(
                    isSelected = (it == workType),
                    workType = it,
                    onWorkTypeInput = { workType ->
                        onWorkTypeInput(workType)
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
    onWorkTypeInput: (WorkDescriptionModelType) -> Unit,
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
                modifier = Modifier.padding(Paddings.small),
                text = stringResource(id = workType.id),
                style = MaterialTheme.typo.labelM,
                color = MaterialTheme.colors.text1
            )
        },
        onClick = { onWorkTypeInput(workType) }
    )
}

@Composable
private fun WorkDescriptionTextField(
    description: String,
    onDescriptionInput: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    CalendarUnderLineTextField(
        modifier = modifier,
        value = description,
        onValueChange = onDescriptionInput,
        placeHolder = {
            Text(
                text = stringResource(id = R.string.feature_main_calendar_add_diary_input_work_description),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
        }
    )
}

@Composable
private fun AddWorkDescriptionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .clip(RoundedCornerShape(CalendarDesignToken.ROUNDED_CORNER_RADIUS.dp)),
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(Paddings.small)
    ) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_diary_add),
            style = MaterialTheme.typo.bodyM
        )
    }
}

@Composable
private fun WorkDescriptionList(
    workDescriptions: List<DiaryModel.WorkDescriptionModel>,
    onDeleteDescription: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(workDescriptions, key = { it.id }) {
            WorkDescriptionItem(
                workDescription = it,
                onDeleteDescription = onDeleteDescription
            )
        }
    }
}

@Composable
private fun WorkDescriptionItem(
    workDescription: DiaryModel.WorkDescriptionModel,
    onDeleteDescription: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = Paddings.medium),
                imageVector = DreamIcon.GreenIcon,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(end = Paddings.medium),
                text = workDescription.description,
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = workDescription.id),
                style = MaterialTheme.typo.labelM,
                color = MaterialTheme.colors.text2
            )
        }
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = Paddings.medium)
                .clickable {
                    onDeleteDescription(workDescription.id)
                },
            imageVector = DreamIcon.Delete,
            tint = Color.LightGray,
            contentDescription = ""
        )
    }
}


@Preview
@Composable
private fun WorkInputPreview(
    @PreviewParameter(FakeWorkDescriptionModelListProvider::class)
    workDescriptions: List<DiaryModel.WorkDescriptionModel>
) {
    Surface {
        DiaryWorkInput(
            workDescriptions = workDescriptions,
            onAddWorkDescription = { _, _ -> },
            onDeleteDescription = { _ -> }
        )
    }
}
