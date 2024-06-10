package kr.co.onboard.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.theme.NBDreamTheme

@Composable
internal fun Splash() {
    Column {
        Logo()
    }

}

@Composable
@Preview(showSystemUi = true)
private fun SplashPreview() {
    NBDreamTheme {
        Splash()
    }
}