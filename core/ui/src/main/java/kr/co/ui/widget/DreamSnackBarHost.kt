package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamSnackBarHost(
    snackBarHostState: SnackbarHostState,
) {
    SnackbarHost(
        hostState = snackBarHostState
    ) { data ->
        DreamSnackBar(
            message = data.visuals.message
        )
    }
}

@Composable
private fun DreamSnackBar(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .alpha(0.8f)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.black)
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typo.body2,
            color = MaterialTheme.colors.white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        Column(Modifier.background(MaterialTheme.colors.white)) {
            DreamSnackBar(message = "TEST")
        }
    }
}