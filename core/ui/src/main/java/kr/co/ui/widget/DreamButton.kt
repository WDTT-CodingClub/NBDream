package kr.co.ui.widget

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun DreamButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors : ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.gray10
    )
){
    Button(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(60.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = colors
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.button,
        )
    }
}