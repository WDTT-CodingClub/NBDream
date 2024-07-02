package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DreamImeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLine: Int = 3,
    maxLength: Int = Int.MAX_VALUE,
    enableLengthGuid: Boolean = false,
    error: Boolean = false,
    errorGuid: String? = "글자를 확인해 주세요",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val isImeVisible = WindowInsets.isImeVisible

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        horizontalAlignment = Alignment.End,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            if (error || errorGuid != null) {
                Text(
                    text = errorGuid ?: "",
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier)
            if (enableLengthGuid) {
                Text(
                    text = "${value.length}/$maxLength",
                    style = MaterialTheme.typo.label,
                    color = if (error) MaterialTheme.colors.error else MaterialTheme.colors.gray4
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = if(error) MaterialTheme.colors.error else MaterialTheme.colors.gray3
        )

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.gray9,
                )
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
            value = value,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            textStyle = MaterialTheme.typo.body1,
            maxLines = maxLine,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    var text by remember { mutableStateOf("") }
    NBDreamTheme {
        DreamImeTextField(
            value = text,
            onValueChange = { text = it },
            enableLengthGuid = true,
            maxLine = 5,
            maxLength = 200,
            error = true
        )
    }
}