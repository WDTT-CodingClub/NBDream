package kr.co.main.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

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
        .padding(32.dp),
    style: TextStyle = MaterialTheme.typo.body1,
    textAlign: TextAlign = TextAlign.Center,
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .background(
                    color = MaterialTheme.colors.white,
                )
        ) {
            Text(
                modifier = textModifier,
                text = text,
                style = style,
                textAlign = textAlign,
            )
        }
    }

//    AlertDialog(
//        onDismissRequest = onDismissRequest,
//        confirmButton = confirmButton,
//        modifier = modifier,
//        dismissButton = dismissButton,
//        title = {
//            Text(
//                text = text,
//                modifier = textModifier,
//                fontSize = fontSize,
//                textAlign = textAlign,
//            )
//        },
//    )

}

@Preview
@Composable
private fun CommunityDialogSimpleTitlePreview() {
    NBDreamTheme {
        CommunityDialogSimpleTitle({}, "처리되었습니다.")
    }
}
