package kr.co.onboard.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kr.co.domain.entity.type.AuthType
import kr.co.ui.theme.Paddings
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
        SocialLoginButtons(
            onSocialLoginClick = onSocialLoginClick,
        )
    }
}


@Composable
internal fun Logo(
    text: String = "",
) {
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
            text = text,
            style = MaterialTheme.typo.displayB
        )
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AuthType.entries.forEach {
                DreamSocialButton(type = it.ordinal) {
                    onSocialLoginClick(it)
                }
            }
        }
    }
}