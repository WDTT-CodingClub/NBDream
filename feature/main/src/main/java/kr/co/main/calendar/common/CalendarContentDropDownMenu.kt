package kr.co.main.calendar.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.typo

@Composable
internal fun CalendarContentDropDownMenuItem(
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