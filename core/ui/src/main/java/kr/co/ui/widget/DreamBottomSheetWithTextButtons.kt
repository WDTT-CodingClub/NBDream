package kr.co.ui.widget

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors

data class TextAndOnClick(
    val text: String,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamBottomSheetWithTextButtons(
    onDismissRequest: () -> Unit,
    textAndOnClicks: List<TextAndOnClick>,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
//    scope: CoroutineScope = rememberCoroutineScope(),
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        for ((text, onClick) in textAndOnClicks) {
            TextButton(
                onClick = {
                    onDismissRequest()
                    onClick()

//                // 바텀시트가 내려가고 나서 동작하게끔 하는 코드인데, 속도가 느려서 비활성화
//                scope.launch { sheetState.hide() }.invokeOnCompletion {
//                    if (!sheetState.isVisible) {
//                        onDismissRequest()
//                        onClick()
//                    }
//                }

                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colors.gray1,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DreamBottomSheetWithTextButtonsPreview() {
    NBDreamTheme {
        DreamBottomSheetWithTextButtons(
            onDismissRequest = {},
            textAndOnClicks = listOf(
                TextAndOnClick("신고하기") {},
                TextAndOnClick("수정하기") {},
                TextAndOnClick("삭제하기") {},
            ),
            sheetState = rememberStandardBottomSheetState(),  // Preview 하려면 이거 넣어야 한다고 함.
        )
    }
}
