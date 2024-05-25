package kr.co.wdtt.nbdream.domain.entity

data class CalendarFarmWorkEntity(
    val cropCode: String,
    val startMonth: Int,
    val startEra: CropScheduleEra,
    val endMonth:Int,
    val endEra: CropScheduleEra,
    val farmWorkTypeCode: String, //농작업 타입 코드 = 응답 DTO infoSeCode
    val farmWorkType: String, //농작업 타입 (ex. 생육과정(주요농작업)) = 응답 DTO infoSeCodeName
    val farmWork:String, //농작업 (ex. 객토, 퇴비 주기) = 응답 DTO opertNm
    val videoUrl: String
)

enum class CropScheduleEra {
    EARLY,
    MID,
    LATE
}
