package kr.co.main.community

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BulletinDetailMoreBottomSheet(
    onDismissRequest: () -> Unit,
    onReportClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {

    @Composable
    fun LocalItemTextButton(
        text: String,
        onClick: () -> Unit,
    ) {
        TextButton(
            onClick = {
                onDismissRequest()
                onClick()

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
                color = MaterialTheme.colors.gray1)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        // TODO: 내 글인지 확인해서 표시 바꾸기.

        LocalItemTextButton(
            text = "신고하기",
            onClick = onReportClick
        )

        LocalItemTextButton(
            text = "수정하기",
            onClick = onEditClick
        )

        LocalItemTextButton(
            text = "삭제하기",
            onClick = onDeleteClick
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BulletinDetailMoreBottomSheetPreview() {
    NBDreamTheme {
        BulletinDetailMoreBottomSheet(
            onDismissRequest = {},
            onReportClick = {},
            onEditClick = {},
            onDeleteClick = {},
            sheetState = rememberStandardBottomSheetState(),  // Preview 하려면 이거 넣어야 한다고 함.
        )
    }
}
