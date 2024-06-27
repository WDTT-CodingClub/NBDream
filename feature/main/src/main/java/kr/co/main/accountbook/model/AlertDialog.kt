package kr.co.main.accountbook.model

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun AccountBookDialog(
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    confirmButtonText: String = "네",
    dismissButtonText: String = "아니오"
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typo.h4
            )
        },
        text = {
            Text(
                text = message,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typo.body1
            )
        },
        confirmButton = {
            AccountBookDialogButton(
                onClick = {
                    onConfirm()
                    onDismissRequest()
                },
                text = confirmButtonText
            )
        },
        dismissButton = {
            AccountBookDialogButton(
                onClick = onDismissRequest,
                text = dismissButtonText
            )
        }
    )
}

@Composable
internal fun AccountBookDialogButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colors.black
        )
    ) {
        Text(text)
    }
}