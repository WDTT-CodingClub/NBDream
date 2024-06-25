package kr.co.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.kakaoYellow
import kr.co.ui.theme.naverGreen
import kr.co.ui.theme.typo

@Composable
fun DreamSocialButton(
    type: Int,
    isLogin: Boolean = true,
    onClick: () -> Unit
) {
    when (type) {
        0 -> {
            LoginButton(
                image = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_kakao_login),
                text = if (isLogin) "카카오 로그인" else "카카오톡 계정 인증",
                backgroundColor = kakaoYellow,
                textColor = Color.Black,
                onClick = onClick
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        1 -> {
            LoginButton(
                image = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_naver_login),
                text = if (isLogin) "네이버 로그인" else "네이버 계정 인증",
                backgroundColor = naverGreen,
                textColor = Color.White,
                onClick = onClick
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        2 -> {
            LoginButton(
                image = painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_google_login),
                text = if (isLogin) "구글 로그인" else "구글 계정 인증",
                backgroundColor = Color.White,
                textColor = Color.Black,
                onClick = onClick
            )
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
                    style = MaterialTheme.typo.button,
                    color = textColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        DreamSocialButton(0){}
    }
}