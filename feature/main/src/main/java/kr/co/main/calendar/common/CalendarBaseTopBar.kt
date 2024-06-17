package kr.co.main.calendar.common

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.ArrowLeft
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun CalendarBaseTopBar(
    @StringRes titleId: Int,
    @StringRes rightLabelId: Int,
    onBackClick: () -> Unit,
    onRightLabelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onBackClick() },
            imageVector = DreamIcon.ArrowLeft,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = titleId),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onRightLabelClick() },
            text = stringResource(id = rightLabelId),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
    }
}