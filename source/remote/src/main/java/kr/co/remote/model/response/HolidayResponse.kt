package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HolidayResponse(
    val response: Response
) {
    @Serializable
    data class Response(
        val header: Header,
        val body: Body
    ) {
        @Serializable
        data class Header(
            val resultCode: String,
            val resultMsg: String
        )

        @Serializable
        data class Body(
            val items: Items,
            val numOfRows: Int,
            val pageNo: Int,
            val totalCount: Int
        ) {
            @Serializable
            data class Items(
                val item: List<Item>
            ) {
                @Serializable
                data class Item(
                    @SerialName("locdate")
                    val date: Int, //날짜 (ex. 20240527)
                    val seq: Int, //순번 (ex. 1)
                    @SerialName("dateKind")
                    val dateType: String, //종류 (ex. 01)
                    val isHoliday: String, //공공기관 휴일 여부 (ex. Y)
                    val dateName: String, //휴일 명 (ex. 삼일절)
                )
            }
        }
    }
}