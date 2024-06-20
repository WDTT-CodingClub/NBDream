package kr.co.onboard.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kr.co.onboard.R
import kr.co.domain.model.AuthType
import kr.co.onboard.navigation.MAIN_ROUTE
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.kakaoYellow
import kr.co.ui.theme.naverGreen
import kr.co.ui.theme.typo

@Composable
internal fun Login(
    onSocialLoginClick: (AuthType) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier
            .background(color = Color.White)
            .padding(Paddings.xlarge),
    ) {
        Logo()
        SocialLoginButtons(
            onSocialLoginClick = onSocialLoginClick,
            navController = navController
        )
    }
}


@Composable
internal fun Logo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_logo),
            contentDescription = "logo"
        )
        Text(
            text = "",
            style = MaterialTheme.typo.displayB
        )
    }
}

@Composable
internal fun SocialLoginButtons(
    onSocialLoginClick: (AuthType) -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(Paddings.xlarge)
        ) {
            LoginButton(
                image = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_kakao_login),
                text = stringResource(R.string.feature_onboard_login_kakao_login),
                backgroundColor = kakaoYellow,
                textColor = Color.Black,
                onClick = { onSocialLoginClick(AuthType.KAKAO) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            LoginButton(
                image = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_naver_login),
                text = stringResource(R.string.feature_onboard_login_naver_login),
                backgroundColor = naverGreen,
                textColor = Color.White,
                onClick = { onSocialLoginClick(AuthType.NAVER) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                navController.navigate(MAIN_ROUTE)
            }) {
                Text(text = "메인화면으로 이동")
            }
//            LoginButton(
//                image = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_google_login),
//                text = stringResource(R.string.feature_onboard_login_google_login),
//                backgroundColor = Color.White,
//                textColor = Color.Black,
//                onClick = { onSocialLoginClick(AuthType.GOOGLE) }
//            )
        }
    }
}

@Composable
private fun LoginButton(
    image: Painter,
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = image,
                contentDescription = text
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }
    }
}

//@Composable
//@Preview(showSystemUi = true)
//private fun loginPreview() {
//    NBDreamTheme {
//        Login()
//    }
//}