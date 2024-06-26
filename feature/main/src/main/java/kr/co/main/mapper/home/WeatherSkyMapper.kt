package kr.co.main.mapper.home

import androidx.compose.ui.graphics.vector.ImageVector
import kr.co.common.mapper.Mapper
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Partlycloudy
import kr.co.ui.icon.dreamicon.Partlycloudycloudy
import kr.co.ui.icon.dreamicon.Rainthunder
import kr.co.ui.icon.dreamicon.Rainthundercloudy
import kr.co.ui.icon.dreamicon.Rainy
import kr.co.ui.icon.dreamicon.Rainycloudy
import kr.co.ui.icon.dreamicon.Snowrain
import kr.co.ui.icon.dreamicon.Snowraincloudy
import kr.co.ui.icon.dreamicon.Snowy
import kr.co.ui.icon.dreamicon.Snowycloudy
import kr.co.ui.icon.dreamicon.Sunny

internal object WeatherSkyMapper
    : Mapper<String, ImageVector> {
    override fun convert(param: String): ImageVector {
        return when(param) {
            "맑음" -> DreamIcon.Sunny
            "구름많음" -> DreamIcon.Partlycloudy
            "구름많고 비" -> DreamIcon.Rainy
            "구름많고 눈" -> DreamIcon.Snowy
            "구름많고 비/눈" -> DreamIcon.Snowrain
            "구름많고 소나기" -> DreamIcon.Rainthunder
            "흐림" -> DreamIcon.Partlycloudycloudy
            "흐리고 비" -> DreamIcon.Rainycloudy
            "흐리고 눈" -> DreamIcon.Snowycloudy
            "흐리고 비/눈" -> DreamIcon.Snowraincloudy
            "흐리고 소나기" -> DreamIcon.Rainthundercloudy
            else -> DreamIcon.Sunny
        }
    }
}