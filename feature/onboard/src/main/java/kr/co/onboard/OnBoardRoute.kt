package kr.co.onboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.domain.entity.type.AuthType
import kr.co.onboard.login.Login
import kr.co.ui.ext.scaffoldBackground

@Composable
internal fun OnBoardRoute(
    viewModel: OnBoardViewModel = hiltViewModel(),
    navigateToAddress: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.showAddressScreen.collect {
            navigateToAddress()
        }
    }

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
            Login(
                onSocialLoginClick = onSocialLoginClick,
            )
        }
    }
}
