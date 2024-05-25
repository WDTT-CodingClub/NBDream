package kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class NsrCropsByCategoryResponse(
    @Element
    val header: CropsByCategoryHeader,
    @Element
    val body: CropsByCategoryBody
)

@Xml(name = "header")
data class CropsByCategoryHeader(
    @PropertyElement
    val responseCode: String,
    @PropertyElement
    val resultMsg: String,
    @Path("requestParameter")
    @PropertyElement
    val kidofcomdtySeCode: String
)

@Xml(name = "body")
data class CropsByCategoryBody(
    @Path("items")
    @Element
    val items: List<CropsByCategoryItem>
)

@Xml(name = "item")
data class CropsByCategoryItem(
    @PropertyElement(name = "cntntsNo")
    val cropCode:String,
    @PropertyElement
    val fileDownUrlInfo:String, //첨부파일 다운로드 url
    @PropertyElement
    val fileName: String, //첨부파일명
    @PropertyElement
    val fileSeCode: String, //첨부파일 구분 코드
    @PropertyElement
    val originFileNm: String, //첨부파일 물리명
    @PropertyElement(name="sj")
    val cropName:String, //제목
)