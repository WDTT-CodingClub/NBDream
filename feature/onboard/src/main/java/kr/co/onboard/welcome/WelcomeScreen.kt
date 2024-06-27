package kr.co.onboard.welcome

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kr.co.onboard.R
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.InputCompleteButton

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
        containerColor = MaterialTheme.colors.white,
        modifier = modifier
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_welcome),
                        contentDescription = "logo"
                    )
                }
            }
            InputCompleteButton(
                modifier = Modifier.padding(Paddings.xlarge),
                text = stringResource(id = R.string.feature_onboard_start),
                onNextClick = {
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
        style = MaterialTheme.typo.displaySB,
        modifier = modifier.padding(start = 48.dp, top = 80.dp)
    )
}
