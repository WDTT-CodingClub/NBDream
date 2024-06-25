package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamListDialog(
    onDismissRequest: () -> Unit,
    descriptionList: List<String>,
    modifier: Modifier = Modifier,
    header: String? = null,
    onDescriptionClick: (String) -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .fillMaxWidth(328 / 375f)
                .background(
                    color = MaterialTheme.colors.white
                )
                .padding(
                    24.dp
                ),
        ) {
            header?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typo.h4,
                    color = MaterialTheme.colors.black
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            descriptionList.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDescriptionClick(it) }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NBDreamTheme {
        DreamListDialog(
            header = "프로필 사진",
            descriptionList = listOf("사진첩", "카메라","기본 프로필로 설정"),
            onDismissRequest = {}
        )
    }
}