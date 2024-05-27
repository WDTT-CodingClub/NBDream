package kr.co.wdtt.nbdream.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountBookResponse(
    val response: Response
) {
    @Serializable
    data class Response(
        val header: Header,
        val body: Body,
    ) {
        @Serializable
        data class Header(
            val resultCode: String,
            val resultMsg: String,
        )

        @Serializable
        data class Body(
            val items: List<Item>
        ) {
            @Serializable
            data class Item(
                val id: String,
                val title: String,
                val category: String,
                val imageUrl: List<String>,
                val registerDateTime: String?,
                val year: Int?,
                val month: Int?,
                val day: Int?,
                val dayName: String?,
                val revenue: Long?,
                val expense: Long?,
                val totalRevenue: Long?,
                val totalExpense: Long?,
                val totalCost: Long?
            )
        }
    }
}
