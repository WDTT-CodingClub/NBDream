package kr.co.main.calendar.common

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kr.co.main.R
import kr.co.main.model.calendar.type.ScreenModeType
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import timber.log.Timber

@Composable
internal fun AddScreenCenterTopAppBar(
    screenMode: ScreenModeType,
    @StringRes postModeTitleId: Int,
    @StringRes editModeTitleId: Int,
    @StringRes actionHintId: Int,
    popBackStack: () -> Unit,
    onPostClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    enableAction: Boolean = true
) {
    DreamCenterTopAppBar(
        modifier = modifier,
        title = stringResource(
            id =
            when (screenMode) {
                ScreenModeType.POST_MODE -> postModeTitleId
                ScreenModeType.EDIT_MODE -> editModeTitleId
            }
        ),
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    popBackStack()
                },
                imageVector = DreamIcon.Arrowleft,
                contentDescription = ""
            )
        },
        actions = {
            when (screenMode) {
                ScreenModeType.POST_MODE -> {
                    TopAppBarActionText(
                        actionTitleId = R.string.feature_main_calendar_top_app_bar_post,
                        actionHintId = actionHintId,
                        onClick = onPostClick,
                        enableAction = enableAction
                    )
                }

                ScreenModeType.EDIT_MODE -> {
                    var expandDropDown by remember { mutableStateOf(false) }

                    Column {
                        TopAppBarActionText(
                            actionTitleId = R.string.feature_main_calendar_top_app_bar_edit,
                            actionHintId = actionHintId,
                            onClick = { expandDropDown = true },
                            enableAction = enableAction
                        )

                        DropdownMenu(
                            expanded = expandDropDown,
                            onDismissRequest = { expandDropDown = false },
                        ) {
                            CardDropDownMenuItem(
                                menuNameId = kr.co.nbdream.core.ui.R.string.core_ui_dropdown_menu_edit,
                                onClick = onEditClick
                            )
                            CardDropDownMenuItem(
                                menuNameId = kr.co.nbdream.core.ui.R.string.core_ui_dropdown_menu_delete,
                                onClick = onDeleteClick // TODO 삭제 다이얼로그 표시 후 일정 삭제
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TopAppBarActionText(
    @StringRes actionTitleId: Int,
    @StringRes actionHintId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enableAction: Boolean = true
) {
    val context = LocalContext.current
    Text(
        modifier = modifier.clickable {
            if (enableAction) {
                onClick()
            } else {
                Toast.makeText(
                    context,
                    context.getString(actionHintId),
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        text = stringResource(id = actionTitleId),
        style = MaterialTheme.typo.bodyM,
        color =
        if (enableAction) MaterialTheme.colors.primary
        else MaterialTheme.colors.gray5
    )
}

@Composable
private fun CardDropDownMenuItem(
    @StringRes menuNameId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Text(
                modifier = Modifier.padding(horizontal = Paddings.large),
                text = stringResource(id = menuNameId),
                style = MaterialTheme.typo.bodyM
            )
        },
        onClick = onClick
    )
}

