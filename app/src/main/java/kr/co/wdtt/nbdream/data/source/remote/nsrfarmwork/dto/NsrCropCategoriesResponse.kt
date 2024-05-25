package kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class NsrCropCategoriesResponse(
    @Element
    val header: CropCategoriesHeader,
    @Element
    val body: CropCategoriesBody
)

@Xml(name="header")
data class CropCategoriesHeader(
    @PropertyElement
    val responseCode: String,
    @PropertyElement
    val resultMsg: String,
    @PropertyElement
    val requestParameter:String?
)

@Xml(name="body")
data class CropCategoriesBody(
    @Path("items")
    @Element
    val items:List<CropsByCategoryItem>
)

@Xml(name="item")
data class CropCategoriesItem(
    @PropertyElement(name = "kidofcomdtySeCode")
    val categoryCode:String, //품목 코드
    @PropertyElement(name = "codeNm")
    val categoryName:String, //품목 명
    @PropertyElement
    val sort:Int
)
