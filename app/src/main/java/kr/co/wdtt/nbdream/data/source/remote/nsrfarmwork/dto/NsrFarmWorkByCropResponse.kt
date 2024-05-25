package kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NsrFarmWorkByCropResponse(
    val header: FarmWorkByCropHeader,
    val body: FarmWorkByCropBody,
)

@Serializable
data class FarmWorkByCropHeader(
    val resultCode: String,
    val resultMsg: String,
    val requestParameter: FarmWorkByCropRequestParameter
)

@Serializable
data class FarmWorkByCropRequestParameter(
    val cntntsNo: String
)

@Serializable
data class FarmWorkByCropBody(
    val items: List<FarmWorkByCropItem>
)

@Serializable
data class FarmWorkByCropItem(
    val beginMon: Int, //시작 월
    val beginEra: String, //시작 시기 (상/중/하)
    val endMon: Int, //종료 월
    val endEra: String, //종료 시기 (상/중/하)
    @SerialName("kidofcomdtySeCode")
    val categoryCode: String, //품목 코드 (ex. 210004)
    @SerialName("kidofcomdtySeCodeNm")
    val categoryName: String, //품목 코드 명 (ex. 벼)
    @SerialName("cntntsNo")
    val cropCode: String, //컨텐츠 번호
    @SerialName("farmWorkFlag")
    val cropName: String, //농작업 구분 (ex. 직파 재배)
    @SerialName("infoSeCode")
    val farmWorkTypeCode:String, //정보 시기 유형 코드 (ex. 410001)
    @SerialName("infoSeCodeNm")
    val farmWorkTypeName:String, //정보 시기 유형 코드 명 (ex. 생육과정(주요농작업))
    @SerialName("opertNm")
    val farmWork:String, //작업 명 (ex. 객토, 퇴비 주기)
    val reqreMonth:Int, //소요 개월 (1월 상~2월 중 일 경우, **5**)
    val vodUrl: String? = null//동영상 URL
)

