package kr.co.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.nbdream.core.ui.R
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typo.body1
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text,
            style = textStyle
        )
    }
}

@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    text: String,
    onSkipClick: () -> Unit = {}
) {
    CustomButton(
        text = text,
        onClick = onSkipClick,
        containerColor = Color.Transparent,
        modifier = modifier
            .fillMaxWidth(),
        contentColor = MaterialTheme.colors.secondary
    )
}
@Composable
fun InputCompleteButton(
    modifier: Modifier = Modifier,
    text: String,
    onNextClick: () -> Unit = {}
) {
    CustomButton(
        text = text,
        onClick = onNextClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        textStyle = MaterialTheme.typo.button
    )
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    @StringRes skipId: Int,
    @StringRes nextId: Int,
    onSkipClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            SkipButton(
                text = stringResource(id = skipId),
                onSkipClick = onSkipClick
            )
            InputCompleteButton(
                text = stringResource(id = nextId),
                onNextClick = onNextClick
            )
        }
    }
}

//다른 버튼 예시
@Composable
private fun TestCropButton(
    modifier: Modifier = Modifier,
    text: String,
    onSkipClick: () -> Unit = {}
) {
    CustomButton(
        text = text,
        onClick = onSkipClick,
        modifier = modifier,
        containerColor = MaterialTheme.colors.green6,
        shape = RoundedCornerShape(12.dp),
        contentColor = MaterialTheme.colors.secondary
    )
}
@Preview
@Composable
private fun ButtonPreview() {
    TestCropButton(text = "농작물")
}
@Preview
@Composable
private fun SkipButtonPreview() {
    SkipButton(text = "나중에 입력할게요")
}
@Preview
@Composable
private fun InputCompleteButtonPreview() {
    InputCompleteButton(text = "다음으로")
}

//@Preview(showBackground = true)
//@Composable
//private fun NextButtonPreview() {
//    NextButton()
//}