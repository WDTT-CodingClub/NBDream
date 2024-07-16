package kr.co.main.calendar.common.content

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
import androidx.compose.ui.res.stringResource
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.MoreVertical
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun CalendarContentDropDownMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandDropDown by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    expandDropDown = true
                },
            imageVector = DreamIcon.MoreVertical,
            tint = MaterialTheme.colors.gray5,
            contentDescription = ""
        )
        DropdownMenu(
            expanded = expandDropDown,
            onDismissRequest = { expandDropDown = false },
        ) {
            CalendarContentDropDownMenuItem(
                menuNameId = kr.co.nbdream.core.ui.R.string.core_ui_dropdown_menu_edit,
                onClick = {
                    expandDropDown = false
                    onEditClick()
                }
            )
            CalendarContentDropDownMenuItem(
                menuNameId = kr.co.nbdream.core.ui.R.string.core_ui_dropdown_menu_delete,
                onClick = {
                    expandDropDown = false
                    onDeleteClick()
                }
            )
        }
    }
}

@Composable
private fun CalendarContentDropDownMenuItem(
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