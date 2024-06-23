package kr.co.onboard.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kr.co.onboard.R
import kr.co.onboard.login.Logo
import kr.co.onboard.navigation.MAIN_ROUTE
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.typo
import kr.co.ui.widget.InputCompleteButton

@Composable
internal fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        modifier = modifier.padding(Paddings.xlarge),
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            WelcomeText(
                modifier = modifier
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Logo()
            }
            InputCompleteButton(
                text = stringResource(id = R.string.feature_onboard_my_farm_next),
                onNextClick = { navController.navigate(MAIN_ROUTE) }
            )
        }
    }
}

@Composable
fun WelcomeText(
    modifier: Modifier
) {
    Text(
        text = "반갑습니다\n말하는 감자님!",
        style = MaterialTheme.typo.headerSB,
        modifier = modifier.padding(Paddings.xlarge)
    )
}

//@Composable
//@Preview(showSystemUi = true)
//private fun SelectCropScreenPreview() {
//    NBDreamTheme {
//        WelcomeScreen()
//    }
//}