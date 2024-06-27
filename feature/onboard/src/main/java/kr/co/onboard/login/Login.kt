package kr.co.onboard.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.domain.entity.type.AuthType
import kr.co.onboard.R
import kr.co.ui.theme.Paddings
import kr.co.ui.widget.DreamSocialButton

@Composable
internal fun Login(
    onSocialLoginClick: (AuthType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .background(Color(0xFFEFE8E0))
    ) {
        LogoBackground(modifier = Modifier)
        Logo("농부의 꿈")
        SocialLoginButtons(
            onSocialLoginClick = onSocialLoginClick,
        )
    }
}

@Composable
internal fun LogoBackground(
    modifier: Modifier
){
    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.backgrounddeco),
                    contentDescription = "배경 이미지",
                    modifier = modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.backgrounddeco),
                    contentDescription = "배경 이미지",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
internal fun Logo(
    text: String = "",
) {
    val FontFamily = FontFamily(
        Font(kr.co.nbdream.core.ui.R.font.nanum_myeongjo_bold, weight = FontWeight.Bold),
        Font(kr.co.nbdream.core.ui.R.font.nanum_myeongjo, weight = FontWeight.Normal)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        Image(
            painter = painterResource(id = kr.co.nbdream.core.ui.R.drawable.logo),
            contentDescription = "logo"
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontFamily = FontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color(0xFF413E33)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.feature_onboard_logo_description),
                fontFamily = FontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF413E33).copy(0.8f)
            )
        }
    }
}

@Composable
internal fun WelcomeLogo() {
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



@Composable
internal fun SocialLoginButtons(
    onSocialLoginClick: (AuthType) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(Paddings.xlarge)
        ) {
            AuthType.entries.forEach {
                DreamSocialButton(type = it.ordinal) {
                    onSocialLoginClick(it)
                }
            }
        }
    }
}