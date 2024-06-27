package kr.co.main.community

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun CommunityDisableScreen(
    color: Color = Color(0x44000000),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color,
    ) {}
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
