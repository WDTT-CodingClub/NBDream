package kr.co.onboard.welcome

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kr.co.onboard.R
import kr.co.onboard.login.Logo
import kr.co.onboard.login.WelcomeLogo
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.background
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.InputCompleteButton
import timber.log.Timber

@Composable
internal fun WelcomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {

    val backStackEntry = navController.currentBackStackEntry
    val fullRoadAddress = backStackEntry?.arguments?.getString("fullRoadAddress") ?: ""
    val bCode = backStackEntry?.arguments?.getString("bCode") ?: ""
    val latitude = backStackEntry?.arguments?.getFloat("latitude") ?: 0F
    val longitude = backStackEntry?.arguments?.getFloat("longitude") ?: 0F
    val cropsString = backStackEntry?.arguments?.getString("cropsString") ?: " "
    val cropsList = if (cropsString.isNotEmpty()) cropsString.split(",") else emptyList<String>()

    LaunchedEffect(Unit) {
        viewModel.setAddressInfo(
            fullRoadAddress = fullRoadAddress,
            bCode = bCode,
            latitude = latitude,
            longitude = longitude,
            crops = cropsList
        )
    }
    Scaffold(
        modifier = modifier
            .padding(Paddings.xlarge)
            .background(MaterialTheme.colors.white),
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
                WelcomeLogo()
            }
            InputCompleteButton(
                text = stringResource(id = R.string.feature_onboard_start),
                onNextClick = {
                    viewModel.onClickNext()
                    viewModel.onClickConfirm()
                }
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