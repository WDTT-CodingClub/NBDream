package kr.co.main.calendar.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.co.main.calendar.CalendarDesignToken
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors

internal const val TEXT_FIELD_LIMIT_SINGLE = 40
internal const val TEXT_FIELD_LIMIT_MULTI = 3000

@Composable
internal fun CalendarUnderLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: @Composable () -> Unit = {},
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = maxLines,
        textStyle = TextStyle.Default.copy(
            textAlign = textAlign
        ),
        keyboardOptions = keyboardOptions
    ) { innerTextField ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier) {
                if (value.isEmpty()) placeHolder()
                innerTextField()
            }
            HorizontalDivider()
        }
    }
}

@Composable
internal fun CalendarContainerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: @Composable () -> Unit = {},
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = maxLines,
        textStyle = TextStyle.Default.copy(
            textAlign = textAlign
        ),
        keyboardOptions = keyboardOptions
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp))
                .background(MaterialTheme.colors.gray9),
        ) {
            Box(modifier = Modifier.padding(Paddings.xlarge)) {
                if (value.isEmpty()) placeHolder()
                innerTextField()
            }
        }
    }
}