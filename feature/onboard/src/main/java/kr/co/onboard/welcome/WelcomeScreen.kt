package kr.co.onboard.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            crops = cropsList.filter { it != " " }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colors.white,
        modifier = modifier
            .background(MaterialTheme.colors.white)
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_tobot),
                contentDescription = "logo",
                modifier
                    .size(260.dp)
                    .padding(start = 16.dp, top = 80.dp)
            )

            Spacer(modifier.size(20.dp))

            ChatBox(modifier = modifier, text = "농부의 꿈에 오신 걸 환영합니다!")

            Spacer(modifier.size(20.dp))

            ChatBox(modifier = Modifier, text = "저와 함께 농부의 꿈을 이뤄보시겠나요?\n" +
                    "준비 되셨다면 시작하기를 눌러주세요!")

            Spacer(modifier = Modifier.weight(1f))

            InputCompleteButton(
                modifier = Modifier.padding(start = Paddings.xlarge, end = Paddings.xlarge),
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
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typo.displaySB,
        color = Color.White,
        fontSize = 16.sp,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun ChatBox(
    modifier: Modifier,
    text: String
){
    Box(
        modifier
            .padding(start = 30.dp)
            .background(color = MaterialTheme.colors.green4, shape = RoundedCornerShape(12.dp))
            .height(100.dp),
        Alignment.Center
    ){
        Image(
            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_speech_bubble_tail),
            contentDescription = "speech_bubble_tail",
            modifier
                .size(20.dp)
                .align(Alignment.TopStart)
                .offset(x = 5.dp, y = (-10).dp)
        )
        WelcomeText(
            text
        )
    }
}

@Preview
@Composable
private fun welcomeScreenPreview(
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        modifier = Modifier
            .background(MaterialTheme.colors.white)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_tobot),
                contentDescription = "logo",
                Modifier
                    .size(260.dp)
                    .padding(start = 16.dp, top = 80.dp)
            )

            Spacer(modifier = Modifier.size(20.dp))

            ChatBox(modifier = Modifier, text = "농부의 꿈에 오신 걸 환영합니다!")

            Spacer(modifier = Modifier.size(20.dp))

            ChatBox(modifier = Modifier, text = "저와 함께 농부의 꿈을 이뤄보시겠나요?\n" +
                    "준비 되셨다면 시작하기를 눌러주세요!")
            Spacer(modifier = Modifier.weight(1f))

            InputCompleteButton(
                modifier = Modifier.padding(Paddings.xlarge),
                text = stringResource(id = R.string.feature_onboard_start),
                onNextClick = {
//                    viewModel.onClickConfirm()
                }
            )
        }
    }
}