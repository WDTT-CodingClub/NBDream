package kr.co.onboard.login

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.theme.NBDreamTheme

@Composable
fun Splash() {
    Column {
        Logo()
    }

}

@Composable
@Preview(showSystemUi = true)
fun SplashPreview() {
    NBDreamTheme {
        Splash()
    }
}