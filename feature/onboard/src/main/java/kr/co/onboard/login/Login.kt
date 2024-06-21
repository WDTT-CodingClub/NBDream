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
import kr.co.ui.widget.DreamSocialButton

@Composable
internal fun Login(
    onSocialLoginClick: (AuthType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .background(color = Color.White)
            .padding(Paddings.xlarge),
    ) {
        Logo()
        SocialLoginButtons(
            onSocialLoginClick = onSocialLoginClick,
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
