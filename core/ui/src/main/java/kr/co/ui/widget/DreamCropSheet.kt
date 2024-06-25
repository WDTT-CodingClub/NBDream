@file:OptIn(ExperimentalMaterial3Api::class)

package kr.co.ui.widget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.theme.NBDreamTheme

@Composable
fun DreamCropSheet(
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {

    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        DreamCropSheet(){}
    }
}