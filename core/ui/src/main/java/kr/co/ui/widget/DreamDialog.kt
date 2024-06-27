package kr.co.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamDialog(
    header: String? = null,
    description: String,
    confirmText: String = "확인",
    dismissText: String = "취소",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit = onDismiss,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.white
                )
        ) {
            header?.let { header ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(
                            bottom = 8.dp,
                            top = 20.dp
                        ),
                    text = header,
                    style = MaterialTheme.typo.h4.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colors.gray1,
                        lineHeight = 27.2.sp,
                        letterSpacing = (-0.01).sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF0A84FF)
                )
            } ?: Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                text = description,
                style = MaterialTheme.typo.body1,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DiaButton(
                    modifier = Modifier.weight(1f),
                    text = dismissText,
                    onClick = onDismiss
                )


                DiaButton(
                    modifier = Modifier.weight(1f),
                    text = confirmText,
                    onClick = {
                        onDismissRequest()
                        onConfirm()
                    }
                )
            }
        }
    }
}

@Composable
private fun DiaButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        DreamDialog(
            header = "로그아웃 하시겠어요?",
            description = "언제든지 다시 로그인 하실 수 있어요.",
            onConfirm = { /*TODO*/ },
            onDismiss = { /*TODO*/ }
        )
    }
}