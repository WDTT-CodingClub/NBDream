package kr.co.onboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.onboard.type.AuthType
import kr.co.onboard.ui.login.LoginBtn
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme

@Composable
internal fun OnBoardRoute(
    viewModel: OnBoardViewModel = hiltViewModel(),
) {
   OnBoardScreen(
       onSocialLoginClick = viewModel::onSocialLoginClick,
   )
}

@Composable
private fun OnBoardScreen(
    onSocialLoginClick: (AuthType) -> Unit,
) {
    Scaffold(
        topBar = {

        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginBtn(
                onSocialLoginClick = onSocialLoginClick,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardScreenPreview() {
    NBDreamTheme {
        OnBoardScreen{
            
        }
    }
}
