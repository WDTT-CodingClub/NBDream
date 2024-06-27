package kr.co.main.community

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.ui.theme.NBDreamTheme

@Composable
internal fun CommunityDisableScreen(
    color: Color = Color(0x44000000),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color,
    ) {}
}

@Composable
internal fun CommunityDialogSimpleTitle(
    onDismissRequest: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
    fontSize: TextUnit = 18.sp,
    textAlign: TextAlign = TextAlign.Center,
    dismissButton: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        title = {
            Text(
                text = text,
                modifier = textModifier,
                fontSize = fontSize,
                textAlign = textAlign,
            )
        },
    )
}

@Preview
@Composable
private fun CommunityDialogSimpleTitlePreview() {
    NBDreamTheme {
        CommunityDialogSimpleTitle({}, "처리되었습니다.")
    }
}

//@Composable
//internal fun CommunitySimpleLoadingDialog(
//    onDismissRequest: () -> Unit = {},
//    title: @Composable (() -> Unit)? = { Text("Loading...") },
//) {
//    AlertDialog(
//        onDismissRequest = onDismissRequest,
//        confirmButton = {},
//        title = title,
//    )
//}
//
//@Composable
//internal fun CommunitySimpleLoadingDialog(
//    onDismissRequest: () -> Unit = {},
//    title: String,
//) {
//    CommunitySimpleLoadingDialog(
//        onDismissRequest = onDismissRequest,
//        title = { Text(title) },
//    )
//}
//
//
//@Preview
//@Composable
//private fun CommunitySimpleLoadingDialogPreview() {
//    NBDreamTheme {
//        CommunitySimpleLoadingDialog()
//    }
//}
