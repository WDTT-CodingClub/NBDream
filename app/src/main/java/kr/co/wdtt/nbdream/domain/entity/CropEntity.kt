package kr.co.wdtt.nbdream.domain.entity

import androidx.compose.ui.graphics.vector.ImageVector

data class CropEntity(
    val cropCode:String, //작물 코드 = 응답 DTO cntntsNo
    val cropName: String, //작물 이름 (ex. 감자) = 응답 DTO sj
    val icon: ImageVector
    //val color: DreamColor or CropColor?
)