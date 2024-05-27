package kr.co.wdtt.nbdream.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastResponse(
    val response: Response
) {
    @Serializable
    data class Response(
        val header: Header,
        val body: Body
    )

    @Serializable
    class Header(
        val resultCode: Int,
        val resultMsg: String
    )
    @Serializable
    data class Body(
        val dataType: String = "JSON",
        val items: Items,
        val pageNo: Int,
        val numOfRows: Int,
        val totalCount: Int
    ) {
        @Serializable
        data class Items(
            val item: List<Item> = emptyList()
        )
        @Serializable
        class Item(
            val baseDate: String = "",
            val baseTime: String = "",
            val category: String = "",
            val fcstDate: String = "",
            val fcstTime: String = "",
            val fcstValue: String = "",
            val nx: Int = -1,
            val ny: Int = -1
        )
    }
}
