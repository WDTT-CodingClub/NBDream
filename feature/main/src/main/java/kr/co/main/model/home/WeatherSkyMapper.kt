package kr.co.main.model.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kr.co.main.R

@Composable
internal fun toSky(e: String): Painter =
    when(e) {
        "맑음" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_sunny_d)
        "구름많음" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_cloudy)
        "구름많고 비" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_light_rain)
        "눈","구름많고 눈" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_cloudy)
        "구름 많고 비" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_light_rain)
        "구름 많고 소나기" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_thunderstorm)
        "흐림" -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_mist)
        else -> painterResource(id = kr.co.nbdream.core.ui.R.drawable.img_cloudy)
    }